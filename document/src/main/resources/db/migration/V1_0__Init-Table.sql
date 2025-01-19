CREATE TABLE documents (
    id SERIAL PRIMARY KEY,
    document_type VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT now() NOT NULL,
    content VARCHAR(500),
    status VARCHAR(255) NOT NULL,
    patient_id bigint
);