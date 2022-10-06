package ua.com.foxminded.andriysalnikov.university.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomDTO;

import java.util.List;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/create-database.sql", "classpath:sql/create-classrooms.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/drop-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClassRoomControllerIT {

    @LocalServerPort
    private int port;

    @Test
    void getAllClassRooms_shouldReturnListOfAllClassRooms() {
        List<ClassRoomDTO> expectedClassRooms = List.of(
            new ClassRoomDTO(Integer.toString(1), "101-A"),
            new ClassRoomDTO(Integer.toString(2), "202-B"),
            new ClassRoomDTO(Integer.toString(3), "202-C"),
            new ClassRoomDTO(Integer.toString(4), "Pool"),
            new ClassRoomDTO(Integer.toString(5), "Gym"));
        List<ClassRoomDTO> returnedClassRooms =
                when().get("http://localhost:" + port + "/university/classrooms/")
                    .then().statusCode(200)
                        .extract().body().jsonPath().getList(".", ClassRoomDTO.class);
        assertEquals(expectedClassRooms, returnedClassRooms);
    }

    @Test
    void getAllClassRoomsWithoutFaculty_shouldReturnListOfAllClassRoomsWithoutFaculty() {
        List<ClassRoomDTO> expectedClassRooms = List.of(
                new ClassRoomDTO(Integer.toString(2), "202-B"),
                new ClassRoomDTO(Integer.toString(3), "202-C"));
        List<ClassRoomDTO> returnedClassRooms =
                when().get("http://localhost:" + port + "/university/classrooms/without-faculty")
                    .then().statusCode(200)
                        .extract().body().jsonPath().getList(".", ClassRoomDTO.class);
        assertEquals(expectedClassRooms, returnedClassRooms);
    }

    @Test
    void getClassRoomById_shouldReturnClassRoom_whenInputIsIntegerId() {
        when().get("http://localhost:" + port + "/university/classrooms/{id}", 1)
            .then().statusCode(200)
                .body("id", equalTo(Integer.toString(1))).body("name", equalTo("101-A"));
        when().get("http://localhost:" + port + "/university/classrooms/{id}", 1000)
            .then().statusCode(400)
                .body("message", equalTo("Cannot get 'Classroom' from storage"));
    }

    @Test
    void deleteClassRoom_shouldDeleteClassRoom_whenInputIsIntegerId() {
        when().delete("http://localhost:" + port + "/university/classrooms/{id}", 1)
            .then().statusCode(204)
                .body(emptyString());
        when().delete("http://localhost:" + port + "/university/classrooms/{id}", 1)
            .then().statusCode(400)
                .body("message", equalTo("Cannot delete 'Classroom' from storage"));
    }

    @Test
    void createClassRoom_shouldCreateClassRoom_whenInputIsClassRoom() {
        ClassRoomCreateDTO classRoom = new ClassRoomCreateDTO();
        classRoom.setName("555-C");
        given().contentType(ContentType.JSON).body(classRoom)
            .when().post("http://localhost:" + port + "/university/classrooms")
                .then().statusCode(201)
                    .body("id", notNullValue()).body("name", equalTo("555-C"));
    }

    @Test
    void updateClassRoom_shouldUpdateCourse_whenInputIsClassRoom() {
        ClassRoomCreateDTO classRoom = new ClassRoomCreateDTO();
        classRoom.setName("555-C");
        given().contentType(ContentType.JSON).body(classRoom)
            .when().put("http://localhost:" + port + "/university/classrooms/{id}", 1)
                .then().statusCode(200)
                    .body("id", equalTo(Integer.toString(1))).body("name", equalTo("555-C"));
    }

}