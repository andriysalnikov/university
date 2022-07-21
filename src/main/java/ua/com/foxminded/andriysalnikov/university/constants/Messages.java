package ua.com.foxminded.andriysalnikov.university.constants;

public final class Messages {

    public static final String APPLICATION_STARTED =
            "Application started...";

    public static final String APPLICATION_FINISHED =
            "Application finished...";

    public static final String ERROR_ARGUMENT_NULL =
            "'id' cannot be null";

    public static final String ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO =
            "'id' cannot be less or equals 0";

    public static final String ERROR_ARGUMENT_USER =
            "'User' or 'User.id' cannot be null";

    public static final String ERROR_DATE_NULL =
            "'StartDate' or 'EndDate' or both cannot be null";

    public static final String ERROR_STARTDATE_AFTER_ENDDATE =
            "'StartDate' cannot be after 'EndDate'";

    public static final String ERROR_GET_STUDENT_BY_ID =
            "Cannot get 'Student' from storage";

    public static final String ERROR_GET_TEACHER_BY_ID =
            "Cannot get 'Teacher' from storage";

    public static final String TRY_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID =
            "Trying to get all 'Events' from '{}' to '{}' by 'Course id'={}";

    public static final String OK_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID =
            "All 'Events' from '{}' to '{}' by 'Course id'={} obtained: {}";

    public static final String TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER =
            "Trying to get 'Timetable' from '{}' to '{}' for {}";

    public static final String OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER =
            "'Timetable' from '{}' to '{}' for {} obtained: {}";

    public static final String TRY_GET_ALL_EVENTS =
            "Trying to get all 'Events'";

    public static final String OK_GET_ALL_EVENTS =
            "All 'Events' obtained: {}";

    public static final String TRY_GET_ALL_STUDENTS =
            "Trying to get all 'Students'";

    public static final String OK_GET_ALL_STUDENTS =
            "All 'Students' obtained: {}";

    public static final String TRY_GET_ALL_TEACHERS =
            "Trying to get all 'Teachers'";

    public static final String OK_GET_ALL_TEACHERS =
            "All 'Teachers' obtained: {}";

    public static final String TRY_GET_ENTITY_BY_ID =
            "Trying to get '{}' by 'id'={}";

    public static final String OK_GET_ENTITY_BY_ID =
            "'{}' obtained by 'id'={} : {}";

    public static final String TRY_GET_USER_COURSES_BY_USER_ID =
            "Trying to get '{}' 'Courses' by 'id'={}";

    public static final String OK_GET_USER_COURSES_BY_USER_ID =
            "All 'Courses' for '{}' with 'id'={} obtained: {}";

    private Messages() { }

}
