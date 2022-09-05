package ua.com.foxminded.andriysalnikov.university.constants;

public final class DBConstants {

    public static final String JPQL_GET_FACULTY_BY_ID_WITH_COURSES =
            "SELECT f FROM Faculty f " +
            "LEFT JOIN FETCH f.courses c " +
            "WHERE f.id = :id";

    public static final String JPQL_GET_FACULTY_BY_ID_WITH_CLASSROOMS =
            "SELECT f FROM Faculty f " +
            "LEFT JOIN FETCH f.classRooms c " +
            "WHERE f.id = :id";

    public static final String JPQL_GET_FACULTY_BY_ID_WITH_STUDENTS =
            "SELECT f FROM Faculty f " +
            "LEFT JOIN FETCH f.students s " +
            "WHERE f.id = :id";

    public static final String JPQL_GET_STUDENT_BY_ID_WITH_COURSES =
            "SELECT s FROM Student s " +
            "LEFT JOIN FETCH s.courses c " +
            "WHERE s.id = :id";

    public static final String JPQL_GET_TEACHER_BY_ID_WITH_COURSES =
            "SELECT t FROM Teacher t " +
            "LEFT JOIN FETCH t.courses c " +
            "WHERE t.id = :id";

    public static final String JPQL_GET_ALL_EVENTS =
            "SELECT e FROM Event e " +
            "JOIN FETCH e.course c " +
            "LEFT JOIN FETCH  e.classRoom cl " +
            "ORDER BY e.dayOfEvent, e.startTime";

    public static final String JPQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID =
            "SELECT e FROM Event e " +
            "JOIN FETCH e.course c " +
            "LEFT JOIN FETCH  e.classRoom cl " +
            "WHERE e.dayOfEvent >= :startDate AND e.dayOfEvent <= :endDate " +
            "AND course_id = :courseId " +
            "ORDER BY e.dayOfEvent, e.startTime";

    private DBConstants() { }

}