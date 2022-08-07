package ua.com.foxminded.andriysalnikov.university.constants;

public final class DBConstants {

    public static final String SQL_CREATE_TEACHER
            = "INSERT INTO university.teachers (first_name, last_name) " +
              "VALUES (?, ?) " +
              "ON CONFLICT (first_name, last_name) " +
              "DO NOTHING " +
              "RETURNING id, first_name, last_name";

    public static final String SQL_GET_ALL_TEACHERS
            = "SELECT * FROM university.teachers " +
              "ORDER BY id";

    public static final String SQL_GET_TEACHER_BY_ID
            = "SELECT * FROM university.teachers " +
              "WHERE id = ?";

    public static final String SQL_UPDATE_TEACHER
            = "UPDATE university.teachers " +
              "SET first_name = ?, last_name = ? " +
              "WHERE id = ? " +
              "RETURNING id, first_name, last_name";

    public static final String SQL_DELETE_TEACHER_BY_ID
            = "DELETE FROM university.teachers " +
              "WHERE id = ? " +
              "RETURNING id, first_name, last_name";

    public static final String SQL_CREATE_STUDENT
            = "INSERT INTO university.students (first_name, last_name) " +
              "VALUES (?, ?) " +
              "ON CONFLICT (first_name, last_name) " +
              "DO NOTHING " +
              "RETURNING id, first_name, last_name";

    public static final String SQL_GET_ALL_STUDENTS
            = "SELECT * FROM university.students " +
              "ORDER BY id";

    public static final String SQL_GET_STUDENT_BY_ID
            = "SELECT * FROM university.students " +
              "WHERE id = ?";

    public static final String SQL_UPDATE_STUDENT
            = "UPDATE university.students " +
              "SET first_name = ?, last_name = ? " +
              "WHERE id = ? " +
              "RETURNING id, first_name, last_name";

    public static final String SQL_DELETE_STUDENT_BY_ID
            = "DELETE FROM university.students " +
              "WHERE id = ? " +
              "RETURNING id, first_name, last_name";

    public static final String SQL_CREATE_FACULTY
            = "INSERT INTO university.faculties (full_name) " +
              "VALUES (?) " +
              "ON CONFLICT (full_name) " +
              "DO NOTHING " +
              "RETURNING id, full_name";

    public static final String SQL_GET_ALL_FACULTIES
            = "SELECT * FROM university.faculties " +
              "ORDER BY id";

    public static final String SQL_GET_FACULTY_BY_ID
            = "SELECT * FROM university.faculties " +
              "WHERE id = ?";

    public static final String SQL_UPDATE_FACULTY
            = "UPDATE university.faculties " +
              "SET full_name = ? " +
              "WHERE id = ? " +
              "RETURNING id, full_name";

    public static final String SQL_DELETE_FACULTY_BY_ID
            = "DELETE FROM university.faculties " +
              "WHERE id = ? " +
              "RETURNING id, full_name";

    public static final String SQL_CREATE_COURSE
            = "INSERT INTO university.courses (name, description) " +
              "VALUES (?, ?) " +
              "ON CONFLICT (name) " +
              "DO NOTHING " +
              "RETURNING id, name, description";

    public static final String SQL_GET_ALL_COURSES
            = "SELECT * FROM university.courses " +
              "ORDER BY id";

    public static final String SQL_GET_ALL_COURSES_WITHOUT_TEACHER
            = "SELECT * FROM university.courses " +
              "WHERE teacher_id IS null " +
              "ORDER BY id";

    public static final String SQL_GET_ALL_COURSES_WITHOUT_FACULTY
            = "SELECT * FROM university.courses " +
              "WHERE faculty_id IS null " +
              "ORDER BY id";

    public static final String SQL_GET_ALL_CLASSROOMS_WITHOUT_FACULTY
            = "SELECT * FROM university.classrooms " +
              "WHERE faculty_id IS null " +
              "ORDER BY id";

