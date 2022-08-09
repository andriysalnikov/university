package ua.com.foxminded.andriysalnikov.university.constants;

public final class Messages {

    // Common ERROR messages

    public static final String ERROR_ARGUMENT_NULL =
            "'id' cannot be null";

    public static final String ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO =
            "'id' cannot be less or equals 0";

    public static final String ERROR_DATE_NULL =
            "'StartDate' or 'EndDate' or both cannot be null";

    public static final String ERROR_STARTDATE_AFTER_ENDDATE =
            "'StartDate' cannot be after 'EndDate'";


    // Messages for 'DataSource'

    public static final String TRY_OBTAIN_DATASOURCE =
            "Trying to obtain a DataSource";

    public static final String OK_OBTAIN_DATASOURCE =
            "DataSource obtained";

    public static final String ERROR_OBTAIN_DATASOURCE =
            "Cannot obtain a DataSource";


    // Messages for 'Timetable'

    public static final String TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER =
            "Trying to get 'Timetable' from '{}' to '{}' for {}";

    public static final String OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER =
            "'Timetable' from '{}' to '{}' for {} obtained: {}";


    // Messages for 'Users'

    public static final String TRY_GET_ALL_STUDENTS =
            "Trying to get all 'Students'";

    public static final String OK_GET_ALL_STUDENTS =
            "All 'Students' obtained: {}";

    public static final String TRY_GET_ALL_TEACHERS =
            "Trying to get all 'Teachers'";

    public static final String OK_GET_ALL_TEACHERS =
            "All 'Teachers' obtained: {}";

    public static final String TRY_GET_USER_BY_ID =
            "Trying to get '{}' by 'id'={}";

    public static final String OK_GET_USER_BY_ID =
            "'{}' obtained by 'id'={} : {}";

    public static final String ERROR_GET_STUDENT_BY_ID =
            "Cannot get 'Student' from storage";

    public static final String ERROR_GET_TEACHER_BY_ID =
            "Cannot get 'Teacher' from storage";

    public static final String TRY_CREATE_USER =
            "Trying to create a '{}'";

    public static final String OK_CREATE_USER =
            "'{}' created : {}";

    public static final String ERROR_CREATE_TEACHER =
            "Cannot create 'Teacher' in storage";

    public static final String ERROR_CREATE_STUDENT =
            "Cannot create 'Student' in storage";

    public static final String ERROR_ARGUMENT_USER =
            "'User' or 'User.firstName' or 'User.lastName' cannot be null";

    public static final String TRY_DELETE_USER_BY_ID =
            "Trying to delete '{}' by 'id'={}";

    public static final String OK_DELETE_USER_BY_ID =
            "'{}' delete by 'id'={} : {}";

    public static final String ERROR_DELETE_TEACHER_BY_ID =
            "Cannot delete 'Teacher' from storage";

    public static final String ERROR_DELETE_STUDENT_BY_ID =
            "Cannot delete 'Student' from storage";

    public static final String TRY_UPDATE_USER =
            "Trying to update a '{}' : {}";

    public static final String OK_UPDATE_USER =
            "Trying to update a '{}' : {}";

    public static final String ERROR_UPDATE_TEACHER =
            "Cannot update 'Teacher' in storage";

    public static final String ERROR_UPDATE_STUDENT =
            "Cannot update 'Student' in storage";

    public static final String TRY_GET_USER_COURSES_BY_USER_ID =
            "Trying to get '{}' 'Courses' by 'id'={}";

    public static final String OK_GET_USER_COURSES_BY_USER_ID =
            "All 'Courses' for '{}' with 'id'={} obtained: {}";

    public static final String TRY_SET_FACULTY_TO_STUDENT =
            "Trying to set 'Faculty'('id'={}) to 'Student'('id'={})";

    public static final String OK_SET_FACULTY_TO_STUDENT =
            "It is OK setting 'Faculty'('id'={}) to 'Student'('id'={}) : {}";

    public static final String ERROR_SET_FACULTY_TO_STUDENT =
            "Cannot set 'Faculty' to 'Student'";

    public static final String TRY_REMOVE_FACULTY_FROM_STUDENT =
            "Trying to remove 'Faculty' from 'Student'('id'={})";

    public static final String OK_REMOVE_FACULTY_FROM_STUDENT =
            "It is OK removing 'Faculty' from 'Student'('id'={}) : {}";

    public static final String ERROR_REMOVE_FACULTY_FROM_STUDENT =
            "Cannot remove 'Faculty' from 'Student'";

