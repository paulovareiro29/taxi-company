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
    license_plate VARCHAR(50) NOT NULL UNIQUE,
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

CREATE TABLE payment_types (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE payments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    trip_id UUID NOT NULL,
    payment_type_id UUID NOT NULL,
    amount FLOAT NOT NULL,
    vat INT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT P_T_FK
        FOREIGN KEY(trip_id)
            REFERENCES trips(id),
    CONSTRAINT P_PT_FK
        FOREIGN KEY(payment_type_id)
            REFERENCES payment_types(id)
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
