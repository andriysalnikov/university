package ua.com.foxminded.andriysalnikov.university.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.marker.View;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @JsonView(View.WithoutDependencies.class)
    public List<Course> getAllCourses() {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES);
        List<Course> courses = courseService.getAllCourses();
        LOGGER.info(Messages.OK_GET_ALL_COURSES, courses);
        return courses;
    }

    @GetMapping("/without-teacher")
    @JsonView(View.WithoutDependencies.class)
    public List<Course> getAllCoursesWithoutTeacher() {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES_WITHOUT_TEACHER);
        List<Course> courses = courseService.getAllCoursesWithoutTeacher();
        LOGGER.info(Messages.OK_GET_ALL_COURSES_WITHOUT_TEACHER, courses);
        return courses;
    }

    @GetMapping("/without-faculty")
    @JsonView(View.WithoutDependencies.class)
    public List<Course> getAllCoursesWithoutFaculty() {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES_WITHOUT_FACULTY);
        List<Course> courses = courseService.getAllCoursesWithoutFaculty();
        LOGGER.info(Messages.OK_GET_ALL_COURSES_WITHOUT_FACULTY, courses);
        return courses;
    }

    @GetMapping("/{id}")
    @JsonView(View.WithoutDependencies.class)
    public Course getCourseById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_COURSE_BY_ID, id);
        Course course;
        try {
            course = courseService.getCourseById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_COURSE_BY_ID, id, course);
        return course;
    }

    @PostMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.WithoutDependencies.class)
    public Course createCourse(@Valid @RequestBody Course course) {
        LOGGER.info(Messages.TRY_CREATE_COURSE);
        Course createdCourse;
        try {
            createdCourse = courseService.createCourse(course);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_COURSE, createdCourse);
        return createdCourse;
    }

    @PostMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.WithoutDependencies.class)
    public Course updateCourse(@PathVariable Integer id, @Valid @RequestBody Course course) {
        LOGGER.info(Messages.TRY_UPDATE_COURSE, course);
        Course updatedCourse;
        try {
            courseService.getCourseById(id);
            course.setId(id);
            updatedCourse = courseService.updateCourse(course);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_COURSE, updatedCourse);
        return updatedCourse;
    }

}
