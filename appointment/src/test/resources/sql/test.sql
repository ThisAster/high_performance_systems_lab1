-- Appointments table
INSERT INTO appointments_types (name, description, duration, price, doctor_id) VALUES
                                                                                   ('Осмотр', 'Первичный осмотр пациента', 15, 1200.00, 1),
                                                                                   ('Вырезание мозоли', 'Операция по вырезанию среднего размера мозоли', 10, 500.00, 2),
                                                                                   ('Консультация', 'Консультация кардиолога', 20, 2000.00, 3);

INSERT INTO appointments (appointment_date, patient_id, appointment_type_id) VALUES
                                                                                 ('2023-05-01 09:00:00', 1, 1),
                                                                                 ('2023-06-15 14:30:00', 2, 2),
                                                                                 ('2023-06-15 15:30:00', 1, 2),
                                                                                 ('2023-07-20 10:00:00', 1, 3),
                                                                                 ('2023-07-20 10:00:00', 3, 2),
                                                                                 ('2025-11-15 01:38:03', 2, 2);