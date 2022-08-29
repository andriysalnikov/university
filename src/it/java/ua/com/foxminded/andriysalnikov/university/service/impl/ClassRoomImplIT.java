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
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;
import ua.com.foxminded.andriysalnikov.university.extractors.ClassRoomResultSetExtractor;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringJdbcConfig.class)
@WebAppConfiguration
class ClassRoomImplIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ClassRoomService classRoomService;
    @Autowired
    private ClassRoomResultSetExtractor classRoomResultSetExtractor;

    @BeforeAll
    void createDataBaseForTests() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_DATABASE_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_CLASSROOMS_FOR_TESTS);
    }

    @BeforeEach
    void createEnvironmentForTest() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_CLASSROOMS);
    }

    @Test
    void getAllClassRooms_shouldReturnListOfAllClassRooms() throws SQLException {
        List<ClassRoom> expectedClassRooms = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_CLASSROOMS, new BeanPropertyRowMapper<>(ClassRoom.class));
        List<ClassRoom> returnedClassRooms = classRoomService.getAllClassRooms();
        assertEquals(expectedClassRooms, returnedClassRooms);
    }

    @Test
    void getAllClassRoomsWithoutFaculty_shouldReturnListOfAllClassRoomsWithoutFaculty() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_CLASSROOMS_WITHOUT_FACULTY);
        List<ClassRoom> expectedClassRooms = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_CLASSROOMS_WITHOUT_FACULTY,
                    new BeanPropertyRowMapper<>(ClassRoom.class));
        List<ClassRoom> returnedClassRooms = classRoomService.getAllClassRoomsWithoutFaculty();
        assertEquals(expectedClassRooms, returnedClassRooms);
    }

    @Test
    void getClassRoomById_shouldReturnClassRoom_whenInputIsIntegerId() throws SQLException {
        Integer id = 7;
        ClassRoom expectedClassRoom = jdbcTemplate.query(TestDBConstants.SQL_GET_CLASSROOM_BY_ID,
                classRoomResultSetExtractor, id);
        ClassRoom returnedClassRoom = classRoomService.getClassRoomById(id);
        assertEquals(expectedClassRoom, returnedClassRoom);
        assertThrows(ServiceException.class, () -> classRoomService.getClassRoomById(1000));
    }

    @Test
    void createClassRoom_shouldCreateClassRoom_whenInputIsClassRoom() throws SQLException {
        ClassRoom classRoom = new ClassRoom("ClassRoom Name");
        classRoomService.createClassRoom(classRoom);
        ClassRoom createdClassRoom = jdbcTemplate.query(TestDBConstants.SQL_GET_CLASSROOM_BY_NAME,
                classRoomResultSetExtractor, classRoom.getName());
        assertEquals(classRoom, createdClassRoom);
    }

    @Test
    void updateClassRoom_shouldUpdateCourse_whenInputIsClassRoom() throws SQLException {
        Integer id = 7;
        ClassRoom classRoom = jdbcTemplate.query(TestDBConstants.SQL_GET_CLASSROOM_BY_ID,
                classRoomResultSetExtractor, id);
        classRoom.setName("New ClassRoom Name");
        classRoomService.updateClassRoom(classRoom);
        ClassRoom updatedClassRoom = jdbcTemplate.query(TestDBConstants.SQL_GET_CLASSROOM_BY_ID,
                classRoomResultSetExtractor, id);
        assertEquals(classRoom, updatedClassRoom);
    }

    @Test
    void deleteClassRoom_shouldDeleteClassRoom_whenInputIsIntegerId() throws SQLException {
        Integer id = 7;
        ClassRoom classRoom = jdbcTemplate.query(TestDBConstants.SQL_GET_CLASSROOM_BY_ID,
                classRoomResultSetExtractor, id);
        classRoomService.deleteClassRoomById(id);
        List<ClassRoom> classRooms = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_CLASSROOMS, new BeanPropertyRowMapper<>(ClassRoom.class));
        assertFalse(classRooms.contains(classRoom));
    }

    @AfterEach
    void cleanEnvironmentAfterTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_CLASSROOMS);
    }

    @AfterAll
    void dropDataBaseAfterTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_DROP_DATABASE_AFTER_TESTS);
    }

}