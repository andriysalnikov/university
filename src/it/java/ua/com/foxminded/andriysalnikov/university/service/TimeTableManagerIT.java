package ua.com.foxminded.andriysalnikov.university.service;

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
import ua.com.foxminded.andriysalnikov.university.mapper.EventMapper;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;
import ua.com.foxminded.andriysalnikov.university.service.TimeTableManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = TestSpringJdbcConfig.class)
@WebAppConfiguration
class TimeTableManagerIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TimeTableManager timeTableManager;
    @Autowired
    private EventMapper eventMapper;

    @BeforeAll
    void createDataBaseForTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_DATABASE_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_CLASSROOMS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_COURSES_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_EVENTS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_TEACHERS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_STUDENTS_FOR_TESTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TABLE_STUDENTS_COURSES_FOR_TESTS);
    }

    @BeforeEach
    void createEnvironmentForTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_CLASSROOMS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_EVENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_TEACHERS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_STUDENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CREATE_STUDENTS_COURSES);
    }

    @Test
    void getTimeTableFromStartDateToEndDateByUser_shouldReturnTimeTableContainingListOfEventsConstrainedByStartDateEndDate_whenArgumentsContainTeacher() {
        Teacher teacher = new Teacher(1, "Valery", "Pekar");
        TimeTable expectedTimeTable = new TimeTable();
        expectedTimeTable.setEvents(jdbcTemplate.query(
                TestDBConstants.SQL_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_TEACHER_ID,
                eventMapper, LocalDate.of(2022,5,30), LocalDate.of(2022,6,5), teacher.getId()));
        TimeTable returnedTimeTable = timeTableManager.getTimeTableFromStartDateToEndDateByUser(
                LocalDate.of(2022, 5, 30), LocalDate.of(2022, 6, 3), teacher);
        assertEquals(expectedTimeTable, returnedTimeTable);
    }

    @Test
    void getTimeTableFromStartDateToEndDateByUser_shouldReturnTimeTableContainingListOfEventsConstrainedByStartDateEndDate_whenArgumentsContainStudent() {
        Student student = new Student(1, "Olexiy", "Arestovich");
        TimeTable expectedTimeTable = new TimeTable();
        expectedTimeTable.setEvents(jdbcTemplate.query(
                TestDBConstants.SQL_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_STUDENT_ID,
                eventMapper, LocalDate.of(2022,5,30), LocalDate.of(2022,6,5), student.getId()));
        TimeTable returnedTimeTable = timeTableManager.getTimeTableFromStartDateToEndDateByUser(
                LocalDate.of(2022, 5, 30), LocalDate.of(2022, 6, 3), student);
        assertEquals(expectedTimeTable, returnedTimeTable);
    }

    @AfterEach
    void cleanEnvironmentAfterTest() {
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_STUDENTS_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_COURSES);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_STUDENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_TEACHERS);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_EVENTS);
        jdbcTemplate.execute(TestDBConstants.SQL_CLEAN_CLASSROOMS);
    }

    @AfterAll
    void dropDataBaseAfterTests() {
        jdbcTemplate.execute(TestDBConstants.SQL_DROP_DATABASE_AFTER_TESTS);
    }

}
