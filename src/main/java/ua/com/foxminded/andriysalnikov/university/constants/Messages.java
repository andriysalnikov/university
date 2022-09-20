package ua.com.foxminded.andriysalnikov.university.constants;

public final class Messages {

    // Common ERROR messages

    public static final String ERROR_DATE_NULL =
            "'StartDate' or 'EndDate' or both cannot be null";

    public static final String ERROR_STARTDATE_AFTER_ENDDATE =
            "'StartDate' cannot be after 'EndDate'";


    // Messages for 'Timetable'

    public static final String TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_TEACHER =
            "Trying to get 'Timetable' from '{}' to '{}' for 'Teacher'";

    public static final String OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_TEACHER =
            "'Timetable' from '{}' to '{}' for 'Teacher' obtained: {}";

    public static final String TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_STUDENT =
            "Trying to get 'Timetable' from '{}' to '{}' for 'Student'";

    public static final String OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_STUDENT =
            "'Timetable' from '{}' to '{}' for 'Student' obtained: {}";


    // Messages for 'Teachers' and 'Students'

    public static final String TRY_GET_ALL_STUDENTS =
            "Trying to get all 'Students'";

    public static final String OK_GET_ALL_STUDENTS =
            "All 'Students' obtained: {}";

    public static final String TRY_GET_ALL_TEACHERS =
            "Trying to get all 'Teachers'";

    public static final String OK_GET_ALL_TEACHERS =
            "All 'Teachers' obtained: {}";

    public static final String TRY_GET_TEACHER_BY_ID =
            "Trying to get 'Teacher' by 'id'={}";

    public static final String OK_GET_TEACHER_BY_ID =
            "'Teacher' obtained by 'id'={} : {}";

    public static final String TRY_GET_STUDENT_BY_ID =
            "Trying to get 'Student' by 'id'={}";

    public static final String OK_GET_STUDENT_BY_ID =
            "'Student' obtained by 'id'={} : {}";

    public static final String ERROR_GET_STUDENT_BY_ID =
            "Cannot get 'Student' from storage";

    public static final String ERROR_GET_TEACHER_BY_ID =
            "Cannot get 'Teacher' from storage";

    public static final String TRY_CREATE_TEACHER =
            "Trying to create a 'Teacher'";

    public static final String OK_CREATE_TEACHER =
            "'Teacher' created : {}";

    public static final String TRY_CREATE_STUDENT =
            "Trying to create a 'Student'";

    public static final String OK_CREATE_STUDENT =
            "'Student' created : {}";

    public static final String ERROR_CREATE_TEACHER =
            "Cannot create 'Teacher' in storage";

    public static final String ERROR_CREATE_TEACHER_SERVICE =
            "Cannot create 'Teacher' in storage : {}";

    public static final String ERROR_CREATE_STUDENT =
            "Cannot create 'Student' in storage";

    public static final String ERROR_CREATE_STUDENT_SERVICE =
            "Cannot create 'Student' in storage : {}";

    public static final String TRY_DELETE_TEACHER_BY_ID =
            "Trying to delete 'Teacher' by 'id'={}";

    public static final String OK_DELETE_TEACHER_BY_ID =
            "'Teacher' deleted by 'id'={}";

    public static final String TRY_DELETE_STUDENT_BY_ID =
            "Trying to delete 'Student' by 'id'={}";

    public static final String OK_DELETE_STUDENT_BY_ID =
            "'Student' deleted by 'id'={}";

    public static final String ERROR_DELETE_TEACHER_BY_ID =
            "Cannot delete 'Teacher' from storage";

    public static final String ERROR_DELETE_STUDENT_BY_ID =
            "Cannot delete 'Student' from storage";

    public static final String TRY_UPDATE_TEACHER =
            "Trying to update a 'Teacher' : {}";

    public static final String OK_UPDATE_TEACHER =
            "'Teacher' updated : {}";

    public static final String TRY_UPDATE_STUDENT =
            "Trying to update a 'Student' : {}";

    public static final String OK_UPDATE_STUDENT =
            "'Student' updated : {}";

    public static final String ERROR_UPDATE_TEACHER =
            "Cannot update 'Teacher' in storage";

    public static final String ERROR_UPDATE_TEACHER_SERVICE =
            "Cannot update 'Teacher' in storage : {}";

