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
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.FacultyService;
import ua.com.foxminded.andriysalnikov.university.extractors.FacultyResultSetExtractor;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringJdbcConfig.class)
@WebAppConfiguration
class FacultyServiceImplIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private FacultyResultSetExtractor facultyResultSetExtractor;

    @BeforeAll
    void createDataBaseForTests() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_DATABASE_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_FACULTIES_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_CLASSROOMS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_STUDENTS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_COURSES_FOR_TESTS);
    }

    @BeforeEach
    void createEnvironmentForTest() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_FACULTIES);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_STUDENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_CLASSROOMS);
    }

    @Test
    void getAllFaculties_shouldReturnListOfAllFaculties() throws SQLException {
        List<Faculty> expectedFaculty = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_FACULTIES, new BeanPropertyRowMapper<>(Faculty.class));
        List<Faculty> returnedFaculty = facultyService.getAllFaculties();
        assertEquals(expectedFaculty, returnedFaculty);
    }

    @Test
    void getFacultyById_shouldReturnFaculty_whenInputIsIntegerId() throws SQLException {
        Integer id = 4;
        Faculty expectedFaculty = jdbcTemplate.query(TestDBConstants.SQL_GET_FACULTY_BY_ID,
                facultyResultSetExtractor, id);
        Faculty returnedFaculty = facultyService.getFacultyById(id);
        assertEquals(expectedFaculty, returnedFaculty);
        assertThrows(ServiceException.class, () -> facultyService.getFacultyById(100));
    }

    @Test
    void getFacultyByIdWithCourses_shouldReturnFacultyWithCourses_whenArgumentIsIntegerId() {
        Integer id = 4;
        Faculty expectedFaculty = jdbcTemplate.query(TestDBConstants.SQL_GET_FACULTY_BY_ID,
                facultyResultSetExtractor, id);
        List<Course> expectedCourses = jdbcTemplate.query(
                TestDBConstants.SQL_GET_FACULTY_COURSES_BY_FACULTY_ID,
                    new BeanPropertyRowMapper<>(Course.class), id);
        Faculty returnedFaculty = facultyService.getFacultyByIdWithCourses(id);
        assertEquals(expectedFaculty, returnedFaculty);
        assertEquals(expectedCourses, returnedFaculty.getCourses());
    }

    @Test
    void getFacultyByIdWithStudents_shouldReturnFacultyWithStudents_whenArgumentIsIntegerId() {
        Integer id = 4;
        Faculty expectedFaculty = jdbcTemplate.query(TestDBConstants.SQL_GET_FACULTY_BY_ID,
                facultyResultSetExtractor, id);
        List<Student> expectedStudents = jdbcTemplate.query(
                TestDBConstants.SQL_GET_FACULTY_STUDENTS_BY_FACULTY_ID,
                    new BeanPropertyRowMapper<>(Student.class), id);
        Faculty returnedFaculty = facultyService.getFacultyByIdWithStudents(id);
        assertEquals(expectedFaculty, returnedFaculty);
        assertEquals(expectedStudents, returnedFaculty.getStudents());
    }

    @Test
    void getFacultyByIdWithClassRooms_shouldReturnFacultyWithClassRooms_whenArgumentIsIntegerId() {
        Integer id = 4;
        Faculty expectedFaculty = jdbcTemplate.query(TestDBConstants.SQL_GET_FACULTY_BY_ID,
                facultyResultSetExtractor, id);
        List<ClassRoom> expectedClassRooms = jdbcTemplate.query(
                TestDBConstants.SQL_GET_FACULTY_CLASSROOMS_BY_FACULTY_ID,
                    new BeanPropertyRowMapper<>(ClassRoom.class), id);
        Faculty returnedFaculty = facultyService.getFacultyByIdWithClassRooms(id);
        assertEquals(expectedFaculty, returnedFaculty);
        assertEquals(expectedClassRooms, returnedFaculty.getClassRooms());
    }

    @Test
    void createFaculty_shouldCreateFaculty_whenInputIsFaculty() throws SQLException {
        Faculty faculty = new Faculty("Faculty Full Name");
        facultyService.createFaculty(faculty);
        Faculty createdFaculty = jdbcTemplate.query(TestDBConstants.SQL_GET_FACULTY_BY_FULL_NAME,
                facultyResultSetExtractor, faculty.getFullName());
        assertEquals(faculty, createdFaculty);
    }

    @Test
    void updateFaculty_shouldUpdateFaculty_whenInputIsFaculty() throws SQLException {
        Integer id = 4;
        Faculty faculty = jdbcTemplate.query(TestDBConstants.SQL_GET_FACULTY_BY_ID,
                facultyResultSetExtractor, id);
        faculty.setFullName("New Faculty Name");
        facultyService.updateFaculty(faculty);
        Faculty updatedFaculty = jdbcTemplate.query(TestDBConstants.SQL_GET_FACULTY_BY_ID,
                facultyResultSetExtractor, id);
        assertEquals(faculty, updatedFaculty);
    }

    @Test
    void deleteFaculty_shouldDeleteFaculty_whenInputIsIntegerId() throws SQLException {
        Integer id = 4;
        Faculty faculty = jdbcTemplate.query(TestDBConstants.SQL_GET_FACULTY_BY_ID,
                facultyResultSetExtractor, id);
        facultyService.deleteFacultyById(id);
        List<Faculty> faculties = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_FACULTIES, new BeanPropertyRowMapper<>(Faculty.class));
        assertFalse(faculties.contains(faculty));
    }

    @AfterEach
    void cleanEnvironmentAfterTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_STUDENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_CLASSROOMS);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_FACULTIES);
    }

    @AfterAll
    void dropDataBaseAfterTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_DROP_DATABASE_AFTER_TESTS);
    }

}
