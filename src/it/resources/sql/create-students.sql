CREATE TABLE university.students (
    id INT GENERATED ALWAYS AS IDENTITY,
    faculty_id INT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(first_name, last_name)
);

INSERT INTO university.students (faculty_id, first_name, last_name)
    VALUES
        (2, 'Bogdan',   'Stupka'),
        (2, 'Taras',    'Shevchenko'),
        (2, 'Yurij',    'Gevz'),
        (1, 'Stas',     'Boklan'),
        (1, 'Marichka', 'Padalka'),
        (1, 'Petro',    'Porosheko'),
        (1, 'Nestor',   'Mahno');