    public static final String ERROR_UPDATE_STUDENT =
            "Cannot update 'Student' in storage";

    public static final String ERROR_UPDATE_STUDENT_SERVICE =
            "Cannot update 'Student' in storage : {}";

    public static final String TRY_ADD_STUDENT_TO_FACULTY =
            "Trying to add 'Student'('id'={}) to 'Faculty'('id'={})";

    public static final String OK_ADD_STUDENT_TO_FACULTY =
            "It is OK adding 'Student'('id'={}) to 'Faculty'('id'={}) : {}";

    public static final String ERROR_ADD_STUDENT_TO_FACULTY =
            "Cannot add 'Student' to 'Faculty'";

    public static final String TRY_REMOVE_STUDENT_FROM_FACULTY =
            "Trying to remove 'Student'('id'={}) from  'Faculty'('id'={})";

    public static final String OK_REMOVE_STUDENT_FROM_FACULTY =
            "It is OK removing 'Student'('id'={}) from  'Faculty'('id'={}) : {}";

    public static final String ERROR_REMOVE_STUDENT_FROM_FACULTY =
            "Cannot remove 'Student' from 'Faculty'";

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
            "'Event' deleted by 'id'={}";

    public static final String ERROR_DELETE_EVENT_BY_ID =
            "Cannot delete 'Event' from storage";

    public static final String TRY_CREATE_EVENT =
            "Trying to create a 'Event'";

    public static final String OK_CREATE_EVENT =
            "'Event' created : {}";

    public static final String ERROR_CREATE_EVENT =
            "Cannot create 'Event' in storage";

    public static final String ERROR_CREATE_EVENT_SERVICE =
            "Cannot create 'Event' in storage : {}";

    public static final String TRY_UPDATE_EVENT =
            "Trying to update a 'Event' : {}";

    public static final String OK_UPDATE_EVENT =
            "'Event' updated : {}";

    public static final String ERROR_UPDATE_EVENT =
            "Cannot update 'Event' in storage";

    public static final String ERROR_UPDATE_EVENT_SERVICE =
            "Cannot update 'Event' in storage : {}";

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

    public static final String ERROR_CREATE_FACULTY_SERVICE =
            "Cannot create 'Faculty' in storage : {}";

    public static final String TRY_GET_FACULTY_BY_ID =
            "Trying to get 'Faculty' by 'id'={}";

    public static final String OK_GET_FACULTY_BY_ID =
            "'Faculty' obtained by 'id'={} : {}";

    public static final String ERROR_GET_FACULTY_BY_ID =
            "Cannot get 'Faculty' from storage";

    public static final String TRY_DELETE_FACULTY_BY_ID =
            "Trying to delete 'Faculty' by 'id'={}";

    public static final String OK_DELETE_FACULTY_BY_ID =
            "'Faculty' deleted by 'id'={}";

    public static final String ERROR_DELETE_FACULTY_BY_ID =
            "Cannot delete 'Faculty' from storage";

    public static final String TRY_UPDATE_FACULTY =
            "Trying to update a 'Faculty' : {}";

    public static final String OK_UPDATE_FACULTY =
            "'Faculty' updated : {}";

    public static final String ERROR_UPDATE_FACULTY =
            "Cannot update 'Faculty' in storage";

    public static final String ERROR_UPDATE_FACULTY_SERVICE =
            "Cannot update 'Faculty' in storage : {}";

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

    public static final String ERROR_CREATE_CLASSROOM_SERVICE =
            "Cannot create 'Classroom' in storage : {}";

    public static final String TRY_DELETE_CLASSROOM_BY_ID =
            "Trying to delete 'Classroom' by 'id'={}";

    public static final String OK_DELETE_CLASSROOM_BY_ID =
            "'Classroom' deleted by 'id'={}";

    public static final String ERROR_DELETE_CLASSROOM_BY_ID =
            "Cannot delete 'Classroom' from storage";

    public static final String TRY_UPDATE_CLASSROOM =
            "Trying to update a 'Classroom' : {}";

    public static final String OK_UPDATE_CLASSROOM =
            "'Classroom' updated : {}";

    public static final String ERROR_UPDATE_CLASSROOM =
            "Cannot update 'Classroom' in storage";

    public static final String ERROR_UPDATE_CLASSROOM_SERVICE =
            "Cannot update 'Classroom' in storage : {}";

