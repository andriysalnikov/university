package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public List<Student> getAllStudents() {
        LOGGER.debug(Messages.TRY_GET_ALL_STUDENTS);
        List<Student> students = studentDAO.getAllStudents();
        LOGGER.debug(Messages.OK_GET_ALL_STUDENTS, students);
        return students;
    }

    @Override
    public List<Student> getAllStudentsWithoutFaculty() {
        LOGGER.debug(Messages.TRY_GET_ALL_STUDENTS_WITHOUT_FACULTY);
        List<Student> students = studentDAO.getAllStudentsWithoutFaculty();
        LOGGER.debug(Messages.OK_GET_ALL_STUDENTS_WITHOUT_FACULTY, students);
        return students;
    }

    @Override
    public Student getStudentById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_STUDENT_BY_ID, id);
        Validation.validateId(id);
        Student student = studentDAO.getStudentById(id).orElseThrow(() -> {
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
        Student student = studentDAO.getStudentByIdWithCourses(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_STUDENT_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_STUDENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_STUDENT_BY_ID, id, student);
        return student;
    }

    @Override
    public Student createStudent(Student student) {
        LOGGER.debug(Messages.TRY_CREATE_STUDENT);
        Validation.validateStudent(student);
        Student createdStudent = studentDAO.createStudent(student).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_CREATE_STUDENT);
            throw new ServiceException(Messages.ERROR_CREATE_STUDENT);
        });
        LOGGER.debug(Messages.OK_CREATE_STUDENT, createdStudent);
        return createdStudent;
    }

    @Override
    public Student deleteStudentById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_STUDENT_BY_ID, id);
        Validation.validateId(id);
        Student deletedStudent = studentDAO.deleteStudentById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_DELETE_STUDENT_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_STUDENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_DELETE_STUDENT_BY_ID, id, deletedStudent);
        return deletedStudent;
    }

    @Override
    public Student updateStudent(Student student) {
        LOGGER.debug(Messages.TRY_UPDATE_STUDENT, student);
        Validation.validateStudent(student);
        Student updatedStudent = studentDAO.updateStudent(student).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_UPDATE_STUDENT);
            throw new ServiceException(Messages.ERROR_UPDATE_STUDENT);
        });
        LOGGER.debug(Messages.OK_UPDATE_STUDENT, updatedStudent);
        return updatedStudent;
    }

}
