package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.TeacherDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final TeacherDAO teacherDAO;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_ENTITY_BY_ID, Teacher.class.getSimpleName(), id);
        if (id == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_NULL);
            throw new ServiceException(Messages.ERROR_ARGUMENT_NULL);
        }
        if (id <= 0) {
            LOGGER.error(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
            throw new ServiceException(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
        }
        Teacher teacher = teacherDAO.getTeacherById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_TEACHER_BY_ID);
            return new ServiceException(Messages.ERROR_GET_TEACHER_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_ENTITY_BY_ID, Teacher.class.getSimpleName(), id, teacher);
        return teacher;
    }

    @Override
    public List<Course> getTeacherCoursesByTeacherId(Integer id) {
        LOGGER.debug(Messages.TRY_GET_USER_COURSES_BY_USER_ID, Teacher.class.getSimpleName(), id);
        if (id == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_NULL);
            throw new ServiceException(Messages.ERROR_ARGUMENT_NULL);
        }
        if (id <= 0) {
            LOGGER.error(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
            throw new ServiceException(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
        }
        List<Course> courses = teacherDAO.getTeacherCoursesByTeacherId(id);
        LOGGER.debug(Messages.OK_GET_USER_COURSES_BY_USER_ID, Teacher.class.getSimpleName(), id, courses);
        return courses;
    }

}