    public static final String SQL_GET_ALL_STUDENTS_WITHOUT_FACULTY
            = "SELECT * FROM university.students " +
              "WHERE faculty_id IS null " +
              "ORDER BY id";

    public static final String SQL_GET_COURSE_BY_ID
            = "SELECT * FROM university.courses " +
              "WHERE id = ?";

    public static final String SQL_UPDATE_COURSE
            = "UPDATE university.courses " +
              "SET name = ?, description = ? " +
              "WHERE id = ? " +
              "RETURNING id, name, description";

    public static final String SQL_SET_TEACHER_TO_COURSE
            = "UPDATE university.courses " +
              "SET teacher_id = ? " +
              "WHERE id = ? " +
              "RETURNING id, name, description";

    public static final String SQL_REMOVE_TEACHER_FROM_COURSE
            = "UPDATE university.courses " +
              "SET teacher_id = NULL " +
              "WHERE id = ? " +
              "RETURNING id, name, description";

    public static final String SQL_SET_FACULTY_TO_COURSE
            = "UPDATE university.courses " +
              "SET faculty_id = ? " +
              "WHERE id = ? " +
              "RETURNING id, name, description";

    public static final String SQL_REMOVE_FACULTY_FROM_COURSE
            = "UPDATE university.courses " +
              "SET faculty_id = NULL " +
              "WHERE id = ? " +
              "RETURNING id, name, description";

    public static final String SQL_SET_FACULTY_TO_CLASSROOM
            = "UPDATE university.classrooms " +
              "SET faculty_id = ? " +
              "WHERE id = ? " +
              "RETURNING id, name";

    public static final String SQL_REMOVE_FACULTY_FROM_CLASSROOM
            = "UPDATE university.classrooms " +
              "SET faculty_id = NULL " +
              "WHERE id = ? " +
              "RETURNING id, name";

    public static final String SQL_SET_FACULTY_TO_STUDENT
            = "UPDATE university.students " +
              "SET faculty_id = ? " +
              "WHERE id = ? " +
              "RETURNING id, first_name, last_name";

    public static final String SQL_REMOVE_FACULTY_FROM_STUDENT
            = "UPDATE university.students " +
              "SET faculty_id = NULL " +
              "WHERE id = ? " +
              "RETURNING id, first_name, last_name";

    public static final String SQL_SET_STUDENT_TO_COURSE
            = "INSERT INTO university.students_courses (student_id, course_id) " +
              "VALUES (?, ?) " +
              "ON CONFLICT (student_id, course_id) " +
              "DO NOTHING";

    public static final String SQL_REMOVE_STUDENT_FROM_COURSE
            = "DELETE FROM university.students_courses " +
              "WHERE student_id = ? AND course_id = ?";

    public static final String SQL_DELETE_COURSE_BY_ID
            = "DELETE FROM university.courses " +
              "WHERE id = ? " +
              "RETURNING id, name, description";

    public static final String SQL_CREATE_CLASSROOM
            = "INSERT INTO university.classrooms (name) " +
              "VALUES (?) " +
              "ON CONFLICT (name) " +
              "DO NOTHING " +
              "RETURNING id, name";

    public static final String SQL_GET_ALL_CLASSROOMS
            = "SELECT * FROM university.classrooms " +
              "ORDER BY id";

    public static final String SQL_GET_CLASSROOM_BY_ID
            = "SELECT * FROM university.classrooms " +
              "WHERE id = ?";

    public static final String SQL_UPDATE_CLASSROOM
            = "UPDATE university.classrooms " +
              "SET name = ? " +
              "WHERE id = ? " +
              "RETURNING id, name";

    public static final String SQL_DELETE_CLASSROOM_BY_ID
            = "DELETE FROM university.classrooms " +
              "WHERE id = ? " +
              "RETURNING id, name";

