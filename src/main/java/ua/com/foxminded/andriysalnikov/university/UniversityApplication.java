package ua.com.foxminded.andriysalnikov.university;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.andriysalnikov.university.config.SpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.impl.StudentServiceImpl;

public class UniversityApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityApplication.class);

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {

        LOGGER.info(Messages.APPLICATION_STARTED);

        context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);

        StudentService studentService = context.getBean(StudentServiceImpl.class);

        Student student = null;
        LOGGER.info(Messages.TRY_GET_ENTITY_BY_ID, Student.class.getSimpleName(), -1);
        try {
            student = studentService.getStudentById(-1);
            LOGGER.info(Messages.ENTITY_GOTTEN_BY_ID, Student.class.getSimpleName(), -1);
        } catch (ServiceException serviceException) {
            LOGGER.error("Oooops!", serviceException);
        }

        LOGGER.info(Messages.TRY_GET_ENTITY_BY_ID, Student.class.getSimpleName(), 1);
        try {
            student = studentService.getStudentById(1);
            LOGGER.info(Messages.ENTITY_GOTTEN_BY_ID, Student.class.getSimpleName(), 1);
        } catch (ServiceException serviceException) {
            LOGGER.error("Oooops!", serviceException);
        }

        context.close();
        LOGGER.info(Messages.APPLICATION_FINISHED);

    }

}
