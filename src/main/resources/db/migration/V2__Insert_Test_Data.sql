INSERT INTO patients (patient_name, date_of_birth, email) VALUES
('Иванов Иван Иванович', '1990-01-01', 'ivan@mail.ru'),
('Петров Петр Петрович', '1985-05-05', 'petr@yandex.ru'),
('Сидоров Сидор Сидорович', '2000-10-10', 'ilovesidr@gmail.ru');

INSERT INTO doctors (doctor_name, speciality, consultation_cost) VALUES
('Врач А', 'Терапевт', 1500.00),
('Врач Б', 'Хирург', 2000.00),
('Врач В', 'Кардиолог', 1800.00);

INSERT INTO analyses (analysis_type, sample_date, result, status, patient_id) VALUES
('Общий анализ крови', DEFAULT, 'Нормально', 'Завершен', 1),
('Анализ на сахар', DEFAULT, 'Нормально', 'Завершен', 2);

INSERT INTO appointments (appointment_date, description, doctor_id, patient_id) VALUES
(DEFAULT, 'Первичный прием', 1, 1),
(DEFAULT, 'Повторный прием', 2, 2),
(DEFAULT, 'Консультация кардиолога', 3, 1);

INSERT INTO documents (document_type, creation_date, content, status, patient_id) VALUES
('Медицинская справка', DEFAULT, 'Справка о болезни', 'Активен', 1),
('Рекомендации', DEFAULT, 'Рекомендации по лечению', 'Активен', 2);

INSERT INTO recipes (recipe_date, medication, dose, duration, doctor_id, patient_id, appointment_id) VALUES
(DEFAULT, 'Парацетамол', '500 мг', '7 дней', 1, 1, 1),
(DEFAULT, 'Ибупрофен', '400 мг', '5 дней', 2, 2, 2);

INSERT INTO users (id, user_name, password) VALUES
(1, 'admin', 'admin123'),
(2, 'user', 'user123');