    public static final String TRY_GET_ALL_STUDENTS_WITHOUT_FACULTY =
            "Trying to get all 'Students' without 'Faculty'";

    public static final String OK_GET_ALL_STUDENTS_WITHOUT_FACULTY =
            "All 'Students' without 'Faculty' obtained: {}";


    // Messages for 'Events'

    public static final String TRY_GET_ALL_EVENTS =
            "Trying to get all 'Events'";

    public static final String OK_GET_ALL_EVENTS =
            "All 'Events' obtained: {}";

    public static final String TRY_DELETE_EVENT_BY_ID =
            "Trying to delete 'Event' by 'id'={}";

    public static final String OK_DELETE_EVENT_BY_ID =
            "'Event' delete by 'id'={} : {}";

    public static final String ERROR_DELETE_EVENT_BY_ID =
            "Cannot delete 'Event' from storage";

    public static final String TRY_CREATE_EVENT =
            "Trying to create a 'Event'";

    public static final String OK_CREATE_EVENT =
            "'Event' created : {}";

    public static final String ERROR_CREATE_EVENT =
            "Cannot create 'Event' in storage";

    public static final String ERROR_ARGUMENT_EVENT =
            "'Event' or 'Event.dayOfEvent' or 'Event.startTime' or 'Event.endTime' cannot be null";

    public static final String TRY_UPDATE_EVENT =
            "Trying to update a 'Event' : {}";

    public static final String OK_UPDATE_EVENT =
            "Trying to update a 'Event' : {}";

    public static final String ERROR_UPDATE_EVENT =
            "Cannot update 'Event' in storage";

    public static final String TRY_GET_EVENT_BY_ID =
            "Trying to get 'Event' by 'id'={}";

    public static final String OK_GET_EVENT_BY_ID =
            "'Event' obtained by 'id'={} : {}";

    public static final String ERROR_GET_EVENT_BY_ID =
            "Cannot get 'Event' from storage";

    public static final String TRY_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID =
            "Trying to get all 'Events' from '{}' to '{}' by 'Course id'={}";

    public static final String OK_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID =
            "All 'Events' from '{}' to '{}' by 'Course id'={} obtained: {}";


    // Messages for 'Faculties'

    public static final String TRY_GET_ALL_FACULTIES =
            "Trying to get all 'Faculties'";

    public static final String OK_GET_ALL_FACULTIES =
            "All 'Faculties' obtained: {}";

    public static final String TRY_CREATE_FACULTY =
            "Trying to create a 'Faculty'";

    public static final String OK_CREATE_FACULTY =
            "'Faculty' created : {}";

    public static final String ERROR_CREATE_FACULTY =
            "Cannot create 'Faculty' in storage";

    public static final String TRY_GET_FACULTY_BY_ID =
            "Trying to get 'Faculty' by 'id'={}";

    public static final String OK_GET_FACULTY_BY_ID =
            "'Faculty' obtained by 'id'={} : {}";

    public static final String ERROR_GET_FACULTY_BY_ID =
            "Cannot get 'Faculty' from storage";

    public static final String TRY_DELETE_FACULTY_BY_ID =
            "Trying to delete 'Faculty' by 'id'={}";

    public static final String OK_DELETE_FACULTY_BY_ID =
            "'Faculty' delete by 'id'={} : {}";

    public static final String ERROR_DELETE_FACULTY_BY_ID =
            "Cannot delete 'Faculty' from storage";

    public static final String TRY_UPDATE_FACULTY =
            "Trying to update a 'Faculty' : {}";

    public static final String OK_UPDATE_FACULTY =
            "Trying to update a 'Faculty' : {}";

    public static final String ERROR_UPDATE_FACULTY =
            "Cannot update 'Faculty' in storage";

    public static final String ERROR_ARGUMENT_FACULTY =
            "'Faculty' or 'Faculty.fullName' cannot be null";

    public static final String TRY_GET_FACULTY_COURSES_BY_FACULTY_ID =
            "Trying to get 'Faculty' 'Courses' by 'id'={}";

    public static final String OK_GET_FACULTY_COURSES_BY_FACULTY_ID =
            "All 'Courses' for 'Faculty' with 'id'={} obtained: {}";

    public static final String TRY_GET_FACULTY_CLASSROOMS_BY_FACULTY_ID =
            "Trying to get 'Faculty' 'Classrooms' by 'id'={}";

    public static final String OK_GET_FACULTY_CLASSROOMS_BY_FACULTY_ID =
            "All 'Classrooms' for 'Faculty' with 'id'={} obtained: {}";

