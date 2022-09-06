package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.repository.StudentRepository;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        LOGGER.debug(Messages.TRY_GET_ALL_STUDENTS);
        List<Student> students = studentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        LOGGER.debug(Messages.OK_GET_ALL_STUDENTS, students);
        return students;
    }

    @Override
    public List<Student> getAllStudentsWithoutFaculty() {
        LOGGER.debug(Messages.TRY_GET_ALL_STUDENTS_WITHOUT_FACULTY);
        List<Student> students = studentRepository.findStudentsByFacultyIsNullOrderByIdAsc();
        LOGGER.debug(Messages.OK_GET_ALL_STUDENTS_WITHOUT_FACULTY, students);
        return students;
    }

    @Override
    public Student getStudentById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_STUDENT_BY_ID, id);
        Validation.validateId(id);
        Student student = studentRepository.getStudentById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_STUDENT_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_STUDENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_STUDENT_BY_ID, id, student);
        return student;
    }

    @Override
    public Student getStudentByIdWithCourses(Integer id) {
        LOGGER.debug(Messages.TRY_GET_STUDENT_BY_ID, id);
        Validation.validateId(id);
        Student student = studentRepository.getStudentByIdWithCourses(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_STUDENT_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_STUDENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_STUDENT_BY_ID, id, student);
        return student;
    }

    @Modifying
    @Transactional
    @Override
    public Student createStudent(Student student) {
        LOGGER.debug(Messages.TRY_CREATE_STUDENT);
        Validation.validateStudent(student);
        Student createdStudent;
        try {
            createdStudent = studentRepository.save(student);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_CREATE_STUDENT);
            throw new ServiceException(Messages.ERROR_CREATE_STUDENT);
        }
        LOGGER.debug(Messages.OK_CREATE_STUDENT, createdStudent);
        return createdStudent;
    }

    @Modifying
    @Transactional
    @Override
    public void deleteStudentById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_STUDENT_BY_ID, id);
        Validation.validateId(id);
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
        Validation.validateStudent(student);
        Student updatedStudent;
        try {
            updatedStudent = studentRepository.save(student);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_UPDATE_STUDENT);
            throw new ServiceException(Messages.ERROR_UPDATE_STUDENT);
        }
        LOGGER.debug(Messages.OK_UPDATE_STUDENT, updatedStudent);
        return updatedStudent;
    }

}
