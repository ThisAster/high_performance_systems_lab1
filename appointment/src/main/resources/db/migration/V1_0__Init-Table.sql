CREATE TABLE appointments_types
(
    id SERIAL PRIMARY KEY,
    name        VARCHAR            NOT NULL,
    description VARCHAR            NOT NULL,
    duration    INTEGER DEFAULT 15 NOT NULL,
    price       NUMERIC(10, 2)     NOT NULL,
    doctor_id   BIGINT
);

CREATE TABLE appointments
(
    id                  SERIAL PRIMARY KEY,
    appointment_date    TIMESTAMP DEFAULT now() NOT NULL,
    patient_id          INTEGER,
    appointment_type_id INTEGER CONSTRAINT fk_appointment_type
        REFERENCES appointments_types ON DELETE CASCADE
);