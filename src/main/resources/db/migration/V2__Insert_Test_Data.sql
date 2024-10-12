-- Patients table
INSERT INTO patients (patient_name, date_of_birth, email)
VALUES
    ('John Doe', '1990-01-15', 'john@example.com'),
    ('Jane Smith', '1985-06-22', 'jane@example.com'),
    ('Michael Johnson', '1978-03-11', 'michael@example.com');

-- Doctors table
INSERT INTO doctors (doctor_name, speciality, consultation_cost)
VALUES
    ('Dr. Alice Brown', 'Cardiology', 150.00),
    ('Dr. Bob Lee', 'Neurology', 120.00),
    ('Dr. Carol Davis', 'Oncology', 180.00);

-- Analyses table
INSERT INTO analyses (analysis_type, result, status, patient_id)
VALUES
    ('Blood Test', 'Normal', 'Completed', 1),
    ('MRI Scan', 'No abnormalities', 'Completed', 2),
    ('ECG', 'Slightly elevated heart rate', 'Completed', 3);

-- Appointments table
INSERT INTO appointments (appointment_date, description, doctor_id, patient_id)
VALUES
    ('2023-05-01 09:00:00', 'Regular check-up', 1, 1),
    ('2023-06-15 14:30:00', 'Follow-up appointment', 2, 2),
    ('2023-07-20 10:00:00', 'Consultation for chest pain', 3, 3);

-- Documents table
INSERT INTO documents (document_type, content, status, patient_id)
VALUES
    ('Medical History', 'Patient has diabetes and high blood pressure.', 'Completed', 1),
    ('Prescription', 'Take medication twice daily.', 'Completed', 2),
    ('Discharge Summary', 'Patient recovered successfully.', 'Completed', 3);

-- Recipes table
INSERT INTO recipes (recipe_date, medication, dose, duration, doctor_id, patient_id, appointment_id)
VALUES
    ('2023-04-10', 'Metformin', '500mg twice daily', 'Ongoing', 1, 1, 1),
    ('2023-03-25', 'Lisinopril', '5mg once daily', '3 months', 2, 2, 2),
    ('2023-02-18', 'Ibuprofen', '400mg every 8 hours', '7 days', 3, 3, 3);

-- Users table
INSERT INTO users (user_name, password)
VALUES
    ('admin_user', 'password123'),
    ('doctor_user', 'doctordata');

-- Admins table
INSERT INTO admins (admin_name, password)
VALUES
    ('clinic_admin', 'adminpass');

-- Roles table
INSERT INTO roles (role_name)
VALUES
    ('ADMIN'),
    ('DOCTOR'),
    ('PATIENT'),
    ('USER');

-- Patients_roles table
INSERT INTO patients_roles (patient_id, role_id)
VALUES
    (1, 3),
    (2, 3),
    (3, 3);

-- Doctors_roles table
INSERT INTO doctors_roles (doctor_id, role_id)
VALUES
    (1, 2),
    (2, 2),
    (3, 2);

-- Admins_roles table
INSERT INTO admins_roles (admin_id, role_id)
VALUES
    (1, 1);

-- Users_roles table
INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2);