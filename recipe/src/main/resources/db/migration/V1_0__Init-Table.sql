CREATE TABLE recipes (
                         id SERIAL PRIMARY KEY,
                         recipe_date TIMESTAMP DEFAULT now() NOT NULL,
                         medication VARCHAR(255) NOT NULL,
                         dose VARCHAR(255) NOT NULL,
                         duration VARCHAR(255) NOT NULL,
                         doctor_id bigint,
                         patient_id bigint,
                         appointment_id bigint
);