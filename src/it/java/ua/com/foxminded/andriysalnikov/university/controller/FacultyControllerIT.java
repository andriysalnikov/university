package ua.com.foxminded.andriysalnikov.university.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.andriysalnikov.university.dto.*;

import java.util.List;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/create-database.sql", "classpath:sql/create-faculties.sql",
        "classpath:sql/create-courses.sql", "classpath:sql/create-students.sql",
        "classpath:sql/create-classrooms.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/drop-database.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FacultyControllerIT {

    @LocalServerPort
    private int port;

    @Test
    void getAllFaculties_shouldReturnListOfAllFaculties() {
        List<FacultyDTO> expectedFaculties = List.of(
                new FacultyDTO(Integer.toString(1), "Faculty of Exact Sciences"),
                new FacultyDTO(Integer.toString(2), "Faculty of Linguistics"));
        List<FacultyDTO> returnedFaculties =
                when().get("http://localhost:" + port + "/university/faculties/")
                        .then().statusCode(200)
                        .extract().body().jsonPath().getList(".", FacultyDTO.class);
        assertEquals(expectedFaculties, returnedFaculties);
    }

    @Test
    void getFacultyById_shouldReturnFaculty_whenInputIsIntegerId() {
        when().get("http://localhost:" + port + "/university/faculties/{id}", 1)
            .then().statusCode(200)
                .body("id", equalTo(Integer.toString(1)))
                .body("fullName", equalTo("Faculty of Exact Sciences"));
        when().get("http://localhost:" + port + "/university/faculties/{id}", 100)
            .then().statusCode(400)
                .body("message", equalTo("Cannot get 'Faculty' from storage"));
    }

    @Test
    void getFacultyByIdWithCourses_shouldReturnFacultyWithCourses_whenArgumentIsIntegerId() {
        FacultyDTOWithCourses expectedFaculty =
                new FacultyDTOWithCourses(Integer.toString(1), "Faculty of Exact Sciences");
        List<String> courses = List.of("Mathematics", "Physics", "Chemistry");
        expectedFaculty.getCourses().addAll(courses);
        FacultyDTOWithCourses returnedFaculty =
                when().get("http://localhost:" + port + "/university/faculties/{id}/courses", 1)
                        .then().statusCode(200)
                            .extract().body().as(FacultyDTOWithCourses.class);
        assertEquals(expectedFaculty, returnedFaculty);
    }

    @Test
    void getFacultyByIdWithStudents_shouldReturnFacultyWithStudents_whenArgumentIsIntegerId() {
        FacultyDTOWithStudents expectedFaculty =
                new FacultyDTOWithStudents(Integer.toString(1), "Faculty of Exact Sciences");
        List<String> students = List.of("Stas Boklan", "Marichka Padalka",
                "Petro Porosheko", "Nestor Mahno");
        expectedFaculty.getStudents().addAll(students);
        FacultyDTOWithStudents returnedFaculty =
                when().get("http://localhost:" + port + "/university/faculties/{id}/students", 1)
                        .then().statusCode(200)
                            .extract().body().as(FacultyDTOWithStudents.class);
        assertEquals(expectedFaculty, returnedFaculty);
    }

    @Test
    void getFacultyByIdWithClassRooms_shouldReturnFacultyWithClassRooms_whenArgumentIsIntegerId() {
        FacultyDTOWithClassRooms expectedFaculty =
                new FacultyDTOWithClassRooms(Integer.toString(1), "Faculty of Exact Sciences");
        List<String> classRooms = List.of("101-A");
        expectedFaculty.getClassRooms().addAll(classRooms);
        FacultyDTOWithClassRooms returnedFaculty =
                when().get("http://localhost:" + port + "/university/faculties/{id}/classrooms", 1)
                        .then().statusCode(200)
                        .extract().body().as(FacultyDTOWithClassRooms.class);
        assertEquals(expectedFaculty, returnedFaculty);
    }

    @Test
    void createFaculty_shouldCreateFaculty_whenInputIsFaculty() {
        FacultyCreateDTO faculty = new FacultyCreateDTO();
        faculty.setFullName("Faculty of Fine Arts");
        given().contentType(ContentType.JSON).body(faculty)
            .when().post("http://localhost:" + port + "/university/faculties")
                .then().statusCode(201)
                    .body("id", notNullValue())
                    .body("fullName", equalTo("Faculty of Fine Arts"));
        faculty.setFullName("Something else");
        given().contentType(ContentType.JSON).body(faculty)
            .when().post("http://localhost:" + port + "/university/faculties")
                .then().statusCode(400)
                    .body("message", equalTo("Validation failed for object='facultyCreateDTO'. Error count: 1"));
    }

    @Test
    void updateFaculty_shouldUpdateFaculty_whenInputIsFaculty() {
        FacultyCreateDTO faculty = new FacultyCreateDTO();
        faculty.setFullName("Faculty of Jumping");
        given().contentType(ContentType.JSON).body(faculty)
            .when().put("http://localhost:" + port + "/university/faculties/{id}", 1)
                .then().statusCode(200)
                    .body("id", equalTo(Integer.toString(1)))
                    .body("fullName", equalTo("Faculty of Jumping"));
    }

    @Test
    void deleteFaculty_shouldDeleteFaculty_whenInputIsIntegerId() {
        when().delete("http://localhost:" + port + "/university/faculties/{id}", 1)
            .then().statusCode(204)
                .body(emptyString());
        when().delete("http://localhost:" + port + "/university/faculties/{id}", 1)
            .then().statusCode(400)
                .body("message", equalTo("Cannot delete 'Faculty' from storage"));
    }

}