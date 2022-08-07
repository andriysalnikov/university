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
import ua.com.foxminded.andriysalnikov.university.model.User;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

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
    public List<Teacher> getAllTeachers() {
        LOGGER.debug(Messages.TRY_GET_ALL_TEACHERS);
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        LOGGER.debug(Messages.OK_GET_ALL_TEACHERS, teachers);
        return teachers;
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_USER_BY_ID, Teacher.class.getSimpleName(), id);
        Validation.validateId(id);
        Teacher teacher = teacherDAO.getTeacherById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_TEACHER_BY_ID);
            return new ServiceException(Messages.ERROR_GET_TEACHER_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_USER_BY_ID, Teacher.class.getSimpleName(), id, teacher);
        return teacher;
    }

    @Override
    public Teacher createTeacher(User user) {
        LOGGER.debug(Messages.TRY_CREATE_USER, Teacher.class.getSimpleName());
        Validation.validateUser(user);
        Teacher createdTeacher = teacherDAO.createTeacher((Teacher) user).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_CREATE_TEACHER);
            throw new ServiceException(Messages.ERROR_CREATE_TEACHER);
        });
        LOGGER.debug(Messages.OK_CREATE_USER, Teacher.class.getSimpleName(), createdTeacher);
        return createdTeacher;
    }

    @Override
    public Teacher deleteTeacherById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_USER_BY_ID, Teacher.class.getSimpleName(), id);
        Validation.validateId(id);
        Teacher deletedTeacher = teacherDAO.deleteTeacherById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_DELETE_TEACHER_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_TEACHER_BY_ID);
        });
        LOGGER.debug(Messages.OK_DELETE_USER_BY_ID, Teacher.class.getSimpleName(), id, deletedTeacher);
        return deletedTeacher;
    }

    @Override
    public Teacher updateTeacher(User user) {
        LOGGER.debug(Messages.TRY_UPDATE_USER, Teacher.class.getSimpleName(), user);
        Validation.validateUser(user);
        Teacher updatedTeacher = teacherDAO.updateTeacher((Teacher) user).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_UPDATE_TEACHER);
            throw new ServiceException(Messages.ERROR_UPDATE_TEACHER);
        });
        LOGGER.debug(Messages.OK_UPDATE_USER, Teacher.class.getSimpleName(), updatedTeacher);
        return updatedTeacher;
    }

    @Override
    public List<Course> getTeacherCoursesByTeacherId(Integer id) {
        LOGGER.debug(Messages.TRY_GET_USER_COURSES_BY_USER_ID, Teacher.class.getSimpleName(), id);
        Validation.validateId(id);
        List<Course> courses = teacherDAO.getTeacherCoursesByTeacherId(id);
        LOGGER.debug(Messages.OK_GET_USER_COURSES_BY_USER_ID, Teacher.class.getSimpleName(), id, courses);
        return courses;
    }

}
