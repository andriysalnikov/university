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
              "ORDER BY id ASC";

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
            = "INSERT INTO university.students (faculty_id, first_name, last_name) " +
              "VALUES (?, ?, ?) " +
              "ON CONFLICT (first_name, last_name) " +
              "DO NOTHING " +
              "RETURNING id, first_name, last_name";

    public static final String SQL_GET_ALL_STUDENTS
            = "SELECT * FROM university.students " +
              "ORDER BY id ASC";

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
              "ORDER BY id ASC";

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
            = "INSERT INTO university.courses (faculty_id, teacher_id, name, description) " +
              "VALUES (?, ?, ?, ?) " +
              "ON CONFLICT (name) " +
              "DO NOTHING " +
              "RETURNING id, name, description";

    public static final String SQL_GET_ALL_COURSES
            = "SELECT * FROM university.courses " +
              "ORDER BY id ASC";

    public static final String SQL_GET_COURSE_BY_ID
            = "SELECT * FROM university.courses " +
              "WHERE id = ?";

    public static final String SQL_UPDATE_COURSE
            = "UPDATE university.courses " +
              "SET name = ?, description = ? " +
              "WHERE id = ? " +
              "RETURNING id, name, description";

    public static final String SQL_DELETE_COURSE_BY_ID
            = "DELETE FROM university.courses " +
              "WHERE id = ? " +
              "RETURNING id, name, description";

    public static final String SQL_CREATE_CLASSROOM
            = "INSERT INTO university.classrooms (faculty_id, name) " +
              "VALUES (?, ?) " +
              "ON CONFLICT (name) " +
              "DO NOTHING " +
              "RETURNING id, name";

    public static final String SQL_GET_ALL_CLASSROOMS
            = "SELECT * FROM university.classrooms " +
              "ORDER BY id ASC";

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
              "JOIN university.classrooms cl ON e.classroom_id = cl.id " +
              "JOIN university.courses c ON e.course_id = c.id";

    public static final String SQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID
            = "SELECT e.id, e.date_of_event, e.start_time, e.end_time, cl.name classroom, c.name course " +
              "FROM university.events e " +
              "JOIN university.classrooms cl ON e.classroom_id = cl.id " +
              "JOIN university.courses c ON e.course_id = c.id " +
              "WHERE e.date_of_event >= ? AND e.date_of_event <= ? AND c.id = ?";

    public static final String SQL_GET_TEACHER_COURSES_BY_TEACHER_ID
            = "SELECT c.id, name, description FROM university.teachers t " +
              "JOIN university.courses c " +
              "ON t.id = c.teacher_id " +
              "WHERE t.id = ?";

    public static final String SQL_GET_STUDENT_COURSES_BY_STUDENT_ID
            = "SELECT c.id, name, description FROM university.students_courses sc " +
              "JOIN university.courses c " +
              "ON sc.course_id = c.id " +
              "WHERE sc.student_id = ?";

    private DBConstants() { }

}