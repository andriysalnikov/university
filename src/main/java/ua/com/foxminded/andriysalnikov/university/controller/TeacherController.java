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
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public TeacherController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping
    @JsonView(View.WithoutCourses.class)
    public List<Teacher> getAllTeachers() {
        LOGGER.info(Messages.TRY_GET_ALL_TEACHERS);
        List<Teacher> teachers = teacherService.getAllTeachers();
        LOGGER.info(Messages.OK_GET_ALL_TEACHERS, teachers);
        return teachers;
    }

    @GetMapping("/{id}")
    @JsonView(View.WithoutCourses.class)
    public Teacher getTeacherById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_TEACHER_BY_ID, id);
        Teacher teacher;
        try {
            teacher = teacherService.getTeacherById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }

        LOGGER.info(Messages.OK_GET_TEACHER_BY_ID, id, teacher);
        return teacher;
    }

    @GetMapping("/{id}/courses")
    @JsonView(View.WithCourses.class)
    public Teacher getTeacherByIdWithCourses(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_TEACHER_BY_ID, id);
        Teacher teacher;
        try {
            teacher = teacherService.getTeacherByIdWithCourses(id);
        } catch (ServiceException exception) {
            System.out.println(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_TEACHER_BY_ID, id, teacher);
        return teacher;
    }

    @PostMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacher(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_TEACHER_BY_ID,id);
        try {
            teacherService.deleteTeacherById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_DELETE_TEACHER_BY_ID, id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.WithoutCourses.class)
    public Teacher createTeacher(@Valid @RequestBody Teacher teacher) {
        LOGGER.info(Messages.TRY_CREATE_TEACHER);
        Teacher createdTeacher;
        try {
            createdTeacher = teacherService.createTeacher(teacher);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_TEACHER, createdTeacher);
        return createdTeacher;
    }

    @PostMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.WithoutCourses.class)
    public Teacher updateTeacher(@PathVariable Integer id, @Valid @RequestBody Teacher teacher) {
        LOGGER.info(Messages.TRY_UPDATE_TEACHER, teacher);
        Teacher updatedTeacher;
        try {
            teacherService.getTeacherById(id);
            teacher.setId(id);
            updatedTeacher = teacherService.updateTeacher(teacher);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_TEACHER, updatedTeacher);
        return updatedTeacher;
    }

    @PostMapping("/{teacherId}/add-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.WithCourses.class)
    public Teacher addCourseToTeacher(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_ADD_COURSE_TO_TEACHER, teacherId, courseId);
        Course course;
        Teacher teacher;
        try {
            course = courseService.getCourseById(courseId);
            System.out.println(course.getTeacher());
            if (course.getTeacher() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        Messages.ERROR_ADD_COURSE_TO_TEACHER);
            }
            teacher = teacherService.getTeacherById(teacherId);
            course.setTeacher(teacher);
            courseService.updateCourse(course);
            teacher = teacherService.getTeacherByIdWithCourses(teacherId);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_ADD_COURSE_TO_TEACHER, teacherId, courseId, course);
        return teacher;
    }

    @PostMapping("/{teacherId}/remove-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.WithCourses.class)
    public Teacher removeCourseFromTeacher(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId);
        Course course;
        Teacher teacher;
        try {
            course = courseService.getCourseById(courseId);
            System.out.println(course.getTeacher());
            if (course.getTeacher() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        Messages.ERROR_REMOVE_COURSE_FROM_TEACHER);
            }
            course.setTeacher(null);
            courseService.updateCourse(course);
            teacher = teacherService.getTeacherByIdWithCourses(teacherId);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId, course);
        return teacher;
    }

}
