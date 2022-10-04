package ua.com.foxminded.andriysalnikov.university.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.StudentCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.StudentDTO;
import ua.com.foxminded.andriysalnikov.university.dto.StudentDTOWithCourses;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
@Api("Controller to work with 'Students'")
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    @ApiOperation("Getting the List of All 'Students'")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        LOGGER.info(Messages.TRY_GET_ALL_STUDENTS);
        List<StudentDTO> studentDTOs = studentService.getAllStudentDTOs();
        LOGGER.info(Messages.OK_GET_ALL_STUDENTS, studentDTOs);
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }

    @GetMapping("/without-faculty")
    @ApiOperation("Getting the List of All 'Students' without 'Faculty' ('Faculty' is null)")
    public ResponseEntity<List<StudentDTO>> getAllStudentsWithoutFaculty() {
        LOGGER.info(Messages.TRY_GET_ALL_STUDENTS_WITHOUT_FACULTY);
        List<StudentDTO> studentDTOs = studentService.getAllStudentDTOsWithoutFaculty();
        LOGGER.info(Messages.OK_GET_ALL_STUDENTS_WITHOUT_FACULTY, studentDTOs);
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Getting the 'Student' by 'id'")
    public ResponseEntity<StudentDTO> getStudentById(
            @ApiParam(value = "The Student id", required = true) @PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_STUDENT_BY_ID, id);
        StudentDTO studentDTO = studentService.getStudentDTOById(id);
        LOGGER.info(Messages.OK_GET_STUDENT_BY_ID, id, studentDTO);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    @ApiOperation("Getting the 'Student' by 'id' with List of 'Courses'")
    public ResponseEntity<StudentDTOWithCourses> getStudentByIdWithCourses(
            @ApiParam(value = "The Student id", required = true) @PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_STUDENT_BY_ID, id);
        StudentDTOWithCourses studentDTOWithCourses =
                studentService.getStudentDTOByIdWithCourses(id);
        LOGGER.info(Messages.OK_GET_STUDENT_BY_ID, id, studentDTOWithCourses);
        return new ResponseEntity<>(studentDTOWithCourses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deleting the 'Student' by 'id'")
    public ResponseEntity<HttpStatus> deleteStudent(
            @ApiParam(value = "The Student id", required = true) @PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_STUDENT_BY_ID,id);
        studentService.deleteStudentById(id);
        LOGGER.info(Messages.OK_DELETE_STUDENT_BY_ID, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ApiOperation("Creating the 'Student'")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentCreateDTO studentCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_STUDENT);
        StudentDTO createdStudentDTO = studentService.createStudentDTO(studentCreateDTO);
        LOGGER.info(Messages.OK_CREATE_STUDENT, createdStudentDTO);
        return new ResponseEntity<>(createdStudentDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Updating the 'Student' by 'id'")
    public ResponseEntity<StudentDTO> updateStudent(
            @ApiParam(value = "The Student id", required = true) @PathVariable Integer id,
                                                    @Valid @RequestBody StudentCreateDTO studentCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_STUDENT, studentCreateDTO);
        studentService.getStudentById(id);
        StudentDTO updatedStudentDTO = studentService.updateStudentDTO(id, studentCreateDTO);
        LOGGER.info(Messages.OK_UPDATE_STUDENT, updatedStudentDTO);
        return new ResponseEntity<>(updatedStudentDTO, HttpStatus.OK);
    }

    @PostMapping("/{studentId}/add-course/{courseId}")
    @ApiOperation("Adding the 'Course' by 'id' to 'Student' by 'id'")
    public ResponseEntity<StudentDTOWithCourses> addCourseToStudent(
            @ApiParam(value = "The Student id", required = true) @PathVariable Integer studentId,
            @ApiParam(value = "The Course id", required = true) @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_ADD_COURSE_TO_STUDENT, studentId, courseId);
        Course course = courseService.getCourseById(courseId);
        Student student = studentService.getStudentByIdWithCourses(studentId);
        student.getCourses().add(course);
        studentService.updateStudent(student);
        StudentDTOWithCourses studentDTOWithCourses =
                studentService.getStudentDTOByIdWithCourses(studentId);
        LOGGER.info(Messages.OK_ADD_COURSE_TO_STUDENT, studentId, courseId, course);
        return new ResponseEntity<>(studentDTOWithCourses, HttpStatus.OK);
    }

    @PostMapping("/{studentId}/remove-course/{courseId}")
    @ApiOperation("Removing the 'Course' by 'id' from 'Student' by 'id'")
    public ResponseEntity<StudentDTOWithCourses> removeCourseFromStudent(
            @ApiParam(value = "The Student id", required = true) @PathVariable Integer studentId,
            @ApiParam(value = "The Course id", required = true) @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_STUDENT, courseId, studentId);
        Student student = studentService.getStudentByIdWithCourses(studentId);
        Course course = courseService.getCourseById(courseId);
        student.getCourses().remove(course);
        studentService.updateStudent(student);
        StudentDTOWithCourses studentDTOWithCourses =
                studentService.getStudentDTOByIdWithCourses(studentId);
        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_STUDENT, courseId, studentId, course);
        return new ResponseEntity<>(studentDTOWithCourses, HttpStatus.OK);
    }

}