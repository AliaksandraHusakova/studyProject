CREATE TABLE role(
    id serial PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE operation(
    id serial PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE role_operation(
    role_id integer NOT NULL REFERENCES role(id),
    operation_id integer NOT NULL REFERENCES operation(id),
    UNIQUE (role_id, operation_id)
);

CREATE TABLE users(
    id serial PRIMARY KEY,
    username varchar(25) UNIQUE NOT NULL,
    password varchar NOT NULL,
    role_id integer NOT NULL REFERENCES role(id)
);

CREATE TABLE employee(
    id serial PRIMARY KEY,
    first_name varchar(20) NOT NULL,
    last_name varchar(20) NOT NULL,
    middle_name varchar(20) NOT NULL,
    date_of_birth timestamp without time zone NOT NULL,
    user_id integer NOT NULL REFERENCES users(id)
);