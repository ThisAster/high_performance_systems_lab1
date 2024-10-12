INSERT INTO patients (patient_name, date_of_birth) VALUES
('Иванов Иван Иванович', '1990-01-01'),
('Петров Петр Петрович', '1985-05-05'),
('Сидоров Сидор Сидорович', '2000-10-10');

INSERT INTO doctors (doctor_name, speciality) VALUES
('Врач А', 'Терапевт'),
('Врач Б', 'Хирург'),
('Врач В', 'Кардиолог');

INSERT INTO analyses (analysis_type, sample_date, result, status, patient_id) VALUES
('Общий анализ крови', DEFAULT, 'Нормально', 'Завершен', 1),
('Анализ на сахар', DEFAULT, 'Нормально', 'Завершен', 2);

INSERT INTO appointments_types (name, description, duration, price, doctor_id) VALUES
('Осмотр', 'Первичный осмотр пациента', 15, 1200.00, 1),
('Вырезание мозоли', 'Операция по вырезанию среднего размера мозоли', 10, 500.00, 2),
('Консультация', 'Консультация кардиолога', 20, 2000.00, 3);

INSERT INTO appointments (appointment_date, patient_id, appointment_type_id) VALUES
(DEFAULT, 1, 1),
(DEFAULT, 2, 2),
(DEFAULT, 1, 3);

INSERT INTO documents (document_type, creation_date, content, status, patient_id) VALUES
('Медицинская справка', DEFAULT, 'Справка о болезни', 'Активен', 1),
('Рекомендации', DEFAULT, 'Рекомендации по лечению', 'Активен', 2);

INSERT INTO recipes (recipe_date, medication, dose, duration, doctor_id, patient_id, appointment_id) VALUES
(DEFAULT, 'Парацетамол', '500 мг', '7 дней', 1, 1, 1),
(DEFAULT, 'Ибупрофен', '400 мг', '5 дней', 2, 2, 2);

INSERT INTO users (id, user_name, password) VALUES
(1, 'admin', 'admin123'),
(2, 'user', 'user123');

