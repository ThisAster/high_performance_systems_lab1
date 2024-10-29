CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    patient_name VARCHAR(40) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(70) NOT NULL
);

CREATE TABLE doctors (
    id SERIAL PRIMARY KEY,
    doctor_name VARCHAR(40) NOT NULL,
    speciality VARCHAR(50) NOT NULL
);


CREATE TABLE analyses (
    id SERIAL PRIMARY KEY,
    analysis_type VARCHAR(255) NOT NULL,
    sample_date TIMESTAMP DEFAULT now() NOT NULL,
    result VARCHAR(200),
    status VARCHAR(255) NOT NULL,
    patient_id SERIAL,
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE appointments_types
(
    id SERIAL PRIMARY KEY,
    name        VARCHAR            NOT NULL,
    description VARCHAR            NOT NULL,
    duration    INTEGER DEFAULT 15 NOT NULL,
    price       NUMERIC(10, 2)     NOT NULL,
    doctor_id   BIGINT CONSTRAINT fk_doctor REFERENCES doctors ON DELETE CASCADE
);

CREATE TABLE appointments
(
    id                  SERIAL PRIMARY KEY,
    appointment_date    TIMESTAMP DEFAULT now() NOT NULL,
    patient_id          INTEGER CONSTRAINT fk_patient
        REFERENCES patients,
    appointment_type_id INTEGER CONSTRAINT fk_appointment_type
        REFERENCES appointments_types ON DELETE CASCADE
);

CREATE TABLE documents (
    id SERIAL PRIMARY KEY,
    document_type VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT now() NOT NULL,
    content VARCHAR(500),
    status VARCHAR(255) NOT NULL,
    patient_id SERIAL,
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE recipes (
                         id SERIAL PRIMARY KEY,
                         recipe_date TIMESTAMP DEFAULT now() NOT NULL,
                         medication VARCHAR(255) NOT NULL,
                         dose VARCHAR(255) NOT NULL,
                         duration VARCHAR(255) NOT NULL,
                         doctor_id INTEGER,
                         patient_id INTEGER,
                         appointment_id INTEGER,
                         CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
                         CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
                         CONSTRAINT fk_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE admins (
    id SERIAL PRIMARY KEY,
    admin_name VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE patients_roles (
    patient_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (patient_id, role_id),
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE doctors_roles (
    doctor_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (doctor_id, role_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE admins_roles (
    admin_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (admin_id, role_id),
    CONSTRAINT fk_admin
        FOREIGN KEY (admin_id)
        REFERENCES admins(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);
