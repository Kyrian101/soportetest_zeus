CREATE TABLE genders (
    id INT AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE jobs (
    id INT AUTO_INCREMENT,
    name VARCHAR(255),
    salary DECIMAL(9, 2),
    PRIMARY KEY (id)
);

CREATE TABLE employees (
    id INT AUTO_INCREMENT,
    gender_id INT,
    job_id INT,
    name VARCHAR(255),
    last_name VARCHAR(255),
    birthdate DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (gender_id) REFERENCES genders(id),
    FOREIGN KEY (job_id) REFERENCES jobs(id)
);

CREATE TABLE employee_worked_hours (
    id INT AUTO_INCREMENT,
    employee_id INT,
    worked_hours INT,
    worked_date DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
)