-- Вставка тестовых данных в таблицу patients
INSERT INTO patients (patient_name, date_of_birth, email) VALUES
                                                              ('John Doe', '1990-01-15', 'johndoe@example.com'),
                                                              ('Jane Smith', '1985-05-20', 'janesmith@example.com'),
                                                              ('Emily Johnson', '2000-12-01', 'emilyj@example.com'),
                                                              ('Michael Brown', '1992-08-30', 'michaelb@example.com'),
                                                              ('Sarah Davis', '1978-11-25', 'sarahd@example.com');

-- Вставка тестовых данных в таблицу doctors
INSERT INTO doctors (doctor_name, speciality, consultation_cost) VALUES
                                                                     ('Dr. Alice Green', 'Cardiology', 150.00),
                                                                     ('Dr. Bob White', 'Dermatology', 120.00),
                                                                     ('Dr. Charlie Black', 'Pediatrics', 100.00),
                                                                     ('Dr. Diana Grey', 'Orthopedics', 180.00),
                                                                     ('Dr. Evan Blue', 'Neurology', 200.00);

-- Вставка тестовых данных в таблицу analyses
INSERT INTO analyses (analysis_type, sample_date, result, status, patient_id) VALUES
                                                                                  ('Blood Test', '2024-10-01 10:00:00', 'Normal', 'Completed', 1),
                                                                                  ('Urine Test', '2024-10-02 11:00:00', 'Pending', 'In Progress', 2),
                                                                                  ('X-Ray', '2024-10-03 09:30:00', 'Abnormal', 'Completed', 3),
                                                                                  ('MRI', '2024-10-04 08:15:00', 'Normal', 'Completed', 4),
                                                                                  ('CT Scan', '2024-10-05 14:45:00', 'Pending', 'In Progress', 5);

-- Вставка тестовых данных в таблицу appointments
INSERT INTO appointments (appointment_date, description, doctor_id, patient_id) VALUES
                                                                                    ('2024-10-10 14:30:00', 'Routine Checkup', 1, 1),
                                                                                    ('2024-10-11 09:00:00', 'Skin Consultation', 2, 2),
                                                                                    ('2024-10-12 11:30:00', 'Child Health Check', 3, 3),
                                                                                    ('2024-10-13 16:00:00', 'Knee Pain Assessment', 4, 4),
                                                                                    ('2024-10-14 10:15:00', 'Neurological Exam', 5, 5);

-- Вставка тестовых данных в таблицу documents
INSERT INTO documents (document_type, creation_date, content, status, patient_id) VALUES
                                                                                      ('Medical Report', '2024-10-06 12:00:00', 'Patient has no allergies.', 'Active', 1),
                                                                                      ('Prescriptions', '2024-10-07 12:00:00', 'Prescribed medication for treatment.', 'Active', 2),
                                                                                      ('Referral Letter', '2024-10-08 12:00:00', 'Referred to specialist.', 'Active', 3),
                                                                                      ('Follow-up Notes', '2024-10-09 12:00:00', 'Follow-up scheduled in 1 month.', 'Active', 4),
                                                                                      ('Test Results', '2024-10-10 12:00:00', 'Results available online.', 'Archived', 5);

-- Вставка тестовых данных в таблицу recipes
INSERT INTO recipes (recipe_date, medication, dose, duration, doctor_id, patient_id, appointment_id) VALUES
                                                                                                         ('2024-10-10 12:00:00', 'Aspirin', '100 mg', '5 days', 1, 1, 1),
                                                                                                         ('2024-10-11 12:00:00', 'Hydrocortisone Cream', '20 g', '7 days', 2, 2, 2),
                                                                                                         ('2024-10-12 12:00:00', 'Amoxicillin', '500 mg', '10 days', 3, 3, 3),
                                                                                                         ('2024-10-13 12:00:00', 'Ibuprofen', '200 mg', '5 days', 4, 4, 4),
                                                                                                         ('2024-10-14 12:00:00', 'Metformin', '500 mg', 'As needed', 5, 5, 5);

-- Вставка тестовых данных в таблицу users
INSERT INTO users (user_name, password) VALUES
                                            ('user1', 'password123'),
                                            ('user2', 'password456'),
                                            ('user3', 'password789');

-- Вставка тестовых данных в таблицу admins
INSERT INTO admins (admin_name, password) VALUES
                                              ('admin1', 'adminpass1'),
                                              ('admin2', 'adminpass2');

-- Вставка тестовых данных в таблицу roles
INSERT INTO roles (role_name) VALUES
                                  ('ROLE_USER'),
                                  ('ROLE_ADMIN'),
                                  ('ROLE_DOCTOR');

-- Вставка тестовых данных в таблицу patients_roles
INSERT INTO patients_roles (patient_id, role_id) VALUES
                                                     (1, 1),
                                                     (2, 1),
                                                     (3, 1);

-- Вставка тестовых данных в таблицу doctors_roles
INSERT INTO doctors_roles (doctor_id, role_id) VALUES
                                                   (1, 3),
                                                   (2, 3),
                                                   (3, 3);

-- Вставка тестовых данных в таблицу admins_roles
INSERT INTO admins_roles (admin_id, role_id) VALUES
                                                 (1, 2),
                                                 (2, 2);

-- Вставка тестовых данных в таблицу users_roles
INSERT INTO users_roles (user_id, role_id) VALUES
                                               (1, 1),
                                               (2, 1),
                                               (3, 1);
