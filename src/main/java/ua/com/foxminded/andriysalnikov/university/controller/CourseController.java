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
import ua.com.foxminded.andriysalnikov.university.dto.CourseCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.CourseDTO;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
@Api("Controller to work with 'Courses'")
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @ApiOperation("Getting the List of All 'Classrooms'")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES);
        List<CourseDTO> courseDTOs = courseService.getAllCourseDTOs();
        LOGGER.info(Messages.OK_GET_ALL_COURSES, courseDTOs);
        return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
    }

    @GetMapping("/without-teacher")
    @ApiOperation("Getting the List of All 'Courses' without 'Teacher' ('Teacher' is null)")
    public ResponseEntity<List<CourseDTO>> getAllCoursesWithoutTeacher() {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES_WITHOUT_TEACHER);
        List<CourseDTO> courseDTOs = courseService.getAllCourseDTOsWithoutTeacher();
        LOGGER.info(Messages.OK_GET_ALL_COURSES_WITHOUT_TEACHER, courseDTOs);
        return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
    }

    @GetMapping("/without-faculty")
    @ApiOperation("Getting the List of All 'Courses' without 'Faculty' ('Faculty' is null)")
    public ResponseEntity<List<CourseDTO>> getAllCoursesWithoutFaculty() {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES_WITHOUT_FACULTY);
        List<CourseDTO> courseDTOs = courseService.getAllCourseDTOsWithoutFaculty();
        LOGGER.info(Messages.OK_GET_ALL_COURSES_WITHOUT_FACULTY, courseDTOs);
        return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Getting the 'Course' by 'id'")
    public ResponseEntity<CourseDTO> getCourseById(
            @ApiParam(value = "The Course id", required = true) @PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_COURSE_BY_ID, id);
        CourseDTO courseDTO = courseService.getCourseDTOById(id);
        LOGGER.info(Messages.OK_GET_COURSE_BY_ID, id, courseDTO);
        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deleting the 'Course' by 'id'")
    public ResponseEntity<HttpStatus> deleteCourse(
            @ApiParam(value = "The Course id", required = true) @PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_COURSE_BY_ID,id);
        courseService.deleteCourseById(id);
        LOGGER.info(Messages.OK_DELETE_COURSE_BY_ID, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ApiOperation("Creating the 'Course'")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseCreateDTO courseCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_COURSE);
        CourseDTO createdCourseDTO =
                courseService.createCourseDTO(courseCreateDTO);
        LOGGER.info(Messages.OK_CREATE_COURSE, createdCourseDTO);
        return new ResponseEntity<>(createdCourseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Updating the 'Course' by 'id'")
    public ResponseEntity<CourseDTO> updateCourse(
            @ApiParam(value = "The Course id", required = true) @PathVariable Integer id,
                                                  @Valid @RequestBody CourseCreateDTO courseCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_COURSE, courseCreateDTO);
        courseService.getCourseById(id);
        CourseDTO updatedCourseDTO =  courseService.updateCourseDTO(id, courseCreateDTO);
        LOGGER.info(Messages.OK_UPDATE_COURSE, updatedCourseDTO);
        return new ResponseEntity<>(updatedCourseDTO, HttpStatus.OK);
    }

}