    public static final String TRY_ADD_CLASSROOM_TO_FACULTY =
            "Trying to add 'Classroom'('id'={}) to 'Faculty'('id'={})";

    public static final String OK_ADD_CLASSROOM_TO_FACULTY =
            "It is OK adding 'Classroom'('id'={}) to 'Faculty'('id'={}) : {}";

    public static final String ERROR_ADD_CLASSROOM_TO_FACULTY =
            "Cannot add 'Classroom' to 'Faculty'";

    public static final String TRY_REMOVE_CLASSROOM_FROM_FACULTY =
            "Trying to remove 'Classroom'('id'={}) from 'Faculty'('id'={})";

    public static final String OK_REMOVE_CLASSROOM_FROM_FACULTY =
            "It is OK removing 'Classroom'('id'={}) from 'Faculty'('id'={}) : {}";

    public static final String ERROR_REMOVE_CLASSROOM_FROM_FACULTY =
            "Cannot remove 'Classroom' from 'Faculty'";

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

    public static final String ERROR_CREATE_COURSE_SERVICE =
            "Cannot create 'Course' in storage : {}";

    public static final String TRY_DELETE_COURSE_BY_ID =
            "Trying to delete 'Course' by 'id'={}";

    public static final String OK_DELETE_COURSE_BY_ID =
            "'Course' deleted by 'id'={}";

    public static final String ERROR_DELETE_COURSE_BY_ID =
            "Cannot delete 'Course' from storage";

    public static final String TRY_UPDATE_COURSE =
            "Trying to update a 'Course' : {}";

    public static final String OK_UPDATE_COURSE =
            "'Course' updated : {}";

    public static final String ERROR_UPDATE_COURSE =
            "Cannot update 'Course' in storage";

    public static final String ERROR_UPDATE_COURSE_SERVICE =
            "Cannot update 'Course' in storage : {}";

    public static final String TRY_ADD_COURSE_TO_TEACHER =
            "Trying to add 'Course'('id'={}) to 'Teacher'('id'={})";

    public static final String OK_ADD_COURSE_TO_TEACHER =
            "It is OK adding 'Course'('id'={}) to 'Teacher'('id'={}) : {}";

    public static final String ERROR_ADD_COURSE_TO_TEACHER =
            "Cannot add 'Course' to 'Teacher'";

    public static final String TRY_REMOVE_COURSE_FROM_TEACHER =
            "Trying to remove 'Course'('id'={}) from 'Teacher'('id'={})";

    public static final String OK_REMOVE_COURSE_FROM_TEACHER =
            "It is OK removing 'Course'('id'={}) from 'Teacher'('id'={}) : {}";

    public static final String ERROR_REMOVE_COURSE_FROM_TEACHER =
            "Cannot remove 'Course' from 'Teacher'";

    public static final String TRY_ADD_COURSE_TO_FACULTY =
            "Trying to add 'Course'('id'={}) to 'Faculty'('id'={})";

    public static final String OK_ADD_COURSE_TO_FACULTY =
            "It is OK adding 'Course'('id'={}) to 'Faculty'('id'={}) : {}";

    public static final String ERROR_ADD_COURSE_TO_FACULTY =
            "Cannot add 'Course' to 'Faculty'";

    public static final String TRY_REMOVE_COURSE_FROM_FACULTY =
            "Trying to remove 'Course'('id'={}) from 'Faculty'('id'={})";

    public static final String OK_REMOVE_COURSE_FROM_FACULTY =
            "It is OK removing 'Course'('id'={}) from 'Faculty'('id'={}) : {}";

    public static final String ERROR_REMOVE_COURSE_FROM_FACULTY =
            "Cannot remove 'Course' from 'FACULTY'";

    public static final String TRY_ADD_COURSE_TO_STUDENT =
            "Trying to add 'Course'('id'={}) to 'Student'('id'={})";

    public static final String OK_ADD_COURSE_TO_STUDENT =
            "It is OK adding 'Course'('id'={}) to 'Student'('id'={}) : {}";

    public static final String TRY_REMOVE_COURSE_FROM_STUDENT =
            "Trying to remove 'Course'('id'={}) from 'Student'('id'={})";

    public static final String OK_REMOVE_COURSE_FROM_STUDENT =
            "It is OK removing 'Course'('id'={}) from 'Student'('id'={}) : {}";

    private Messages() { }

}
