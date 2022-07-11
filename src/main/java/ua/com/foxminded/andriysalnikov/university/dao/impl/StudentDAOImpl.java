package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.assembler.Assembler;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.DBException;
import ua.com.foxminded.andriysalnikov.university.mapper.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;

    @Autowired
    public StudentDAOImpl(DataSource dataSource, CourseMapper courseMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Student> getAllStudents() {
        LOGGER.debug(Messages.TRY_GET_ALL_ENTITIES, Student.class.getSimpleName());
        List<Student> students;
        try {
            students = jdbcTemplate.query(
                    DBConstants.SQL_GET_ALL_STUDENTS, new BeanPropertyRowMapper<>(Student.class));
        } catch (DataAccessException dataAccessException) {
            LOGGER.error(Messages.ERROR_GET_ALL_STUDENTS);
            throw new DBException(Messages.ERROR_GET_ALL_STUDENTS, dataAccessException);
        }
        LOGGER.debug(Messages.ALL_ENTITY_GOTTEN, Student.class.getSimpleName(), students);
        return students;
    }

    @Override
    public Student getStudentById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_ENTITY_BY_ID, Student.class.getSimpleName(), id);
        Student student;
        try {
            student = Assembler.assembleStudent(
                    jdbcTemplate.queryForRowSet(DBConstants.SQL_GET_STUDENT_BY_ID, id));
        } catch (DataAccessException dataAccessException) {
            LOGGER.error(Messages.ERROR_GET_STUDENT_BY_ID);
            throw new DBException(Messages.ERROR_GET_STUDENT_BY_ID, dataAccessException);
        }
        LOGGER.debug(Messages.ENTITY_GOTTEN_BY_ID, Student.class.getSimpleName(), student);
        return student;
    }

    @Override
    public Student createStudent(Student student) {
        LOGGER.debug(Messages.TRY_CREATE_ENTITY, Student.class.getSimpleName(), student);
        Student createdStudent;
        try {
            createdStudent = Assembler.assembleStudent(
                    jdbcTemplate.queryForRowSet(DBConstants.SQL_CREATE_STUDENT,
                        0, student.getFirstName(), student.getLastName()));
        } catch (DataAccessException dataAccessException) {
            LOGGER.error(Messages.ERROR_CREATE_STUDENT);
            throw new DBException(Messages.ERROR_CREATE_STUDENT, dataAccessException);
        }
        LOGGER.debug(Messages.ENTITY_CREATED, Student.class.getSimpleName(), createdStudent);
        return createdStudent;
    }

    @Override
    public Student deleteStudentById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_ENTITY_BY_ID, Student.class.getSimpleName(), id);
        Student deletedStudent;
        try {
            deletedStudent = Assembler.assembleStudent(
                    jdbcTemplate.queryForRowSet(DBConstants.SQL_DELETE_STUDENT_BY_ID, id));
        } catch (DataAccessException dataAccessException) {
            LOGGER.error(Messages.ERROR_DELETE_STUDENT);
            throw new DBException(Messages.ERROR_DELETE_STUDENT, dataAccessException);
        }
        LOGGER.debug(Messages.ENTITY_DELETED, Student.class.getSimpleName(), deletedStudent);
        return deletedStudent;
    }

    @Override
    public Student updateStudent(Student student) {
        LOGGER.debug(Messages.TRY_UPDATE_ENTITY, Student.class.getSimpleName(), student);
        Student updatedStudent;
        try {
            updatedStudent = Assembler.assembleStudent(
                    jdbcTemplate.queryForRowSet(DBConstants.SQL_UPDATE_STUDENT,
                        student.getFirstName(), student.getLastName(), student.getId()));
        } catch (DataAccessException dataAccessException) {
            LOGGER.error(Messages.ERROR_UPDATE_STUDENT);
            throw new DBException(Messages.ERROR_UPDATE_STUDENT, dataAccessException);
        }
        LOGGER.debug(Messages.ENTITY_UPDATED, Student.class.getSimpleName(), updatedStudent);
        return updatedStudent;
    }

    @Override
    public List<Course> getStudentCoursesByStudentId(Integer id) {
        LOGGER.debug(Messages.TRY_GET_ENTITY_COURSES_BY_ENTITY_ID,
                Student.class.getSimpleName(), Student.class.getSimpleName(), id);
        List<Course> courses;
        try {
            courses = jdbcTemplate.query(
                    DBConstants.SQL_GET_STUDENT_COURSES_BY_STUDENT_ID, courseMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.error(Messages.ERROR_GET_STUDENT_COURSES_BY_STUDENT_ID);
            throw new DBException(Messages.ERROR_GET_STUDENT_COURSES_BY_STUDENT_ID,
                    dataAccessException);
        }
        LOGGER.debug(Messages.ENTITY_COURSES_GOTTEN, Student.class.getSimpleName(), id, courses);
        return courses;
    }

}
