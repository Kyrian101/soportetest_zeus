INSERT INTO genders(name) VALUES ('Femenino'),
                                 ('Masculino');

INSERT INTO jobs(name, salary) VALUES ('Empleado Nivel I', 1000.0),
                                      ('Empleado Nivel II', 2000.0),
                                      ('Empleado Nivel III', 3000.0),
                                      ('Empleado Nivel IV', 4000.0);

-- Initial Example Employees
INSERT INTO employees(gender_id, job_id, name, last_name, birthdate) VALUES (2, 1, 'John', 'Doe', '1990-01-01');