package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.CourseCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.CourseDTO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.mapper.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES);
        List<Course> courses = courseService.getAllCourses();
        LOGGER.info(Messages.OK_GET_ALL_COURSES, courses);
        return new ResponseEntity<>(courses.stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/without-teacher")
    public ResponseEntity<List<CourseDTO>> getAllCoursesWithoutTeacher() {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES_WITHOUT_TEACHER);
        List<Course> courses = courseService.getAllCoursesWithoutTeacher();
        LOGGER.info(Messages.OK_GET_ALL_COURSES_WITHOUT_TEACHER, courses);
        return new ResponseEntity<>(courses.stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/without-faculty")
    public ResponseEntity<List<CourseDTO>> getAllCoursesWithoutFaculty() {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES_WITHOUT_FACULTY);
        List<Course> courses = courseService.getAllCoursesWithoutFaculty();
        LOGGER.info(Messages.OK_GET_ALL_COURSES_WITHOUT_FACULTY, courses);
        return new ResponseEntity<>(courses.stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_COURSE_BY_ID, id);
        Course course;
        try {
            course = courseService.getCourseById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_COURSE_BY_ID, id, course);
        return new ResponseEntity<>(courseMapper.toDTO(course), HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public void deleteCourse(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_COURSE_BY_ID,id);
        try {
            courseService.deleteCourseById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_DELETE_COURSE_BY_ID, id);
    }

    @PostMapping("/create")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseCreateDTO courseCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_COURSE);
        Course createdCourse;
        try {
            createdCourse = courseService.createCourse(courseMapper.fromDTO(courseCreateDTO));
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_COURSE, createdCourse);
        return new ResponseEntity<>(courseMapper.toDTO(createdCourse), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Integer id,
                                                  @Valid @RequestBody CourseCreateDTO courseCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_COURSE, courseCreateDTO);
        Course updatedCourse;
        try {
            courseService.getCourseById(id);
            Course course = courseMapper.fromDTO(courseCreateDTO);
            course.setId(id);
            updatedCourse = courseService.updateCourse(course);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_COURSE, updatedCourse);
        return new ResponseEntity<>(courseMapper.toDTO(updatedCourse), HttpStatus.OK);
    }

}
