package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.*;
import ua.com.foxminded.andriysalnikov.university.mapper.FacultyMapper;
import ua.com.foxminded.andriysalnikov.university.repository.FacultyRepository;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.service.FacultyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FacultyServiceImpl implements FacultyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository, FacultyMapper facultyMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
    }

    @Override
    public List<Faculty> getAllFaculties() {
        LOGGER.debug(Messages.TRY_GET_ALL_FACULTIES);
        List<Faculty> faculties = facultyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        LOGGER.debug(Messages.OK_GET_ALL_FACULTIES, faculties);
        return faculties;
    }

    @Override
    public List<FacultyDTO> getAllFacultyDTOs() {
        return getAllFaculties()
                .stream()
                .map(facultyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Faculty getFacultyById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty = facultyRepository.getFacultyById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_FACULTY_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @Override
    public FacultyDTO getFacultyDTOById(Integer id) {
        return facultyMapper.toDTO(getFacultyById(id));
    }

    @Override
    public Faculty getFacultyByIdWithClassRooms(Integer id) {
        LOGGER.debug(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty = facultyRepository.getFacultyByIdWithClassRooms(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_FACULTY_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @Override
    public FacultyDTOWithClassRooms getFacultyDTOByIdWithClassRooms(Integer id) {
        return facultyMapper.toDTOWithClassRooms(getFacultyByIdWithClassRooms(id));
    }

    @Override
    public Faculty getFacultyByIdWithCourses(Integer id) {
        LOGGER.debug(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty = facultyRepository.getFacultyByIdWithCourses(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_FACULTY_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @Override
    public FacultyDTOWithCourses getFacultyDTOByIdWithCourses(Integer id) {
        return facultyMapper.toDTOWithCourses(getFacultyByIdWithCourses(id));
    }

    @Override
    public Faculty getFacultyByIdWithStudents(Integer id) {
        LOGGER.debug(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty = facultyRepository.getFacultyByIdWithStudents(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_FACULTY_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @Override
    public FacultyDTOWithStudents getFacultyDTOByIdWithStudents(Integer id) {
        return facultyMapper.toDTOWithStudents(getFacultyByIdWithStudents(id));
    }

    @Modifying
    @Transactional
    @Override
    public Faculty createFaculty(Faculty faculty) {
        LOGGER.debug(Messages.TRY_CREATE_FACULTY);
        Faculty createdFaculty;
        try {
            createdFaculty = facultyRepository.save(faculty);
        } catch (RuntimeException exception){
            LOGGER.error(Messages.ERROR_CREATE_FACULTY_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_CREATE_FACULTY, exception);
        }
        LOGGER.debug(Messages.OK_CREATE_FACULTY, createdFaculty);
        return createdFaculty;
    }

    @Modifying
    @Transactional
    @Override
    public FacultyDTO createFacultyDTO(FacultyCreateDTO facultyCreateDTO) {
        return facultyMapper.toDTO(createFaculty(facultyMapper.fromDTO(facultyCreateDTO)));
    }

    @Modifying
    @Transactional
    @Override
    public void deleteFacultyById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_FACULTY_BY_ID, id);
        if (facultyRepository.deleteFacultyById(id) == 0) {
            LOGGER.error(Messages.ERROR_DELETE_FACULTY_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_FACULTY_BY_ID);
        }
        LOGGER.debug(Messages.OK_DELETE_FACULTY_BY_ID, id);
    }

    @Modifying
    @Transactional
    @Override
    public Faculty updateFaculty(Faculty faculty) {
        LOGGER.debug(Messages.TRY_UPDATE_FACULTY, faculty);
        Faculty updatedFaculty;
        try {
            updatedFaculty = facultyRepository.save(faculty);
        } catch (RuntimeException exception){
            LOGGER.error(Messages.ERROR_UPDATE_FACULTY_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_UPDATE_FACULTY, exception);
        }
        LOGGER.debug(Messages.OK_UPDATE_FACULTY, updatedFaculty);
        return updatedFaculty;
    }

    @Modifying
    @Transactional
    @Override
    public FacultyDTO updateFacultyDTO(Integer id, FacultyCreateDTO facultyCreateDTO) {
        Faculty facultyToUpdate = facultyMapper.fromDTO(facultyCreateDTO);
        facultyToUpdate.setId(id);
        return facultyMapper.toDTO(updateFaculty(facultyToUpdate));
    }

}
