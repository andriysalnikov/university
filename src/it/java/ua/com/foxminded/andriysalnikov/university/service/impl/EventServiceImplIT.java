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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.foxminded.andriysalnikov.university.config.TestSpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.consnants.TestDBConstants;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.extractors.ClassRoomResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.extractors.CourseResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.extractors.EventResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.service.EventService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringJdbcConfig.class)
@WebAppConfiguration
class EventServiceImplIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventResultSetExtractor eventResultSetExtractor;
    @Autowired
    private CourseResultSetExtractor courseResultSetExtractor;
    @Autowired
    private ClassRoomResultSetExtractor classRoomResultSetExtractor;

    @BeforeAll
    void createDataBaseForTests() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_DATABASE_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_EVENTS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_CLASSROOMS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_COURSES_FOR_TESTS);
    }

    @BeforeEach
    void createEnvironmentForTest() throws SQLException {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_EVENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_CLASSROOMS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_COURSES);
    }

    @Test
    void getAllEvents_shouldReturnListOfAllEvents() throws SQLException {
        List<Event> expectedEvents = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_EVENTS, new EventMapper());
        List<Event> returnedEvents = eventService.getAllEvents();
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    void getEventById_shouldReturnEvent_whenInputIsIntegerId() throws SQLException {
        Integer id = 3;
        Event expectedEvent = jdbcTemplate.query(TestDBConstants.SQL_GET_EVENT_BY_ID,
                eventResultSetExtractor, id);
        Event returnedEvent = eventService.getEventById(id);
        assertEquals(expectedEvent, returnedEvent);
        assertThrows(ServiceException.class, () -> eventService.getEventById(1000));
    }

    @Test
    void createEvent_shouldCreateEvent_whenInputIsEvent() throws SQLException {
        Integer courseId = 1;
        Integer classRoomId = 1;
        LocalDate dayOfEvent = LocalDate.of(2022, 9, 1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(11, 0);
        Course course = jdbcTemplate.query(TestDBConstants.SQL_GET_COURSE_BY_ID,
                courseResultSetExtractor, courseId);
        ClassRoom classRoom = jdbcTemplate.query(TestDBConstants.SQL_GET_CLASSROOM_BY_ID,
                classRoomResultSetExtractor, classRoomId);
        Event event = new Event(dayOfEvent, startTime, endTime, classRoom, course);
        eventService.createEvent(event);
        Event createdEvent = jdbcTemplate.query(TestDBConstants.SQL_GET_EVENT_BY_PARAMETERS,
                eventResultSetExtractor, dayOfEvent, startTime, course.getName());
        assertEquals(event, createdEvent);
    }

    @Test
    void updateEvent_shouldUpdateEvent_whenInputIsEvent() throws SQLException {
        Integer id = 3;
        Event event = jdbcTemplate.query(TestDBConstants.SQL_GET_EVENT_BY_ID,
                eventResultSetExtractor, id);
        event.setDayOfEvent(LocalDate.of(2022, 9, 1));
        event.setStartTime(LocalTime.of(9, 0));
        event.setEndTime(LocalTime.of(9, 0));
        eventService.updateEvent(event);
        Event updatedEvent = jdbcTemplate.query(TestDBConstants.SQL_GET_EVENT_BY_ID,
                eventResultSetExtractor, id);
        assertEquals(event, updatedEvent);
    }

    @Test
    void deleteEvent_shouldDeleteEvent_whenInputIsIntegerId() throws SQLException {
        Integer id = 3;
        Event event = jdbcTemplate.query(TestDBConstants.SQL_GET_EVENT_BY_ID,
                eventResultSetExtractor, id);
        eventService.deleteEventById(id);
        List<Event> events = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_EVENTS, new EventMapper());
        assertFalse(events.contains(event));
    }

    @Test
    void getAllEventsFromStartDateToEndDateByCourseId_shouldReturnListOfAllEventsConstraintByParameters() throws SQLException {
        LocalDate startDate = LocalDate.of(2022, 5, 30);
        LocalDate endDate = LocalDate.of(2022, 6, 3);
        Integer courseId = 5;
        List<Event> expectedEvents = jdbcTemplate.query(
                TestDBConstants.SQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID, new EventMapper(),
                    startDate, endDate, courseId);
        List<Event> returnedEvents = eventService
                .getAllEventsFromStartDateToEndDateByCourseId(startDate, endDate, courseId);
        assertEquals(expectedEvents, returnedEvents);
    }

    @AfterEach
    void cleanEnvironmentAfterTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_CLASSROOMS);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_EVENTS);
    }

    @AfterAll
    void dropDataBaseAfterTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_DROP_DATABASE_AFTER_TESTS);
    }

    private static class EventMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            ClassRoom classRoom = new ClassRoom(rs.getString("classroom_name"));
            classRoom.setId(rs.getInt("classroom_id"));
            Course course = new Course(rs.getString("course_name"), rs.getString("description"));
            course.setId(rs.getInt("course_id"));
            Event event = new Event(
                    rs.getObject("date_of_event", LocalDate.class),
                    rs.getObject("start_time", LocalTime.class),
                    rs.getObject("end_time", LocalTime.class),
                    classRoom, course);
            event.setId(rs.getInt("id"));
            return event;
        }
    }

}




