----------------------
-- TABLES
----------------------
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL
);

----
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    role_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    postal_code VARCHAR(50),
    address VARCHAR(50),
    house_number VARCHAR(50),
    registration_number VARCHAR(50),
    vat INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP,
    CONSTRAINT U_R_FK
        FOREIGN KEY(role_id)
            REFERENCES roles(id)
);

CREATE UNIQUE INDEX users_email ON users (email) WHERE users.deleted_at IS NULL;
----


CREATE TABLE booking_states (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL
);

----
CREATE TABLE brands (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);

CREATE UNIQUE INDEX brands_name ON brands (name) WHERE brands.deleted_at IS NULL;
----

----
CREATE TABLE models (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL,
    brand_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP,
    CONSTRAINT MODEL_B_FK
        FOREIGN KEY (brand_id)
            REFERENCES brands(id)
);

CREATE UNIQUE INDEX models_name ON models (name) WHERE models.deleted_at IS NULL;
----

----
CREATE TABLE taxis (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    model_id UUID NOT NULL,
    license_plate VARCHAR(50) NOT NULL,
    max_occupancy INT NOT NULL,
    year INT NOT NULL,
    color VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP,
	CONSTRAINT TAXI_B_FK
		FOREIGN KEY(model_id)
			REFERENCES models(id)
);

CREATE UNIQUE INDEX taxis_plate ON taxis (license_plate) WHERE taxis.deleted_at IS NULL;
----


CREATE TABLE bookings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    state_id UUID NOT NULL,
    client_id UUID NOT NULL,
    booked_by UUID NOT NULL,
    taxi_id UUID,
    origin VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    pickup_date TIMESTAMP NOT NULL,
    extra VARCHAR(255),
    occupancy INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP,
    CONSTRAINT B_S_FK
        FOREIGN KEY(state_id)
            REFERENCES booking_states(id),
    CONSTRAINT B_C_FK
        FOREIGN KEY(client_id)
            REFERENCES users(id),
    CONSTRAINT B_BY_FK
        FOREIGN KEY(booked_by)
            REFERENCES users(id),
    CONSTRAINT B_T_FK
        FOREIGN KEY(taxi_id)
            REFERENCES taxis(id)
);

CREATE TABLE trips (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    employee_id UUID NOT NULL,
    booking_id UUID NOT NULL,
    pickup_date TIMESTAMP NOT NULL,
    dropoff_date TIMESTAMP NOT NULL,
    price FLOAT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP,
    CONSTRAINT T_E_FK
        FOREIGN KEY(employee_id)
            REFERENCES users(id),
    CONSTRAINT T_B_FK
        FOREIGN KEY(booking_id)
            REFERENCES bookings(id)
);

----
CREATE TABLE payment_methods (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);

CREATE UNIQUE INDEX payment_method_name ON payment_methods (name) WHERE payment_methods.deleted_at IS NULL;
----

CREATE TABLE payments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trip_id UUID NOT NULL,
    payment_method_id UUID NOT NULL,
    amount FLOAT NOT NULL,
    vat INT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT P_T_FK
        FOREIGN KEY(trip_id)
            REFERENCES trips(id),
    CONSTRAINT P_PT_FK
        FOREIGN KEY(payment_method_id)
            REFERENCES payment_methods(id)
);

CREATE TABLE feedbacks (
    client_id UUID NOT NULL,
    trip_id UUID NOT NULL,
    rating INT NOT NULL DEFAULT 1 CHECK (rating >= 1 AND rating <= 5),
    review VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (client_id, trip_id),
    CONSTRAINT F_C_FK
        FOREIGN KEY (client_id)
            REFERENCES users(id),
    CONSTRAINT F_T_FK
        FOREIGN KEY (trip_id)
            REFERENCES trips(id)
);

CREATE TABLE work_proposal (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    message VARCHAR(1024) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT W_U_FK
            FOREIGN KEY(user_id)
                REFERENCES users(id)
);

----------------------
-- TRIGGERS
----------------------

