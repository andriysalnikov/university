package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
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
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;
import ua.com.foxminded.andriysalnikov.university.extractors.TeacherResultSetExtractor;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringJdbcConfig.class)
@WebAppConfiguration
class TeacherServiceImplIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherResultSetExtractor teacherResultSetExtractor;

    @BeforeAll
    void createDataBaseForTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_DATABASE_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_TEACHERS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_COURSES_FOR_TESTS);
    }

    @BeforeEach
    void createEnvironmentForTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TEACHERS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_COURSES);
    }

    @Test
    void getAllTeachers_shouldReturnListOfAllTeachers() throws SQLException {
        List<Teacher> expectedTeachers = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_TEACHERS, new BeanPropertyRowMapper<>(Teacher.class));
        List<Teacher> returnedTeachers = teacherService.getAllTeachers();
        assertEquals(expectedTeachers, returnedTeachers);
    }

    @Test
    void getTeacherById_shouldReturnTeacher_whenArgumentIsIntegerId() {
        Integer id = 2;
        Teacher expectedTeacher = jdbcTemplate.query(TestDBConstants.SQL_GET_TEACHER_BY_ID,
                teacherResultSetExtractor, id);
        Teacher returnedTeacher = teacherService.getTeacherById(id);
        assertEquals(expectedTeacher, returnedTeacher);
        assertThrows(ServiceException.class, () -> teacherService.getTeacherById(1000));
    }

    @Test
    void createTeacher_shouldCreateTeacher_whenInputIsTeacher() throws SQLException {
        Teacher teacher = new Teacher("First Name", "Last Name");
        teacherService.createTeacher(teacher);
        Teacher createdTeacher = jdbcTemplate.query(TestDBConstants.SQL_GET_TEACHER_BY_FULL_NAME,
                teacherResultSetExtractor, teacher.getFirstName(), teacher.getLastName());
        assertEquals(teacher, createdTeacher);
    }

    @Test
    void updateTeacher_shouldUpdateTeacher_whenInputIsTeacher() throws SQLException {
        Integer id = 2;
        Teacher teacher = jdbcTemplate.query(TestDBConstants.SQL_GET_TEACHER_BY_ID,
                teacherResultSetExtractor, id);
        teacher.setFirstName("New First Name");
        teacher.setLastName("New Last Name");
        teacherService.updateTeacher(teacher);
        Teacher updatedTeacher = jdbcTemplate.query(TestDBConstants.SQL_GET_TEACHER_BY_ID,
                teacherResultSetExtractor, id);
        assertEquals(teacher, updatedTeacher);
    }

    @Test
    void deleteTeacher_shouldDeleteTeacher_whenInputIsIntegerId() throws SQLException {
        Integer id = 2;
        Teacher teacher = jdbcTemplate.query(TestDBConstants.SQL_GET_TEACHER_BY_ID,
                teacherResultSetExtractor, id);
        teacherService.deleteTeacherById(id);
        List<Teacher> teachers = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_TEACHERS, new BeanPropertyRowMapper<>(Teacher.class));
        assertFalse(teachers.contains(teacher));
    }

    @Test
    void getTeacherByIdWithCourses_shouldReturnTeacherWithCourses_whenArgumentIsIntegerId() {
        Integer id = 2;
        Teacher expectedTeacher = jdbcTemplate.query(TestDBConstants.SQL_GET_TEACHER_BY_ID,
                teacherResultSetExtractor, id);
        List<Course> expectedCourses = jdbcTemplate.query(
                TestDBConstants.SQL_GET_TEACHER_COURSES_BY_TEACHER_ID,
                    new BeanPropertyRowMapper<>(Course.class), id);
        Teacher returnedTeacher = teacherService.getTeacherByIdWithCourses(id);
        assertEquals(expectedTeacher, returnedTeacher);
        assertEquals(expectedCourses, returnedTeacher.getCourses());
    }

    @AfterEach
    void cleanEnvironmentAfterTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_TEACHERS);
    }

    @AfterAll
    void dropDataBaseAfterTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_DROP_DATABASE_AFTER_TESTS);
    }

}