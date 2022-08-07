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
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.foxminded.andriysalnikov.university.config.TestSpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.consnants.TestDBConstants;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.extractors.TeacherResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.mappers.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    private CourseMapper CourseMapper;
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
    void getTeacherById_shouldReturnTeacher_whenArgumentIsIntegerId() {
        Teacher teacher = jdbcTemplate.query(TestDBConstants.SQL_GET_TEACHER_BY_ID,
                teacherResultSetExtractor, 2);
        assertEquals(teacher, teacherService.getTeacherById(2));
        assertThrows(ServiceException.class, () -> teacherService.getTeacherById(10));
    }

    @Test
    void getTeacherCoursesByTeacherId_shouldReturnListOfTeacherCourses_whenArgumentIsTeacherId() {
        List<Course> expectedCourses = jdbcTemplate.query(TestDBConstants.SQL_GET_TEACHER_COURSES_BY_TEACHER_ID,
                CourseMapper, 3);
        List<Course> returnedCourses = teacherService.getTeacherCoursesByTeacherId(3);
        assertEquals(expectedCourses, returnedCourses);
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