    public static final String TRY_GET_FACULTY_STUDENTS_BY_FACULTY_ID =
            "Trying to get 'Faculty' 'Students' by 'id'={}";

    public static final String OK_GET_FACULTY_STUDENTS_BY_FACULTY_ID =
            "All 'Students' for 'Faculty' with 'id'={} obtained: {}";


    // Messages for 'Classrooms'

    public static final String TRY_GET_ALL_CLASSROOMS =
            "Trying to get all 'Classrooms'";

    public static final String OK_GET_ALL_CLASSROOMS =
            "All 'Classrooms' obtained: {}";

    public static final String TRY_GET_CLASSROOM_BY_ID =
            "Trying to get 'Classroom' by 'id'={}";

    public static final String OK_GET_CLASSROOM_BY_ID =
            "'Classroom' obtained by 'id'={} : {}";

    public static final String ERROR_GET_CLASSROOM_BY_ID =
            "Cannot get 'Classroom' from storage";

    public static final String TRY_CREATE_CLASSROOM =
            "Trying to create a 'Classroom'";

    public static final String OK_CREATE_CLASSROOM =
            "'Classroom' created : {}";

    public static final String ERROR_CREATE_CLASSROOM =
            "Cannot create 'Classroom' in storage";

    public static final String TRY_DELETE_CLASSROOM_BY_ID =
            "Trying to delete 'Classroom' by 'id'={}";

    public static final String OK_DELETE_CLASSROOM_BY_ID =
            "'Classroom' delete by 'id'={} : {}";

    public static final String ERROR_DELETE_CLASSROOM_BY_ID =
            "Cannot delete 'Classroom' from storage";

    public static final String TRY_UPDATE_CLASSROOM =
            "Trying to update a 'Classroom' : {}";

    public static final String OK_UPDATE_CLASSROOM =
            "Trying to update a 'Classroom' : {}";

    public static final String ERROR_UPDATE_CLASSROOM =
            "Cannot update 'Classroom' in storage";

    public static final String ERROR_ARGUMENT_CLASSROOM =
            "'Classroom' or 'Classroom.name' cannot be null";

    public static final String TRY_SET_FACULTY_TO_CLASSROOM =
            "Trying to set 'Faculty'('id'={}) to 'Classroom'('id'={})";

    public static final String OK_SET_FACULTY_TO_CLASSROOM =
            "It is OK setting 'Faculty'('id'={}) to 'Classroom'('id'={}) : {}";

    public static final String ERROR_SET_FACULTY_TO_CLASSROOM =
            "Cannot set 'Faculty' to 'Course'";

    public static final String TRY_REMOVE_FACULTY_FROM_CLASSROOM =
            "Trying to remove 'Faculty' from 'Classroom'('id'={})";

    public static final String OK_REMOVE_FACULTY_FROM_CLASSROOM =
            "It is OK removing 'Faculty' from 'Classroom'('id'={}) : {}";

    public static final String ERROR_REMOVE_FACULTY_FROM_CLASSROOM =
            "Cannot remove 'Faculty' from 'Classroom'";

    public static final String TRY_GET_ALL_CLASSROOMS_WITHOUT_FACULTY =
            "Trying to get all 'Classrooms' without 'Faculty'";

    public static final String OK_GET_ALL_CLASSROOMS_WITHOUT_FACULTY =
            "All 'Classrooms' without 'Faculty' obtained: {}";


    // Messages for 'Courses'

    public static final String TRY_GET_ALL_COURSES =
            "Trying to get all 'Courses'";

    public static final String OK_GET_ALL_COURSES =
            "All 'Courses' obtained: {}";

    public static final String TRY_GET_ALL_COURSES_WITHOUT_TEACHER =
            "Trying to get all 'Courses' without 'Teacher'";

    public static final String OK_GET_ALL_COURSES_WITHOUT_TEACHER =
            "All 'Courses' without 'Teacher' obtained: {}";

    public static final String TRY_GET_ALL_COURSES_WITHOUT_FACULTY =
            "Trying to get all 'Courses' without 'Faculty'";

    public static final String OK_GET_ALL_COURSES_WITHOUT_FACULTY =
            "All 'Courses' without 'Faculty' obtained: {}";

    public static final String TRY_GET_ALL_OTHER_AVAILABLE_COURSES_FOR_STUDENT =
            "Trying to get all other available 'Courses' for 'Student'('id'={})";

    public static final String OK_GET_ALL_OTHER_AVAILABLE_COURSES_FOR_STUDENT =
            "All other available 'Courses' for 'Student'('id'={}) obtained: {}";

