CREATE TABLE IF NOT EXISTS role_type (
     id SERIAL PRIMARY KEY,
     name TEXT NOT NULL UNIQUE
);

-- Insert initial roles into the role_enum table, only if they don't already exist
INSERT INTO role_type (name)
VALUES ('ADMIN'), ('SUPERVISOR'), ('USER')
    ON CONFLICT (name) DO NOTHING;


-- Таблица user
CREATE TABLE IF NOT EXISTS user_ (
    id SERIAL PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES role_type (id)
);


INSERT INTO user_ (email, login, password, role_id)
VALUES ('admin@test.ru', 'admin', '$2a$12$sEro9rIaSELb2Btm4zflO.SaAB5Q.DcNI.2cXi0PwmNGwktR08sCy', 2);