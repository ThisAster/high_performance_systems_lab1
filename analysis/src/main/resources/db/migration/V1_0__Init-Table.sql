CREATE TABLE analyses (
    id SERIAL PRIMARY KEY,
    analysis_type VARCHAR(255) NOT NULL,
    sample_date TIMESTAMP DEFAULT now() NOT NULL,
    result VARCHAR(200),
    status VARCHAR(255) NOT NULL,
    patient_id bigint
);
