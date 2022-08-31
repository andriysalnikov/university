package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.foxminded.andriysalnikov.university.config.TestSpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.consnants.TestDBConstants;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.extractors.StudentResultSetExtractor;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringJdbcConfig.class)
@WebAppConfiguration
class StudentServiceImplIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentResultSetExtractor studentResultSetExtractor;

    @BeforeAll
    void createDataBaseForTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_DATABASE_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_STUDENTS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_COURSES_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_STUDENTS_COURSES_FOR_TESTS);
    }

    @BeforeEach
    void createEnvironmentForTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_STUDENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_STUDENTS_COURSES);
    }

    @Test
    void getAllStudents_shouldReturnListOfAllStudents() throws SQLException {
        List<Student> expectedStudents = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_STUDENTS, new BeanPropertyRowMapper<>(Student.class));
        List<Student> returnedStudents = studentService.getAllStudents();
        assertEquals(expectedStudents, returnedStudents);
    }

    @Test
    void getAllStudentsWithoutFaculty_shouldReturnListOfAllStudentsWithoutFaculty() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_STUDENTS_WITHOUT_FACULTY);
        List<Student> expectedStudents = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_STUDENTS_WITHOUT_FACULTY,
                    new BeanPropertyRowMapper<>(Student.class));
        List<Student> returnedStudents = studentService.getAllStudentsWithoutFaculty();
        assertEquals(expectedStudents, returnedStudents);
    }

    @Test
    void getStudentById_shouldReturnStudent_whenArgumentIsIntegerId() {
        Integer id = 5;
        Student expectedStudent = jdbcTemplate.query(TestDBConstants.SQL_GET_STUDENT_BY_ID,
                studentResultSetExtractor, id);
        Student returnedStudent = studentService.getStudentById(id);
        assertEquals(expectedStudent, returnedStudent);
        assertThrows(ServiceException.class, () -> studentService.getStudentById(1000));
    }

    @Test
    void createStudent_shouldCreateStudent_whenInputIsStudent() throws SQLException {
        Student student = new Student("First Name", "Last Name");
        studentService.createStudent(student);
        Student createdStudent = jdbcTemplate.query(TestDBConstants.SQL_GET_STUDENT_BY_FULL_NAME,
                studentResultSetExtractor, student.getFirstName(), student.getLastName());
        assertEquals(student, createdStudent);
    }

    @Test
    void updateStudent_shouldUpdateStudent_whenInputIsStudent() throws SQLException {
        Integer id = 5;
        Student student = jdbcTemplate.query(TestDBConstants.SQL_GET_STUDENT_BY_ID,
                studentResultSetExtractor, id);
        student.setFirstName("New First Name");
        student.setLastName("New Last Name");
        studentService.updateStudent(student);
        Student updatedStudent = jdbcTemplate.query(TestDBConstants.SQL_GET_STUDENT_BY_ID,
                studentResultSetExtractor, id);
        assertEquals(student, updatedStudent);
    }

    @Test
    void deleteStudent_shouldDeleteStudent_whenInputIsIntegerId() throws SQLException {
        Integer id = 5;
        Student student = jdbcTemplate.query(TestDBConstants.SQL_GET_STUDENT_BY_ID,
                studentResultSetExtractor, id);
        studentService.deleteStudentById(id);
        List<Student> students = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_STUDENTS, new BeanPropertyRowMapper<>(Student.class));
        assertFalse(students.contains(student));
    }

    @Test
    void getStudentByIdWithCourses_shouldReturnStudentWithCourses_whenArgumentIsIntegerId() {
        Integer id = 5;
        Student expectedStudent = jdbcTemplate.query(TestDBConstants.SQL_GET_STUDENT_BY_ID,
                studentResultSetExtractor, id);
        List<Course> expectedCourses = jdbcTemplate.query(
                TestDBConstants.SQL_GET_STUDENT_COURSES_BY_STUDENT_ID,
                    new BeanPropertyRowMapper<>(Course.class), id);
        Student returnedStudent = studentService.getStudentByIdWithCourses(id);
        assertEquals(expectedStudent, returnedStudent);
        assertEquals(expectedCourses, returnedStudent.getCourses());
    }

    @AfterEach
    void cleanEnvironmentAfterTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_STUDENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_STUDENTS_COURSES);
    }

    @AfterAll
    void dropDataBaseAfterTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_DROP_DATABASE_AFTER_TESTS);
    }

}