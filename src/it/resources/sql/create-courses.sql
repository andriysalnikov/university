CREATE TABLE university.courses (
    id INT GENERATED ALWAYS AS IDENTITY,
    faculty_id INT,
    teacher_id INT,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY(id),
    UNIQUE(name)
);
INSERT INTO university.courses (faculty_id, teacher_id, name, description)
    VALUES
        (1, 1,  'Mathematics',         'Fundamentals of algebra, geometry and analysis'),
        (1, 1,  'Physics',             'Study of the fundamental laws of the universe'),
        (1, 1,  'Chemistry',           'How to make a moonshine machine'),
        (2, 2,  'Ukrainian language',  'Study of the state language'),
        (2, 2,  'English language',    'Study of the world language');