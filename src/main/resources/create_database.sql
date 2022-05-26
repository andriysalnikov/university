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
    faculty_id INT NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(first_name, last_name)
);

CREATE TABLE university.courses (
    id INT GENERATED ALWAYS AS IDENTITY,
    faculty_id INT NOT NULL,
    teacher_id INT,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY(id),
    UNIQUE(name)
);

CREATE TABLE university.classrooms (
    id INT GENERATED ALWAYS AS IDENTITY,
    faculty_id INT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(name)
);

CREATE TABLE university.events (
    id INT GENERATED ALWAYS AS IDENTITY,
    date_of_event DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    classroom_id INT,
    course_id INT,
    PRIMARY KEY(id),
    UNIQUE(date_of_event, start_time, end_time, course_id)
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
        (1, 'Bogdan',       'Stupka'),
        (1, 'Taras',        'Shevchenko'),
        (1, 'Yurij',        'Gevz'),
        (1, 'Stas',         'Boklan'),
        (2, 'Marichka',     'Padalka'),
        (2, 'Petro',        'Porosheko'),
        (2, 'Ruslana',      'Pysanka'),
        (2, 'Viktor',       'Yushenko'),
        (3, 'Igor',         'Terehov'),
        (3, 'Myhaylo',      'Podolyak'),
        (3, 'Nestor',       'Mahno'),
        (3, 'Andriy',       'Parubiy'),
        (3, 'Arsen',        'Yacenuk'),
        (4, 'Olga',         'Polyakova'),
        (4, 'Nastya',       'Kamenskih'),
        (4, 'Yegor',        'Vozniuk'),
        (4, 'Sergey',       'Zalkin'),
        (4, 'Yana',         'Izmaylova'),
        (4, 'Olesya',       'Homenko')
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
        (5, 'Gym'),
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
        (4, '404')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO university.events (date_of_event, start_time, end_time, classroom_id, course_id)
    VALUES
        (DATE '2022-05-23', TIME '09:00', TIME '11:00', 2, 1),
        (DATE '2022-05-23', TIME '13:00', TIME '15:00', 3, 2),
        (DATE '2022-05-23', TIME '17:00', TIME '19:00', 4, 3),
        (DATE '2022-05-24', TIME '09:00', TIME '11:00', 5, 4),
        (DATE '2022-05-24', TIME '13:00', TIME '15:00', 6, 5),
        (DATE '2022-05-24', TIME '17:00', TIME '19:00', 7, 6),
        (DATE '2022-05-25', TIME '09:00', TIME '11:00', 1, 7),
        (DATE '2022-05-25', TIME '13:00', TIME '15:00', 8, 8),
        (DATE '2022-05-25', TIME '17:00', TIME '11:00', 9, 9),
        (DATE '2022-05-26', TIME '09:00', TIME '15:00', 10, 10),
        (DATE '2022-05-26', TIME '13:00', TIME '11:00', 11, 11),
        (DATE '2022-05-26', TIME '17:00', TIME '15:00', 12, 12),
        (DATE '2022-05-27', TIME '09:00', TIME '11:00', 14, 13),
        (DATE '2022-05-27', TIME '13:00', TIME '15:00', 13, 1),
        (DATE '2022-05-27', TIME '17:00', TIME '19:00', 12, 2)
    ON CONFLICT (date_of_event, start_time, end_time, course_id) DO NOTHING;

-- adding some main user to connect the database
DROP USER IF EXISTS university_user;
CREATE USER university_user WITH PASSWORD '1111';
ALTER USER university_user SUPERUSER;



