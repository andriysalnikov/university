package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.*;
import ua.com.foxminded.andriysalnikov.university.mapper.StudentMapper;
import ua.com.foxminded.andriysalnikov.university.repository.StudentRepository;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> getAllStudents() {
        LOGGER.debug(Messages.TRY_GET_ALL_STUDENTS);
        List<Student> students = studentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        LOGGER.debug(Messages.OK_GET_ALL_STUDENTS, students);
        return students;
    }

    @Override
    public List<StudentDTO> getAllStudentDTOs() {
        return getAllStudents()
                .stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> getAllStudentsWithoutFaculty() {
        LOGGER.debug(Messages.TRY_GET_ALL_STUDENTS_WITHOUT_FACULTY);
        List<Student> students = studentRepository.findStudentsByFacultyIsNullOrderByIdAsc();
        LOGGER.debug(Messages.OK_GET_ALL_STUDENTS_WITHOUT_FACULTY, students);
        return students;
    }

    @Override
    public List<StudentDTO> getAllStudentDTOsWithoutFaculty() {
        return getAllStudentsWithoutFaculty()
                .stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Student getStudentById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_STUDENT_BY_ID, id);
        Student student = studentRepository.getStudentById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_STUDENT_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_STUDENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_STUDENT_BY_ID, id, student);
        return student;
    }

    @Override
    public StudentDTO getStudentDTOById(Integer id) {
        return studentMapper.toDTO(getStudentById(id));
    }

    @Override
    public Student getStudentByIdWithCourses(Integer id) {
        LOGGER.debug(Messages.TRY_GET_STUDENT_BY_ID, id);
        Student student = studentRepository.getStudentByIdWithCourses(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_STUDENT_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_STUDENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_STUDENT_BY_ID, id, student);
        return student;
    }

    @Override
    public StudentDTOWithCourses getStudentDTOByIdWithCourses(Integer id) {
        return studentMapper.toDTOWithCourses(getStudentByIdWithCourses(id));
    }

    @Modifying
    @Transactional
    @Override
    public Student createStudent(Student student) {
        LOGGER.debug(Messages.TRY_CREATE_STUDENT);
        Student createdStudent;
        try {
            createdStudent = studentRepository.save(student);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_CREATE_STUDENT_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_CREATE_STUDENT, exception);
        }
        LOGGER.debug(Messages.OK_CREATE_STUDENT, createdStudent);
        return createdStudent;
    }

    @Modifying
    @Transactional
    @Override
    public StudentDTO createStudentDTO(StudentCreateDTO studentCreateDTO) {
        return studentMapper.toDTO(createStudent(studentMapper.fromDTO(studentCreateDTO)));
    }

    @Modifying
    @Transactional
    @Override
    public void deleteStudentById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_STUDENT_BY_ID, id);
        if(studentRepository.deleteStudentById(id) == 0) {
            LOGGER.error(Messages.ERROR_DELETE_STUDENT_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_STUDENT_BY_ID);
        }
        LOGGER.debug(Messages.OK_DELETE_STUDENT_BY_ID, id);
    }

    @Modifying
    @Transactional
    @Override
    public Student updateStudent(Student student) {
        LOGGER.debug(Messages.TRY_UPDATE_STUDENT, student);
        Student updatedStudent;
        try {
            updatedStudent = studentRepository.save(student);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_UPDATE_STUDENT_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_UPDATE_STUDENT, exception);
        }
        LOGGER.debug(Messages.OK_UPDATE_STUDENT, updatedStudent);
        return updatedStudent;
    }

    @Modifying
    @Transactional
    @Override
    public StudentDTO updateStudentDTO(Integer id, StudentCreateDTO studentCreateDTO) {
        Student studentToUpdate = studentMapper.fromDTO(studentCreateDTO);
        studentToUpdate.setId(id);
        return studentMapper.toDTO(updateStudent(studentToUpdate));
    }

}