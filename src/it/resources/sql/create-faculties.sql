CREATE TABLE university.faculties (
    id INT GENERATED ALWAYS AS IDENTITY,
    full_name VARCHAR(100) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(full_name)
);

INSERT INTO university.faculties (full_name)
    VALUES
        ('Faculty of Exact Sciences'),
        ('Faculty of Linguistics');