    public static final String SQL_GET_ALL_EVENTS
            = "SELECT e.id, e.date_of_event, " +
              "e.start_time, e.end_time, " +
              "cl.name classroom, c.name course " +
              "FROM university.events e " +
              "LEFT JOIN university.classrooms cl ON e.classroom_id = cl.id " +
              "JOIN university.courses c ON e.course_id = c.id " +
              "ORDER BY e.date_of_event, e.start_time, e.id";

    public static final String SQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID
            = "SELECT e.id, e.date_of_event, e.start_time, e.end_time, cl.name classroom, c.name course " +
              "FROM university.events e " +
              "LEFT JOIN university.classrooms cl ON e.classroom_id = cl.id " +
              "JOIN university.courses c ON e.course_id = c.id " +
              "WHERE e.date_of_event >= ? AND e.date_of_event <= ? AND c.id = ? " +
              "ORDER BY e.date_of_event, e.start_time, e.id";

    public static final String SQL_DELETE_EVENT_BY_ID
            = "DELETE FROM university.events " +
              "WHERE id = ? " +
              "RETURNING id, date_of_event, start_time, end_time, classroom_id, course_id";

    public static final String SQL_GET_EVENT_BY_ID
            = "SELECT * FROM university.events " +
              "WHERE id = ?";

    public static final String SQL_CREATE_EVENT
            = "INSERT INTO university.events (date_of_event, start_time, end_time, classroom_id, course_id) " +
              "VALUES (?, ?, ?, ?, ?) " +
              "ON CONFLICT (date_of_event, start_time, end_time, course_id) DO NOTHING " +
              "RETURNING id, date_of_event, start_time, end_time, classroom_id, course_id";

    public static final String SQL_UPDATE_EVENT
            = "UPDATE university.events " +
              "SET date_of_event = ?, start_time = ?, end_time = ?, classroom_id = ?, course_id = ? " +
              "WHERE id = ? " +
              "RETURNING id, date_of_event, start_time, end_time, classroom_id, course_id";

    public static final String SQL_GET_TEACHER_COURSES_BY_TEACHER_ID
            = "SELECT c.id, name, description FROM university.teachers t " +
              "JOIN university.courses c " +
              "ON t.id = c.teacher_id " +
              "WHERE t.id = ? " +
              "ORDER BY c.id";

    public static final String SQL_GET_STUDENT_COURSES_BY_STUDENT_ID
            = "SELECT c.id, name, description FROM university.students_courses sc " +
              "JOIN university.courses c " +
              "ON sc.course_id = c.id " +
              "WHERE sc.student_id = ? " +
              "ORDER BY sc.course_id";

    public static final String SQL_GET_ALL_OTHER_AVAILABLE_COURSES_FOR_STUDENT
            = "SELECT id, name, description FROM university.courses EXCEPT " +
              "(SELECT c.id, name, description FROM university.students_courses sc " +
              "JOIN university.courses c " +
              "ON sc.course_id = c.id " +
              "WHERE sc.student_id = ?) " +
              "ORDER BY id";

    public static final String SQL_GET_FACULTY_COURSES_BY_FACULTY_ID
            = "SELECT c.id, name, description FROM university.faculties f " +
              "JOIN university.courses c " +
              "ON f.id = c.faculty_id " +
              "WHERE f.id = ? " +
              "ORDER BY c.id";

    public static final String SQL_GET_FACULTY_CLASSROOMS_BY_FACULTY_ID
            = "SELECT c.id, name FROM university.faculties f " +
              "JOIN university.classrooms c " +
              "ON f.id = c.faculty_id " +
              "WHERE f.id = ? " +
              "ORDER BY c.id";

    public static final String SQL_GET_FACULTY_STUDENTS_BY_FACULTY_ID
            = "SELECT s.id, first_name, last_name FROM university.faculties f " +
              "JOIN university.students s " +
              "ON f.id = s.faculty_id " +
              "WHERE f.id = ? " +
              "ORDER BY s.id";

    private DBConstants() { }

}