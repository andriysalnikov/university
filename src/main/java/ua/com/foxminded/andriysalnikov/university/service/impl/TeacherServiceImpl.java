package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.TeacherDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.util.List;

@Service
@Transactional
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
        LOGGER.debug(Messages.TRY_GET_TEACHER_BY_ID, id);
        Validation.validateId(id);
        Teacher teacher = teacherDAO.getTeacherById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_TEACHER_BY_ID);
            return new ServiceException(Messages.ERROR_GET_TEACHER_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_TEACHER_BY_ID, id, teacher);
        return teacher;
    }

    @Override
    public Teacher getTeacherByIdWithCourses(Integer id) {
        LOGGER.debug(Messages.TRY_GET_TEACHER_BY_ID, id);
        Validation.validateId(id);
        Teacher teacher = teacherDAO.getTeacherByIdWithCourses(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_TEACHER_BY_ID);
            return new ServiceException(Messages.ERROR_GET_TEACHER_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_TEACHER_BY_ID, id, teacher);
        return teacher;
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        LOGGER.debug(Messages.TRY_CREATE_TEACHER);
        Validation.validateTeacher(teacher);
        Teacher createdTeacher = teacherDAO.createTeacher(teacher).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_CREATE_TEACHER);
            throw new ServiceException(Messages.ERROR_CREATE_TEACHER);
        });
        LOGGER.debug(Messages.OK_CREATE_TEACHER, createdTeacher);
        return createdTeacher;
    }

    @Override
    public Teacher deleteTeacherById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_TEACHER_BY_ID, id);
        Validation.validateId(id);
        Teacher deletedTeacher = teacherDAO.deleteTeacherById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_DELETE_TEACHER_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_TEACHER_BY_ID);
        });
        LOGGER.debug(Messages.OK_DELETE_TEACHER_BY_ID, id, deletedTeacher);
        return deletedTeacher;
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        LOGGER.debug(Messages.TRY_UPDATE_TEACHER, teacher);
        Validation.validateTeacher(teacher);
        Teacher updatedTeacher = teacherDAO.updateTeacher(teacher).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_UPDATE_TEACHER);
            throw new ServiceException(Messages.ERROR_UPDATE_TEACHER);
        });
        LOGGER.debug(Messages.OK_UPDATE_TEACHER, updatedTeacher);
        return updatedTeacher;
    }

}
