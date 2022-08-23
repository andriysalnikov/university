package ua.com.foxminded.andriysalnikov.university.constants;

public final class DBConstants {

    public static final String JPQL_GET_ALL_FACULTIES =
            "SELECT f FROM Faculty f ORDER BY f.id";

    public static final String JPQL_GET_ALL_CLASSROOMS =
            "SELECT c FROM ClassRoom c ORDER BY c.id";

    public static final String JPQL_GET_ALL_CLASSROOMS_WITHOUT_FACULTY =
            "SELECT c FROM ClassRoom c WHERE faculty_id IS NULL ORDER BY c.id";

    public static final String JPQL_GET_ALL_COURSES =
            "SELECT c FROM Course c ORDER BY c.id";

    public static final String JPQL_GET_ALL_COURSES_WITHOUT_FACULTY =
            "SELECT c FROM Course c WHERE faculty_id IS NULL ORDER BY c.id";

    public static final String JPQL_GET_ALL_STUDENTS =
            "SELECT s FROM Student s ORDER BY s.id";

    public static final String JPQL_GET_ALL_STUDENTS_WITHOUT_FACULTY =
            "SELECT s FROM Student s WHERE faculty_id IS NULL ORDER BY s.id";

    public static final String JPQL_GET_ALL_TEACHERS =
            "SELECT t FROM Teacher t ORDER BY t.id";

    public static final String JPQL_GET_ALL_COURSES_WITHOUT_TEACHER =
            "SELECT c FROM Course c WHERE teacher_id IS NULL ORDER BY c.id";

    public static final String JPQL_GET_ALL_EVENTS =
            "SELECT e FROM Event e ORDER BY e.dayOfEvent, e.startTime";

    public static final String JPQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID =
            "SELECT e FROM Event e " +
            "WHERE e.dayOfEvent >= :startDate AND e.dayOfEvent <= :endDate " +
            "AND course_id = :courseId " +
            "ORDER BY e.dayOfEvent, e.startTime";

    private DBConstants() { }

}