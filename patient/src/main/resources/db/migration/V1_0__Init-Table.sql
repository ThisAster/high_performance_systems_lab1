CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    patient_name VARCHAR(40) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(70) NOT NULL
);
