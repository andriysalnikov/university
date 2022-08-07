package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.utils.ExceptionUtil;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;

import java.util.List;

@Controller
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String getAllCourses(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_COURSES);
        List<Course> courses = courseService.getAllCourses();
        LOGGER.info(Messages.OK_GET_ALL_COURSES, courses);
        model.addAttribute("courses", courses);
        return "course/courses";
    }

    @GetMapping("/course/create")
    public String getCreationCourseModalWindow() {
        LOGGER.info(Messages.TRY_CREATE_COURSE);
        return "course/course_create";
    }

    @PostMapping("/course/create")
    public String createCourse(@RequestParam("name") String name,
                               @RequestParam("description") String description,
                               Model model) {
        Course createdCourse;
        try {
            createdCourse = courseService.createCourse(new Course(0, name, description));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_CREATE_COURSE, createdCourse);
        return "redirect:/courses";
    }

    @PostMapping("/course/delete")
    public String deleteCourse(@RequestParam("id") Integer courseId, Model model) {
        LOGGER.info(Messages.TRY_DELETE_COURSE_BY_ID, courseId);
        Course deletedCourse;
        try {
            deletedCourse = courseService.deleteCourseById(courseId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_DELETE_COURSE_BY_ID, courseId, deletedCourse);
        return "redirect:/courses";
    }

    @GetMapping("/course/update")
    public String getUpdationCourseModalWindow(@RequestParam("id") Integer courseId, Model model) {
        Course course;
        try {
            course = courseService.getCourseById(courseId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.TRY_UPDATE_COURSE, course);
        model.addAttribute("course", course);
        return "course/course_update";
    }

    @PostMapping("/course/update")
    public String updateCourse(@RequestParam("id") Integer courseId,
                               @RequestParam("name") String name,
                               @RequestParam("description") String description,
                               Model model) {
        Course updatedCourse;
        try {
            updatedCourse = courseService.updateCourse(new Course(courseId, name, description));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_UPDATE_COURSE, updatedCourse);
        return "redirect:/courses";
    }

}
