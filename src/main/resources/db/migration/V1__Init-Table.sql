CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    patient_name VARCHAR(40) NOT NULL,
    date_of_birth DATE NOT NULL
);

CREATE TABLE doctors (
    id SERIAL PRIMARY KEY,
    doctor_name VARCHAR(40) NOT NULL,
    speciality VARCHAR(50) NOT NULL,
    consultation_cost NUMERIC(10, 2) NOT NULL
);


CREATE TABLE analyses (
    id SERIAL PRIMARY KEY,
    analysis_type VARCHAR(255) NOT NULL,
    sample_date TIMESTAMP DEFAULT now() NOT NULL,
    result VARCHAR(200),
    status VARCHAR(255) NOT NULL,
    doctor_id SERIAL,
    patient_id SERIAL,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE appointments (
    id SERIAL PRIMARY KEY,
    appointment_date TIMESTAMP DEFAULT now() NOT NULL,
    description VARCHAR(200),
    doctor_id SERIAL,
    patient_id SERIAL,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE documents (
    id SERIAL PRIMARY KEY,
    document_type VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT now() NOT NULL,
    content VARCHAR(500),
    status VARCHAR(255) NOT NULL,
    doctor_id SERIAL,
    patient_id SERIAL,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE recipes (
    id SERIAL PRIMARY KEY,
    recipe_date TIMESTAMP DEFAULT now() NOT NULL,
    medication VARCHAR(255) NOT NULL,
    dose VARCHAR(255) NOT NULL,
    duration VARCHAR(255) NOT NULL,
    doctor_id SERIAL,
    patient_id SERIAL,
    appointment_id SERIAL,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
);
