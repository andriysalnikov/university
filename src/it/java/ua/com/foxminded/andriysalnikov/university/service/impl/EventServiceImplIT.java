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

import ua.com.foxminded.andriysalnikov.university.config.TestSpringJdbcConfig;
import ua.com.foxminded.andriysalnikov.university.consnants.TestDBConstants;
import ua.com.foxminded.andriysalnikov.university.mapper.EventMapper;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.service.EventService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = TestSpringJdbcConfig.class)
class EventServiceImplIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventMapper eventMapper;

    @BeforeAll
    void createDataBaseForTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_DATABASE_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_CLASSROOMS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_COURSES_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_EVENTS_FOR_TESTS);
    }

    @BeforeEach
    void createEnvironmentForTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_CLASSROOMS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_EVENTS);
    }

    @Test
    void getAllEvents_shouldReturnListOfAllEvents() {
        List<Event> expectedEvents
                = jdbcTemplate.query(TestDBConstants.SQL_GET_ALL_EVENTS, eventMapper);
        List<Event> returnedEvents = eventService.getAllEvents();
        assertEquals(expectedEvents, returnedEvents);
    }

    @Test
    void getAllEventsFromStartDateToEndDateByCourseId_shouldReturnListOfEventsConstrainedByStartDateEndDate_whenArgumentsContainCourseId() {
        List<Event> expectedEvents
                = jdbcTemplate.query(TestDBConstants.SQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                eventMapper, LocalDate.of(2022,5,30), LocalDate.of(2022,6,5), 5);
        List<Event> returnedEvents = eventService
                .getAllEventsFromStartDateToEndDateByCourseId(
                        LocalDate.of(2022,5,30), LocalDate.of(2022,6,5), 5);
        assertEquals(expectedEvents, returnedEvents);
    }

    @AfterEach
    void cleanEnvironmentAfterTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_EVENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_CLASSROOMS);
    }

    @AfterAll
    void dropDataBaseAfterTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_DROP_DATABASE_AFTER_TESTS);
    }

}
