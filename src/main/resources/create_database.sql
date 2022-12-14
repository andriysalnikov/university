-- creating schema
DROP SCHEMA IF EXISTS university CASCADE;
CREATE SCHEMA university;


-- creating structure of database
CREATE TABLE university.faculties (
    id INT GENERATED ALWAYS AS IDENTITY,
    full_name VARCHAR(100) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(full_name)
);

CREATE TABLE university.teachers (
    id INT GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(first_name, last_name)
);

CREATE TABLE university.students (
    id INT GENERATED ALWAYS AS IDENTITY,
    faculty_id INT REFERENCES university.faculties(id) ON DELETE SET NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(first_name, last_name)
);

CREATE TABLE university.courses (
    id INT GENERATED ALWAYS AS IDENTITY,
    faculty_id INT REFERENCES university.faculties(id) ON DELETE SET NULL,
    teacher_id INT REFERENCES university.teachers(id) ON DELETE SET NULL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY(id),
    UNIQUE(name)
);

CREATE TABLE university.classrooms (
    id INT GENERATED ALWAYS AS IDENTITY,
    faculty_id INT REFERENCES university.faculties(id) ON DELETE SET NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(name)
);

CREATE TABLE university.events (
    id INT GENERATED ALWAYS AS IDENTITY,
    date_of_event DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    classroom_id INT REFERENCES university.classrooms(id) ON DELETE SET NULL,
    course_id INT REFERENCES university.courses(id) ON DELETE CASCADE,
    PRIMARY KEY(id),
    UNIQUE(date_of_event, start_time, end_time, course_id)
);

CREATE TABLE university.students_courses(
    student_id INT REFERENCES university.students(id) ON DELETE CASCADE,
    course_id INT REFERENCES university.courses(id) ON DELETE CASCADE,
    UNIQUE (student_id, course_id)
);

-- filling the database with initial data
INSERT INTO university.faculties (full_name)
    VALUES
        ('Faculty of Exact Sciences'),
        ('Faculty of Natural Sciences'),
        ('Faculty of Fine Arts'),
        ('Faculty of Linguistics'),
        ('Faculty of healthy lifestyle')
    ON CONFLICT (full_name) DO NOTHING;

INSERT INTO university.teachers (first_name, last_name)
    VALUES
        ('Valery',      'Pekar'),
        ('Olga',        'Scherbina'),
        ('Maryna',      'Starodubska'),
        ('Yaroslava',   'Lojanych'),
        ('Oleksandr',   'Savruk'),
        ('Valery',      'Pekar'),        -- duplicating, checking for conflict resolving
        ('Oleg',        'Shram')
    ON CONFLICT (first_name, last_name) DO NOTHING;

INSERT INTO university.students (faculty_id, first_name, last_name)
    VALUES
        (1, 'Olexiy',       'Arestovich'),
        (2, 'Bogdan',       'Stupka'),
        (3, 'Taras',        'Shevchenko'),
        (2, 'Yurij',        'Gevz'),
        (2, 'Stas',         'Boklan'),
        (1, 'Marichka',     'Padalka'),
        (1, 'Petro',        'Porosheko'),
        (2, 'Ruslana',      'Pysanka'),
        (3, 'Viktor',       'Yushenko'),
        (2, 'Igor',         'Terehov'),
        (3, 'Myhaylo',      'Podolyak'),
        (3, 'Nestor',       'Mahno'),
        (1, 'Andriy',       'Parubiy'),
        (3, 'Arsen',        'Yacenuk'),
        (1, 'Olga',         'Polyakova'),
        (2, 'Nastya',       'Kamenskih'),
        (3, 'Yegor',        'Vozniuk'),
        (2, 'Sergey',       'Zalkin'),
        (3, 'Yana',         'Izmaylova'),
        (1, 'Olesya',       'Homenko')
    ON CONFLICT (first_name, last_name) DO NOTHING;

