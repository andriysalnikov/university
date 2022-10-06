CREATE TABLE university.students_courses (
    student_id INT REFERENCES university.students(id) ON DELETE CASCADE,
    course_id INT REFERENCES university.courses(id) ON DELETE CASCADE,
    UNIQUE (student_id, course_id)
);

INSERT INTO university.students_courses (student_id, course_id)
    VALUES
        (1, 4), (1, 5), (1, 1), (1, 2), (1, 3),
        (2, 4), (2, 5), (2, 1), (2, 2),
        (3, 4), (3, 5), (3, 1), (3, 3),
        (4, 4), (4, 5), (4, 1), (4, 3),
        (5, 4), (5, 5), (5, 2), (5, 3),
        (6, 4), (6, 5), (6, 2), (6, 3),
        (7, 4), (7, 5), (7, 3), (7, 1), (7, 2);