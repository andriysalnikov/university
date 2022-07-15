package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public Student getStudentById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_ENTITY_BY_ID, Student.class.getSimpleName(), id);
        if (id == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_NULL);
            throw new ServiceException(Messages.ERROR_ARGUMENT_NULL);
        }
        if (id <= 0) {
            LOGGER.error(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
            throw new ServiceException(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
        }
        Student student = studentDAO.getStudentById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_STUDENT_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_STUDENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_ENTITY_BY_ID, Student.class.getSimpleName(), id, student);
        return student;
    }

    @Override
    public List<Course> getStudentCoursesByStudentId(Integer id) {
        LOGGER.debug(Messages.TRY_GET_USER_COURSES_BY_USER_ID, Student.class.getSimpleName(), id);
        if (id == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_NULL);
            throw new ServiceException(Messages.ERROR_ARGUMENT_NULL);
        }
        if (id <= 0) {
            LOGGER.error(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
            throw new ServiceException(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
        }
        List<Course> courses = studentDAO.getStudentCoursesByStudentId(id);
        LOGGER.debug(Messages.OK_GET_USER_COURSES_BY_USER_ID, Student.class.getSimpleName(), id, courses);
        return courses;
    }

}
