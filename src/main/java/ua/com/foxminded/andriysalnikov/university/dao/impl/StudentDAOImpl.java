package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.mappers.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.extractors.StudentResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;
    private final StudentResultSetExtractor studentResultSetExtractor;

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate, CourseMapper courseMapper,
                          StudentResultSetExtractor studentResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseMapper = courseMapper;
        this.studentResultSetExtractor = studentResultSetExtractor;
    }

    @Override
    public List<Student> getAllStudents() {
        return jdbcTemplate.query(
                DBConstants.SQL_GET_ALL_STUDENTS, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public List<Student> getAllStudentsWithoutFaculty() {
        return jdbcTemplate.query(
                DBConstants.SQL_GET_ALL_STUDENTS_WITHOUT_FACULTY,
                new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public Optional<Student> getStudentById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_GET_STUDENT_BY_ID,
                studentResultSetExtractor, id));
    }

    @Override
    public Optional<Student> createStudent(Student student) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_CREATE_STUDENT,
                studentResultSetExtractor, student.getFirstName(), student.getLastName()));
    }

    @Override
    public Optional<Student> deleteStudentById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_DELETE_STUDENT_BY_ID,
                studentResultSetExtractor, id));
    }

    @Override
    public Optional<Student> updateStudent(Student student) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_UPDATE_STUDENT,
                studentResultSetExtractor, student.getFirstName(), student.getLastName(), student.getId()));
    }

    @Override
    public List<Course> getStudentCoursesByStudentId(Integer id) {
        return jdbcTemplate.query(
                DBConstants.SQL_GET_STUDENT_COURSES_BY_STUDENT_ID, courseMapper, id);
    }

    @Override
    public Optional<Student> setFacultyToStudent(Integer facultyId, Integer studentId) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_SET_FACULTY_TO_STUDENT,
                studentResultSetExtractor, facultyId, studentId));
    }

    @Override
    public Optional<Student> removeFacultyFromStudent(Integer studentId) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_REMOVE_FACULTY_FROM_STUDENT,
                studentResultSetExtractor, studentId));
    }

}