INSERT INTO university.courses (faculty_id, teacher_id, name, description)
    VALUES
        (1, 1,  'Mathematics',         'Fundamentals of algebra, geometry and analysis'),
        (1, 1,  'Physics',             'Study of the fundamental laws of the universe'),
        (1, 1,  'Chemistry',           'How to make a moonshine machine'),
        (2, 3,  'Biology',             'Basics of how the earth biosphere works'),
        (4, 4,  'Ukrainian language',  'Study of the state language'),
        (4, 4,  'English language',    'Study of the world language'),
        (5, 7,  'Football',            'Putin - h#$%o, la-la-la-la-la-laaaa!!!'),
        (3, 2,  'Music',               'Study of singing and musical harmony'),
        (3, 2,  'Painting',            'Study of the basics of fine arts'),
        (1, 5,  'Computer Science',    'Basics of computer science, programming and computer work'),
        (2, 3,  'Anatomy',             'Human design'),
        (2, 3,  'Zoology',             'Kittens, puppies, rabbits...'),
        (3, 2,  'Dancing',             'Dancing with stars')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO university.classrooms (faculty_id, name)
    VALUES
        (1, '101'),
        (1, '102'),
        (1, '103'),
        (1, '103-A'),
        (2, '201'),
        (2, '202'),
        (2, '203'),
        (2, '204'),
        (3, '301'),
        (3, '301-A'),
        (3, '301-B'),
        (3, '301-C'),
        (4, '401'),
        (4, '402'),
        (4, '403'),
        (4, '404'),
        (5, 'Gym')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO university.students_courses (student_id, course_id)
    VALUES
        (1, 1), (1, 2), (1, 3), (1, 10), (1, 5), (1, 6), (1, 7),
        (6, 1), (6, 2), (6, 3), (6, 10), (6, 5), (6, 6), (6, 7),
        (7, 1), (7, 2), (7, 3), (7, 10), (7, 5), (7, 6), (7, 7),
        (13, 1), (13, 2), (13, 3), (13, 10), (13, 5), (13, 6), (13, 7),
        (15, 1), (15, 2), (15, 3), (15, 10), (15, 5), (15, 6), (15, 7),
        (20, 1), (20, 2), (20, 3), (20, 10), (20, 5), (20, 6), (20, 7),
        (2, 4), (2, 11), (2, 12), (2, 5), (2, 6), (2, 7),
        (4, 4), (4, 11), (4, 12), (4, 5), (4, 6), (4, 7),
        (5, 4), (5, 11), (5, 12), (5, 5), (5, 6), (5, 7),
        (8, 4), (8, 11), (8, 12), (8, 5), (8, 6), (8, 7),
        (10, 4), (10, 11), (10, 12), (10, 5), (10, 6), (10, 7),
        (16, 4), (16, 11), (16, 12), (16, 5), (16, 6), (16, 7),
        (18, 4), (18, 11), (18, 12), (18, 5), (18, 6), (18, 7),
        (3, 8), (3, 9), (3, 13), (3, 5), (3, 6), (3, 7),
        (9, 8), (9, 9), (9, 13), (9, 5), (9, 6), (9, 7),
        (11, 8), (11, 9), (11, 13), (11, 5), (11, 6), (11, 7),
        (12, 8), (12, 9), (12, 13), (12, 5), (12, 6), (12, 7),
        (14, 8), (14, 9), (14, 13), (14, 5), (14, 6), (14, 7),
        (17, 8), (17, 9), (17, 13), (17, 5), (17, 6), (17, 7),
        (19, 8), (19, 9), (19, 13), (19, 5), (19, 6), (19, 7)
    ON CONFLICT (student_id, course_id) DO NOTHING;

