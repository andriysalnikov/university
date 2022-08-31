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
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.extractors.CourseResultSetExtractor;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringJdbcConfig.class)
@WebAppConfiguration
class CourseServiceImplIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseResultSetExtractor courseResultSetExtractor;

    @BeforeAll
    void createDataBaseForTests() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_DATABASE_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_COURSES_FOR_TESTS);
    }

    @BeforeEach
    void createEnvironmentForTest() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_COURSES);
    }

    @Test
    void getAllCourses_shouldReturnListOfAllCourses() throws SQLException {
        List<Course> expectedCourses = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_COURSES, new BeanPropertyRowMapper<>(Course.class));
        List<Course> returnedCourses = courseService.getAllCourses();
        assertEquals(expectedCourses, returnedCourses);
    }

    @Test
    void getAllCoursesWithoutFaculty_shouldReturnListOfAllCoursesWithoutFaculty() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_COURSES_WITHOUT_FACULTY_OR_TEACHER);
        List<Course> expectedCourses = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_COURSES_WITHOUT_FACULTY,
                    new BeanPropertyRowMapper<>(Course.class));
        List<Course> returnedCourses = courseService.getAllCoursesWithoutFaculty();
        assertEquals(expectedCourses, returnedCourses);
    }

    @Test
    void getAllCoursesWithoutTeacher_shouldReturnListOfAllCoursesWithoutFaculty() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_COURSES_WITHOUT_FACULTY_OR_TEACHER);
        List<Course> expectedCourses = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_COURSES_WITHOUT_TEACHER,
                    new BeanPropertyRowMapper<>(Course.class));
        List<Course> returnedCourses = courseService.getAllCoursesWithoutTeacher();
        assertEquals(expectedCourses, returnedCourses);
    }

    @Test
    void getCourseById_shouldReturnCourse_whenInputIsIntegerId() throws SQLException {
        Integer id = 3;
        Course expectedCourse = jdbcTemplate.query(TestDBConstants.SQL_GET_COURSE_BY_ID,
                courseResultSetExtractor, id);
        Course returnedCourse = courseService.getCourseById(id);
        assertEquals(expectedCourse, returnedCourse);
        assertThrows(ServiceException.class, () -> courseService.getCourseById(1000));
    }

    @Test
    void createCourse_shouldCreateCourse_whenInputIsCourse() throws SQLException {
        Course course = new Course("Course Name", "Course Description");
        courseService.createCourse(course);
        Course createdCourse = jdbcTemplate.query(TestDBConstants.SQL_GET_COURSE_BY_NAME,
                courseResultSetExtractor, course.getName());
        assertEquals(course, createdCourse);
    }

    @Test
    void updateCourse_shouldUpdateCourse_whenInputIsCourse() throws SQLException {
        Integer id = 3;
        Course course = jdbcTemplate.query(TestDBConstants.SQL_GET_COURSE_BY_ID,
                courseResultSetExtractor, id);
        course.setName("New name");
        course.setDescription("New description");
        courseService.updateCourse(course);
        Course updatedCourse = jdbcTemplate.query(TestDBConstants.SQL_GET_COURSE_BY_ID,
                courseResultSetExtractor, id);
        assertEquals(course.getName(), updatedCourse.getName());
        assertEquals(course.getDescription(), updatedCourse.getDescription());
    }

    @Test
    void deleteCourse_shouldDeleteCourse_whenInputIsIntegerId() throws SQLException {
        Integer id = 3;
        Course course = jdbcTemplate.query(TestDBConstants.SQL_GET_COURSE_BY_ID,
                courseResultSetExtractor, id);
        courseService.deleteCourseById(id);
        List<Course> courses = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_COURSES, new BeanPropertyRowMapper<>(Course.class));
        assertFalse(courses.contains(course));
    }

    @AfterEach
    void cleanEnvironmentAfterTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_COURSES);
    }

    @AfterAll
    void dropDataBaseAfterTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_DROP_DATABASE_AFTER_TESTS);
    }

}


