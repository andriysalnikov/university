CREATE TABLE university.classrooms (
    id INT GENERATED ALWAYS AS IDENTITY,
    faculty_id INT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(name)
);

INSERT INTO university.classrooms (faculty_id, name)
    VALUES (1, '101-A'), (null, '202-B'), (null, '202-C'), (2, 'Pool'), (2, 'Gym');