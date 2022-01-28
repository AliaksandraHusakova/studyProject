INSERT INTO role (name) VALUES
    ('ADMIN'),
    ('USER');

INSERT INTO operation (name) VALUES
    ('READ'),
    ('WRITE');

INSERT INTO role_operation VALUES
    (1, 1),
    (1, 2),
    (2, 1);

INSERT INTO users (username, password, role_id) VALUES
    ('admin', 'admin', 1),
    ('user', 'user', 2);

INSERT INTO employee (first_name, last_name, middle_name, date_of_birth, user_id) VALUES
    ('Иван', 'Иванов', 'Иванович', '1990-06-15', 1),
    ('Петр', 'Петров', 'Петрович', '1995-12-03', 2);


