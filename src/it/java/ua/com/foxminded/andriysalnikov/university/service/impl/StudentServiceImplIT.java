package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ua.com.foxminded.andriysalnikov.university.assembler.Assembler;
import ua.com.foxminded.andriysalnikov.university.config.TestSpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.consnants.TestDBConstants;
import ua.com.foxminded.andriysalnikov.university.mapper.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = TestSpringJdbcConfig.class)
class StudentServiceImplIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseMapper courseMapper;

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
    void getStudentById_shouldReturnStudent_whenArgumentIsIntegerId() {
        Student student
                = Assembler.assembleStudent(
                        jdbcTemplate.queryForRowSet(TestDBConstants.SQL_GET_STUDENT_BY_ID, 4));
        assertEquals(student, studentService.getStudentById(4));
        assertNull(studentService.getStudentById(30));
    }

    @Test
    void getStudentCoursesByStudentId_shouldReturnListOfStudentCourses_whenArgumentIsStudentId() {
        List<Course> expectedCourses
                = jdbcTemplate.query(TestDBConstants.SQL_GET_STUDENT_COURSES_BY_STUDENT_ID,
                courseMapper, 5);
        List<Course> returnedCourses = studentService.getStudentCoursesByStudentId(5);
        assertEquals(expectedCourses, returnedCourses);
    }

    @AfterEach
    void cleanEnvironmentAfterTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_STUDENTS_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_STUDENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_COURSES);
    }

    @AfterAll
    void dropDataBaseAfterTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_DROP_DATABASE_AFTER_TESTS);
    }

}
