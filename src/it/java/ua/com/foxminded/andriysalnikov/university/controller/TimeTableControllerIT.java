package ua.com.foxminded.andriysalnikov.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.andriysalnikov.university.dto.EventDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TimeTableDTO;

import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/create-database.sql", "classpath:sql/create-events.sql",
        "classpath:sql/create-courses.sql", "classpath:sql/create-students.sql",
        "classpath:sql/create-classrooms.sql", "classpath:sql/create-students-courses.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/drop-database.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TimeTableControllerIT {

    @LocalServerPort
    private int port;

    @Test
    void getTimeTableFromStartDateToEndDateForStudent_shouldReturnListOfEventsConstrainedByStartDateEndDate_whenArgumentsContainStudentId() {

        TimeTableDTO expectedTimeTable = new TimeTableDTO(List.of(
                new EventDTO("24", "2022-06-01", "09:00", "11:00", "Pool", "Physics"),
                new EventDTO("23", "2022-06-01", "09:00", "11:00", "202-C", "Chemistry"),
                new EventDTO("25", "2022-06-01", "11:00", "13:00", "Gym", "Mathematics"),
                new EventDTO("27", "2022-06-01", "11:00", "13:00", "202-B", "Ukrainian language"),
                new EventDTO("26", "2022-06-01", "11:00", "13:00", "101-A", "English language"),
                new EventDTO("30", "2022-06-01", "13:00", "15:00", "Gym", "Mathematics"),
                new EventDTO("29", "2022-06-01", "13:00", "15:00", "Pool", "Physics"),
                new EventDTO("28", "2022-06-01", "13:00", "15:00", "202-C", "Chemistry"),
                new EventDTO("32", "2022-06-01", "15:00", "17:00", "202-B", "Ukrainian language"),
                new EventDTO("31", "2022-06-01", "15:00", "17:00", "101-A", "English language"),
                new EventDTO("33", "2022-06-01", "17:00", "19:00", "202-C", "Chemistry"),
                new EventDTO("35", "2022-06-02", "09:00", "11:00", "Gym", "Mathematics"),
                new EventDTO("34", "2022-06-02", "09:00", "11:00", "Pool", "Physics"),
                new EventDTO("38", "2022-06-02", "11:00", "13:00", "202-C", "Chemistry"),
                new EventDTO("37", "2022-06-02", "11:00", "13:00", "202-B", "Ukrainian language"),
                new EventDTO("36", "2022-06-02", "11:00", "13:00", "101-A", "English language"),
                new EventDTO("40", "2022-06-02", "13:00", "15:00", "Gym", "Mathematics"),
                new EventDTO("39", "2022-06-02", "13:00", "15:00", "Pool", "Physics"),
                new EventDTO("41", "2022-06-02", "13:00", "15:00", "101-A", "English language"),
                new EventDTO("43", "2022-06-02", "15:00", "17:00", "202-C", "Chemistry"),
                new EventDTO("42", "2022-06-02", "15:00", "17:00", "202-B", "Ukrainian language"),
                new EventDTO("44", "2022-06-02", "17:00", "19:00", "Pool", "Physics")
        ));
        TimeTableDTO returnedTimeTable = when().get("http://localhost:" + port + "/university/timetable/" +
            "student/{id}/start-date/{startDate}/end-date/{endDate}", 1, "2022-06-01", "2022-06-02")
            .then().statusCode(200).extract().body().as(TimeTableDTO.class);

        assertEquals(expectedTimeTable, returnedTimeTable);

        when().get("http://localhost:" + port + "/university/timetable/" +
            "student/{id}/start-date/{startDate}/end-date/{endDate}", 1, "2022-06-01", "2022-05-30")
            .then().statusCode(400)
                .body("message",equalTo("'StartDate' cannot be after 'EndDate'"));

    }

}