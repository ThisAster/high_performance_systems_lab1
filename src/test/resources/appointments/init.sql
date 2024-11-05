-- Patients table
INSERT INTO patients (patient_name, date_of_birth, email)
VALUES
    ('John Doe', '1990-01-15', 'john@example.com'),
    ('Jane Smith', '1985-06-22', 'jane@example.com'),
    ('Michael Johnson', '1978-03-11', 'michael@example.com');


INSERT INTO doctors (doctor_name, speciality)
VALUES
    ('Dr. Alice Brown', 'Cardiology'),
    ('Dr. Bob Lee', 'Neurology'),
    ('Dr. Carol Davis', 'Oncology');

INSERT INTO appointments_types (name, description, duration, price, doctor_id) VALUES
                                                                                   ('Осмотр', 'Первичный осмотр пациента', 15, 1200.00, 1),
                                                                                   ('Вырезание мозоли', 'Операция по вырезанию среднего размера мозоли', 10, 500.00, 2),
                                                                                   ('Консультация', 'Консультация кардиолога', 20, 2000.00, 3);

INSERT INTO appointments (appointment_date, patient_id, appointment_type_id) VALUES
                                                                                 ('2023-05-01 09:00:00', 1, 1),
                                                                                 ('2023-06-15 14:30:00', 2, 2),
                                                                                 ('2023-07-20 10:00:00', 1, 3);