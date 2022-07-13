package ua.com.foxminded.andriysalnikov.university;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.andriysalnikov.university.config.SpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.impl.StudentServiceImpl;

import java.util.List;

public class UniversityApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityApplication.class);

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {

        LOGGER.info(Messages.APPLICATION_STARTED);

        context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);

        StudentService studentService = context.getBean(StudentServiceImpl.class);


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




        context.close();
        LOGGER.info(Messages.APPLICATION_FINISHED);

    }

}
