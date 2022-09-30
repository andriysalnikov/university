package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherDTOWithCourses;
import ua.com.foxminded.andriysalnikov.university.mapper.TeacherMapper;
import ua.com.foxminded.andriysalnikov.university.repository.TeacherRepository;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;

    }

    @Override
    public List<Teacher> getAllTeachers() {
        LOGGER.debug(Messages.TRY_GET_ALL_TEACHERS);
        List<Teacher> teachers = teacherRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        LOGGER.debug(Messages.OK_GET_ALL_TEACHERS, teachers);
        return teachers;
    }

    @Override
    public List<TeacherDTO> getAllTeacherDTOs() {
        return getAllTeachers()
                .stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_TEACHER_BY_ID, id);
        Teacher teacher = teacherRepository.getTeacherById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_TEACHER_BY_ID);
            return new ServiceException(Messages.ERROR_GET_TEACHER_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_TEACHER_BY_ID, id, teacher);
        return teacher;
    }

    @Override
    public TeacherDTO getTeacherDTOById(Integer id) {
        return teacherMapper.toDTO(getTeacherById(id));
    }

    @Override
    public Teacher getTeacherByIdWithCourses(Integer id) {
        LOGGER.debug(Messages.TRY_GET_TEACHER_BY_ID, id);
        Teacher teacher = teacherRepository.getTeacherByIdWithCourses(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_TEACHER_BY_ID);
            return new ServiceException(Messages.ERROR_GET_TEACHER_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_TEACHER_BY_ID, id, teacher);
        return teacher;
    }

    @Override
    public TeacherDTOWithCourses getTeacherDTOByIdWithCourses(Integer id) {
        return teacherMapper.toDTOWithCourses(getTeacherByIdWithCourses(id));
    }

    @Modifying
    @Transactional
    @Override
    public Teacher createTeacher(Teacher teacher) {
        LOGGER.debug(Messages.TRY_CREATE_TEACHER);
        Teacher createdTeacher;
        try {
            createdTeacher = teacherRepository.save(teacher);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_CREATE_TEACHER_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_CREATE_TEACHER);
        }
        LOGGER.debug(Messages.OK_CREATE_TEACHER, createdTeacher);
        return createdTeacher;
    }

    @Modifying
    @Transactional
    @Override
    public TeacherDTO createTeacherDTO(TeacherCreateDTO teacherCreateDTO) {
        return teacherMapper.toDTO(createTeacher(teacherMapper.fromDTO(teacherCreateDTO)));
    }

    @Modifying
    @Transactional
    @Override
    public void deleteTeacherById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_TEACHER_BY_ID, id);
        if (teacherRepository.deleteTeacherById(id) == 0) {
            LOGGER.error(Messages.ERROR_DELETE_TEACHER_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_TEACHER_BY_ID);
        }
        LOGGER.debug(Messages.OK_DELETE_TEACHER_BY_ID, id);
    }

    @Modifying
    @Transactional
    @Override
    public Teacher updateTeacher(Teacher teacher) {
        LOGGER.debug(Messages.TRY_UPDATE_TEACHER, teacher);
        Teacher updatedTeacher;
        try {
            updatedTeacher = teacherRepository.save(teacher);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_UPDATE_TEACHER_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_UPDATE_TEACHER, exception);
        }
        LOGGER.debug(Messages.OK_UPDATE_TEACHER, updatedTeacher);
        return updatedTeacher;
    }

    @Modifying
    @Transactional
    @Override
    public TeacherDTO updateTeacherDTO(Integer id, TeacherCreateDTO teacherCreateDTO) {
        Teacher teacherToUpdate = teacherMapper.fromDTO(teacherCreateDTO);
        teacherToUpdate.setId(id);
        return teacherMapper.toDTO(updateTeacher(teacherToUpdate));
    }

}
