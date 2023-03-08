----------------------
-- TABLES
----------------------
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL
);


CREATE TABLE postal_codes (
    code VARCHAR(50) PRIMARY KEY,
    location VARCHAR(255)
);

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    role_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    postal_code VARCHAR(50),
    address VARCHAR(50),
    house_number VARCHAR(50),
    registration_number VARCHAR(50),
    vat INT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT U_R_FK
        FOREIGN KEY(role_id)
            REFERENCES roles(id),
    CONSTRAINT U_PC_FK
        FOREIGN KEY(postal_code)
            REFERENCES postal_codes(code)
);

CREATE TABLE booking_states (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE bookings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    state_id UUID NOT NULL,
    client_id UUID NOT NULL,
    booked_by UUID NOT NULL,
    origin VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    pickup_date TIMESTAMP NOT NULL,
    extra VARCHAR(255),
    occupancy INT NOT NULL,
    CONSTRAINT B_S_FK
        FOREIGN KEY(state_id)
            REFERENCES booking_states(id),
    CONSTRAINT B_C_FK
        FOREIGN KEY(client_id)
            REFERENCES users(id),
    CONSTRAINT B_BY_FK
        FOREIGN KEY(booked_by)
            REFERENCES users(id)
);

CREATE TABLE brands (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL
);

CREATE TABLE taxis (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    brand_id UUID NOT NULL,
    license_plate VARCHAR(50) NOT NULL,
    max_occupancy INT NOT NULL,
    year INT NOT NULL,
    color VARCHAR(50),
	CONSTRAINT TAXI_B_FK
		FOREIGN KEY(brand_id)
			REFERENCES brands(id)
);

CREATE TABLE trips (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    taxi_id UUID NOT NULL,
    employee_id UUID NOT NULL,
    booking_id UUID NOT NULL,
    pickup_date TIMESTAMP NOT NULL,
    dropoff_date TIMESTAMP NOT NULL,
    price FLOAT NOT NULL,
    CONSTRAINT T_T_FK
        FOREIGN KEY(taxi_id)
            REFERENCES taxis(id),
    CONSTRAINT T_E_FK
        FOREIGN KEY(employee_id)
            REFERENCES users(id),
    CONSTRAINT T_B_FK
        FOREIGN KEY(booking_id)
            REFERENCES bookings(id)
);

CREATE TABLE payment_types (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    description VARCHAR(50) NOT NULL
);

CREATE TABLE payment (
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
    ('secretary', 'Secretary role');