CREATE OR REPLACE FUNCTION update_models_deleted_at()
    RETURNS TRIGGER AS $$
    BEGIN
        UPDATE models
        SET deleted_at = NEW.deleted_at
        WHERE brand_id = OLD.id;
        RETURN NEW;
    END;
    $$ LANGUAGE plpgsql;

CREATE TRIGGER update_models_deleted_at
    AFTER UPDATE OF deleted_at ON brands
    FOR EACH ROW
    WHEN (OLD.deleted_at IS DISTINCT FROM NEW.deleted_at)
EXECUTE FUNCTION update_models_deleted_at();



CREATE OR REPLACE FUNCTION check_employee_role_on_trips()
    RETURNS TRIGGER AS $$
DECLARE
    user_role_name VARCHAR(50);
    BEGIN
        SELECT r.name into user_role_name FROM users u
        JOIN roles r ON u.role_id = r.id
        WHERE u.id = NEW.employee_id;

        IF user_role_name = 'driver' THEN
            RETURN NEW;
        ELSE
            RAISE EXCEPTION 'Only users with the driver role can be added to a trip.';
        END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_trips
    BEFORE INSERT ON trips
    FOR EACH ROW
EXECUTE FUNCTION check_employee_role_on_trips();

-----

CREATE OR REPLACE FUNCTION check_employee_role_on_bookings()
    RETURNS TRIGGER AS $$
DECLARE
    user_role_name VARCHAR(50);
BEGIN
    SELECT r.name INTO user_role_name FROM users u
    JOIN roles r ON u.role_id = r.id
    WHERE u.id = NEW.client_id;

    IF user_role_name = 'client' THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Only users with the client role can be added to a booking.';
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_bookings
    BEFORE INSERT ON bookings
    FOR EACH ROW
EXECUTE FUNCTION check_employee_role_on_bookings();

-----

CREATE OR REPLACE FUNCTION check_payment_amount()
    RETURNS TRIGGER AS $$
DECLARE
    trip_price FLOAT;
BEGIN
    SELECT t.price INTO trip_price
    FROM trips t
    WHERE t.id = NEW.trip_id;

    IF NEW.amount = trip_price THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Payment amount must be equal to the trip price.';
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_payments
    BEFORE INSERT ON payments
    FOR EACH ROW
EXECUTE FUNCTION check_payment_amount();

-----

CREATE OR REPLACE FUNCTION check_taxi_capacity()
RETURNS TRIGGER AS $$
DECLARE
    taxi_capacity INT;
    booking_occupancy INT;
BEGIN
    SELECT max_occupancy INTO taxi_capacity FROM taxis WHERE id = NEW.taxi_id;
	IF (TG_OP = 'UPDATE') THEN
            SELECT occupancy INTO booking_occupancy FROM bookings WHERE id = NEW.id;
	ELSIF(TG_OP = 'INSERT') THEN
			SELECT NEW.occupancy INTO booking_occupancy;
	END IF;
    IF booking_occupancy > taxi_capacity THEN
        RAISE EXCEPTION 'The taxi does not have enough capacity for this booking.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_taxi_capacity_before_update
BEFORE INSERT OR UPDATE ON bookings
FOR EACH ROW
EXECUTE FUNCTION check_taxi_capacity();

----------------------
-- INSERTS
----------------------

INSERT INTO booking_states (name, description) VALUES
    ('pending','The booking is awaiting a decision and still has not been confirmed or denied.'),
    ('confirmed', 'The booking has been accepted and scheduled to a specific date and place.'),
    ('ongoing', 'The booking is currently underway. The passenger is being transported to their destination.'),
    ('completed', 'The booking has been completed. The passenger has been transported and the trip has ended.'),
    ('cancelled', 'The booking has been cancelled by the passenger.');

INSERT INTO roles (name, description) VALUES
    ('client','Client role'),
    ('administration', 'Administration role'),
    ('secretary', 'Secretary role'),
    ('driver', 'Driver role');

INSERT INTO users (role_id, name, phone, email, password, vat)
    SELECT id, 'root', '999999999', 'root@ipvc.pt', '$2a$10$VIKmLsnhqYGBgok4OixLKuclODcNjltEOeV2DQJ72DhLENHnAGzGa','999999999'
    FROM roles
    WHERE name = 'administration';


