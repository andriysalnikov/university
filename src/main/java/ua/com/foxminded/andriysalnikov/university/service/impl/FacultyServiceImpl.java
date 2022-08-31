package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.FacultyDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.service.FacultyService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.util.List;

@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyDAO facultyDAO;

    @Autowired
    public FacultyServiceImpl(FacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
    }

    @Override
    public List<Faculty> getAllFaculties() {
        LOGGER.debug(Messages.TRY_GET_ALL_FACULTIES);
        List<Faculty> faculties = facultyDAO.getAllFaculties();
        LOGGER.debug(Messages.OK_GET_ALL_FACULTIES, faculties);
        return faculties;
    }

    @Override
    public Faculty getFacultyById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_FACULTY_BY_ID, id);
        Validation.validateId(id);
        Faculty faculty = facultyDAO.getFacultyById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_FACULTY_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyByIdWithClassRooms(Integer id) {
        LOGGER.debug(Messages.TRY_GET_FACULTY_BY_ID, id);
        Validation.validateId(id);
        Faculty faculty = facultyDAO.getFacultyByIdWithClassRooms(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_FACULTY_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyByIdWithCourses(Integer id) {
        LOGGER.debug(Messages.TRY_GET_FACULTY_BY_ID, id);
        Validation.validateId(id);
        Faculty faculty = facultyDAO.getFacultyByIdWithCourses(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_FACULTY_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyByIdWithStudents(Integer id) {
        LOGGER.debug(Messages.TRY_GET_FACULTY_BY_ID, id);
        Validation.validateId(id);
        Faculty faculty = facultyDAO.getFacultyByIdWithStudents(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_FACULTY_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        LOGGER.debug(Messages.TRY_CREATE_FACULTY);
        Validation.validateFaculty(faculty);
        Faculty createdFaculty = facultyDAO.createFaculty(faculty).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_CREATE_FACULTY);
            throw new ServiceException(Messages.ERROR_CREATE_FACULTY);
        });
        LOGGER.debug(Messages.OK_CREATE_FACULTY, createdFaculty);
        return createdFaculty;
    }

    @Override
    public Faculty deleteFacultyById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_FACULTY_BY_ID, id);
        Validation.validateId(id);
        Faculty deletedFaculty = facultyDAO.deleteFacultyById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_DELETE_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_FACULTY_BY_ID);
        });
        LOGGER.debug(Messages.OK_DELETE_FACULTY_BY_ID, id, deletedFaculty);
        return deletedFaculty;
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        LOGGER.debug(Messages.TRY_UPDATE_FACULTY, faculty);
        Validation.validateFaculty(faculty);
        Faculty updatedFaculty = facultyDAO.updateFaculty(faculty).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_UPDATE_FACULTY);
            throw new ServiceException(Messages.ERROR_UPDATE_FACULTY);
        });
        LOGGER.debug(Messages.OK_UPDATE_FACULTY, updatedFaculty);
        return updatedFaculty;
    }

}
