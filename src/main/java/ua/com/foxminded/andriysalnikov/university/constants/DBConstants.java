package ua.com.foxminded.andriysalnikov.university.constants;

public final class DBConstants {

    public static final String HQL_GET_ALL_FACULTIES =
            "FROM Faculty ORDER BY id";

    public static final String HQL_GET_ALL_CLASSROOMS =
            "FROM ClassRoom ORDER BY id";

    public static final String HQL_GET_ALL_CLASSROOMS_WITHOUT_FACULTY =
            "FROM ClassRoom WHERE faculty_id IS NULL ORDER BY id";

    public static final String HQL_GET_ALL_COURSES =
            "FROM Course ORDER BY id";

    public static final String HQL_GET_ALL_COURSES_WITHOUT_FACULTY =
            "FROM Course WHERE faculty_id IS NULL ORDER BY id";

    public static final String HQL_GET_ALL_STUDENTS =
            "FROM Student ORDER BY id";

    public static final String HQL_GET_ALL_STUDENTS_WITHOUT_FACULTY =
            "FROM Student WHERE faculty_id IS NULL ORDER BY id";

    public static final String HQL_GET_ALL_TEACHERS =
            "FROM Teacher ORDER BY id";

    public static final String HQL_GET_ALL_COURSES_WITHOUT_TEACHER =
            "FROM Course WHERE teacher_id IS NULL ORDER BY id";

    public static final String HQL_GET_ALL_EVENTS =
            "FROM Event ORDER BY date_of_event, start_time";

    public static final String HQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID =
            "FROM Event WHERE date_of_event >= :startDate AND date_of_event <= :endDate " +
            "AND course_id = :courseId " +
            "ORDER BY date_of_event, start_time";

    private DBConstants() { }

}