package ua.com.foxminded.andriysalnikov.university;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.andriysalnikov.university.config.SpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.EventService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;
import ua.com.foxminded.andriysalnikov.university.service.impl.EventServiceImpl;
import ua.com.foxminded.andriysalnikov.university.service.impl.StudentServiceImpl;
import ua.com.foxminded.andriysalnikov.university.service.impl.TeacherServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class UniversityApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityApplication.class);

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {

        LOGGER.info(Messages.APPLICATION_STARTED);

        context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);

        StudentService studentService = context.getBean(StudentServiceImpl.class);
        TeacherService teacherService = context.getBean(TeacherServiceImpl.class);
        EventService eventService = context.getBean(EventServiceImpl.class);

        Integer studentId = 1;
        Student student;
        LOGGER.info(Messages.TRY_GET_ENTITY_BY_ID, Student.class.getSimpleName(), studentId);
        try {
            student = studentService.getStudentById(studentId);
            LOGGER.info(Messages.OK_GET_ENTITY_BY_ID,
                    Student.class.getSimpleName(), studentId, student);
        } catch (ServiceException serviceException) {
            LOGGER.error(serviceException.getMessage(), serviceException);
        }
        List<Course> courses;
        LOGGER.info(Messages.TRY_GET_USER_COURSES_BY_USER_ID, Student.class.getSimpleName(), studentId);
        try {
            courses = studentService.getStudentCoursesByStudentId(studentId);
            LOGGER.info(Messages.OK_GET_USER_COURSES_BY_USER_ID,
                    Student.class.getSimpleName(), studentId, courses);
        } catch (ServiceException serviceException) {
            LOGGER.error(serviceException.getMessage(), serviceException);
        }


        Integer teacherId = 3;
        Teacher teacher;
        LOGGER.info(Messages.TRY_GET_ENTITY_BY_ID, Teacher.class.getSimpleName(), studentId);
        try {
            teacher = teacherService.getTeacherById(teacherId);
            LOGGER.info(Messages.OK_GET_ENTITY_BY_ID,
                    Teacher.class.getSimpleName(), teacherId, teacher);
        } catch (ServiceException serviceException) {
            LOGGER.error(serviceException.getMessage(), serviceException);
        }

        LOGGER.info(Messages.TRY_GET_USER_COURSES_BY_USER_ID, Teacher.class.getSimpleName(), teacherId);
        try {
            courses = teacherService.getTeacherCoursesByTeacherId(teacherId);
            LOGGER.info(Messages.OK_GET_USER_COURSES_BY_USER_ID,
                    Teacher.class.getSimpleName(), teacherId, courses);
        } catch (ServiceException serviceException) {
            LOGGER.error(serviceException.getMessage(), serviceException);
        }


        LOGGER.info(Messages.TRY_GET_ALL_EVENTS);
        List<Event> events = eventService.getAllEvents();
        LOGGER.info(Messages.OK_GET_ALL_EVENTS, events);

        Integer courseId = 5;
        LocalDate startDate = LocalDate.of(2022, 5, 30);
        LocalDate endDate = LocalDate.of(2022, 6, 3);
        LOGGER.info(Messages.TRY_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                startDate, endDate, courseId);
        try {
            events = eventService.getAllEventsFromStartDateToEndDateByCourseId(
                    startDate, endDate, courseId);
            LOGGER.info(Messages.OK_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                    startDate, endDate, courseId, events);
        } catch (ServiceException serviceException) {
            LOGGER.error(serviceException.getMessage(), serviceException);
        }




        context.close();
        LOGGER.info(Messages.APPLICATION_FINISHED);

    }

}