    public static final String TRY_GET_COURSE_BY_ID =
            "Trying to get 'Course' by 'id'={}";

    public static final String OK_GET_COURSE_BY_ID =
            "'Course' obtained by 'id'={} : {}";

    public static final String ERROR_GET_COURSE_BY_ID =
            "Cannot get 'Course' from storage";

    public static final String TRY_CREATE_COURSE =
            "Trying to create a 'Course'";

    public static final String OK_CREATE_COURSE =
            "'Course' created : {}";

    public static final String ERROR_CREATE_COURSE =
            "Cannot create 'Course' in storage";

    public static final String TRY_DELETE_COURSE_BY_ID =
            "Trying to delete 'Course' by 'id'={}";

    public static final String OK_DELETE_COURSE_BY_ID =
            "'Course' delete by 'id'={} : {}";

    public static final String ERROR_DELETE_COURSE_BY_ID =
            "Cannot delete 'Course' from storage";

    public static final String TRY_UPDATE_COURSE =
            "Trying to update a 'Course' : {}";

    public static final String OK_UPDATE_COURSE =
            "Trying to update a 'Course' : {}";

    public static final String ERROR_UPDATE_COURSE =
            "Cannot update 'Course' in storage";

    public static final String ERROR_ARGUMENT_COURSE =
            "'Course' or 'Course.name' cannot be null";

    public static final String TRY_SET_TEACHER_TO_COURSE =
            "Trying to set 'Teacher'('id'={}) to 'Course'('id'={})";

    public static final String OK_SET_TEACHER_TO_COURSE =
            "It is OK setting 'Teacher'('id'={}) to 'Course'('id'={}) : {}";

    public static final String ERROR_SET_TEACHER_TO_COURSE =
            "Cannot set 'Teacher' to 'Course'";

    public static final String TRY_REMOVE_TEACHER_FROM_COURSE =
            "Trying to remove 'Teacher' from 'Course'('id'={})";

    public static final String OK_REMOVE_TEACHER_FROM_COURSE =
            "It is OK removing 'Teacher' from 'Course'('id'={}) : {}";

    public static final String ERROR_REMOVE_TEACHER_FROM_COURSE =
            "Cannot remove 'Teacher' from 'Course'";

    public static final String TRY_SET_FACULTY_TO_COURSE =
            "Trying to set 'Faculty'('id'={}) to 'Course'('id'={})";

    public static final String OK_SET_FACULTY_TO_COURSE =
            "It is OK setting 'Faculty'('id'={}) to 'Course'('id'={}) : {}";

    public static final String ERROR_SET_FACULTY_TO_COURSE =
            "Cannot set 'Faculty' to 'Course'";

    public static final String TRY_REMOVE_FACULTY_FROM_COURSE =
            "Trying to remove 'Faculty' from 'Course'('id'={})";

    public static final String OK_REMOVE_FACULTY_FROM_COURSE =
            "It is OK removing 'Faculty' from 'Course'('id'={}) : {}";

    public static final String ERROR_REMOVE_FACULTY_FROM_COURSE =
            "Cannot remove 'Faculty' from 'Course'";

    public static final String TRY_SET_STUDENT_TO_COURSE =
            "Trying to set 'Student'('id'={}) to 'Course'('id'={})";

    public static final String OK_SET_STUDENT_TO_COURSE =
            "It is OK setting 'Student'('id'={}) to 'Course'('id'={})";

    public static final String ERROR_SET_STUDENT_TO_COURSE =
            "Cannot set 'Student' to 'Course'";

    public static final String TRY_REMOVE_STUDENT_FROM_COURSE =
            "Trying to remove 'Student'('id'={}) from 'Course'('id'={})";

    public static final String OK_REMOVE_STUDENT_FROM_COURSE =
            "It is OK removing 'Student'('id'={}) from 'Course'('id'={})";

    public static final String ERROR_REMOVE_STUDENT_FROM_COURSE =
            "Cannot remove 'Student' from 'Course'";

    public static final String TRY_SET_USER_TO_COURSE =
            "Trying to set '{}'('id'={}) to 'Course'('id'={})";

    public static final String OK_SET_USER_TO_COURSE =
            "It is OK setting '{}'('id'={}) to 'Course'('id'={})";

    public static final String TRY_REMOVE_USER_FROM_COURSE =
            "Trying to remove '{}'('id'={}) from 'Course'('id'={})";

    public static final String OK_REMOVE_USER_FROM_COURSE =
            "It is OK removing '{}'('id'={}) from 'Course'('id'={})";

    private Messages() { }

}