INSERT INTO university.events (date_of_event, start_time, end_time, classroom_id, course_id)
    VALUES
        (DATE '2022-05-30', TIME '09:00', TIME '11:00', 1, 1),
        (DATE '2022-05-30', TIME '09:00', TIME '11:00', 10, 8),
        (DATE '2022-05-30', TIME '11:00', TIME '13:00', 2, 2),
        (DATE '2022-05-30', TIME '11:00', TIME '13:00', 6, 4),
        (DATE '2022-05-30', TIME '11:00', TIME '13:00', 11, 9),
        (DATE '2022-05-30', TIME '13:00', TIME '15:00', 7, 11),
        (DATE '2022-05-30', TIME '13:00', TIME '15:00', 12, 13),
        (DATE '2022-05-30', TIME '13:00', TIME '15:00', 3, 3),
        (DATE '2022-05-30', TIME '15:00', TIME '17:00', 4, 10),
        (DATE '2022-05-30', TIME '15:00', TIME '17:00', 8, 12),
        (DATE '2022-05-30', TIME '17:00', TIME '19:00', 13, 5),

        (DATE '2022-05-31', TIME '09:00', TIME '11:00', 2, 2),
        (DATE '2022-05-31', TIME '09:00', TIME '11:00', 11, 9),
        (DATE '2022-05-31', TIME '11:00', TIME '13:00', 7, 11),
        (DATE '2022-05-31', TIME '11:00', TIME '13:00', 12, 13),
        (DATE '2022-05-31', TIME '11:00', TIME '13:00', 3, 3),
        (DATE '2022-05-31', TIME '13:00', TIME '15:00', 4, 10),
        (DATE '2022-05-31', TIME '13:00', TIME '15:00', 8, 12),
        (DATE '2022-05-31', TIME '13:00', TIME '15:00', 10, 8),
        (DATE '2022-05-31', TIME '15:00', TIME '17:00', 1, 1),
        (DATE '2022-05-31', TIME '15:00', TIME '17:00', 6, 4),
        (DATE '2022-05-31', TIME '17:00', TIME '19:00', 14, 6),

        (DATE '2022-06-01', TIME '09:00', TIME '11:00', 12, 13),
        (DATE '2022-06-01', TIME '09:00', TIME '11:00', 3, 3),
        (DATE '2022-06-01', TIME '11:00', TIME '13:00', 4, 10),
        (DATE '2022-06-01', TIME '11:00', TIME '13:00', 8, 12),
        (DATE '2022-06-01', TIME '11:00', TIME '13:00', 10, 8),
        (DATE '2022-06-01', TIME '13:00', TIME '15:00', 1, 1),
        (DATE '2022-06-01', TIME '13:00', TIME '15:00', 6, 4),
        (DATE '2022-06-01', TIME '13:00', TIME '15:00', 11, 9),
        (DATE '2022-06-01', TIME '15:00', TIME '17:00', 7, 11),
        (DATE '2022-06-01', TIME '15:00', TIME '17:00', 2, 2),
        (DATE '2022-06-01', TIME '17:00', TIME '19:00', 13, 5),

        (DATE '2022-06-02', TIME '09:00', TIME '11:00', 4, 10),
        (DATE '2022-06-02', TIME '09:00', TIME '11:00', 10, 8),
        (DATE '2022-06-02', TIME '11:00', TIME '13:00', 1, 1),
        (DATE '2022-06-02', TIME '11:00', TIME '13:00', 6, 4),
        (DATE '2022-06-02', TIME '11:00', TIME '13:00', 11, 9),
        (DATE '2022-06-02', TIME '13:00', TIME '15:00', 7, 11),
        (DATE '2022-06-02', TIME '13:00', TIME '15:00', 12, 13),
        (DATE '2022-06-02', TIME '13:00', TIME '15:00', 2, 2),
        (DATE '2022-06-02', TIME '15:00', TIME '17:00', 8, 12),
        (DATE '2022-06-02', TIME '15:00', TIME '17:00', 3, 3),
        (DATE '2022-06-02', TIME '17:00', TIME '19:00', 14, 6),

        (DATE '2022-06-03', TIME '09:00', TIME '11:00', 1, 1),
        (DATE '2022-06-03', TIME '09:00', TIME '11:00', 11, 9),
        (DATE '2022-06-03', TIME '11:00', TIME '13:00', 7, 11),
        (DATE '2022-06-03', TIME '11:00', TIME '13:00', 12, 13),
        (DATE '2022-06-03', TIME '11:00', TIME '13:00', 2, 2),
        (DATE '2022-06-03', TIME '13:00', TIME '15:00', 8, 12),
        (DATE '2022-06-03', TIME '13:00', TIME '15:00', 3, 3),
        (DATE '2022-06-03', TIME '13:00', TIME '15:00', 10, 8),
        (DATE '2022-06-03', TIME '15:00', TIME '17:00', 4, 10),
        (DATE '2022-06-03', TIME '15:00', TIME '17:00', 6, 4),
        (DATE '2022-06-03', TIME '17:00', TIME '19:00', 17, 7)
    ON CONFLICT (date_of_event, start_time, end_time, course_id) DO NOTHING;

-- adding some main user to connect the database
DROP USER IF EXISTS university_user;
CREATE USER university_user WITH PASSWORD '1111';
ALTER USER university_user SUPERUSER;



