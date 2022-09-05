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
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.utils.ExceptionUtil;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import java.util.List;

@Controller
public class TeacherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public TeacherController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping("/teachers")
    public String getAllTeachers(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_TEACHERS);
        List<Teacher> teachers = teacherService.getAllTeachers();
        LOGGER.info(Messages.OK_GET_ALL_TEACHERS, teachers);
        model.addAttribute("teachers", teachers);
        return "teacher/teachers";
    }

    @GetMapping("/teacher")
    public String showTeacher(@RequestParam("id") Integer id, Model model) {

        Teacher teacher;
        List<Course> otherAvailableCourses;
        try {
            teacher = teacherService.getTeacherByIdWithCourses(id);
            otherAvailableCourses = courseService.getAllCoursesWithoutTeacher();
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("teacher", teacher);
        model.addAttribute("othercourses", otherAvailableCourses);
        return "teacher/teacher";
    }

    @GetMapping("/teacher/create")
    public String getCreationTeacherModalWindow(Model model) {
        LOGGER.info(Messages.TRY_CREATE_TEACHER);
        return "teacher/teacher_create";
    }

    @PostMapping("/teacher/create")
    public String createTeacher(@RequestParam("first_name") String firstName,
                                @RequestParam("last_name") String lastName,
                                Model model) {
        Teacher createdTeacher;
        try {
            createdTeacher = teacherService.createTeacher(new Teacher(firstName, lastName));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_CREATE_TEACHER, createdTeacher);
        return "redirect:/teachers";
    }

    @PostMapping("/teacher/delete")
    public String deleteTeacher(@RequestParam("id") Integer id, Model model) {
        LOGGER.info(Messages.TRY_DELETE_TEACHER_BY_ID,id);
        try {
            teacherService.deleteTeacherById(id);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_DELETE_TEACHER_BY_ID, id);
        return "redirect:/teachers";
    }

    @GetMapping("/teacher/update")
    public String getUpdationTeacherModalWindow(@RequestParam("id") Integer id, Model model) {
        Teacher teacher;
        try {
            teacher = teacherService.getTeacherById(id);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.TRY_UPDATE_TEACHER, teacher);
        model.addAttribute("teacher", teacher);
        return "teacher/teacher_update";
    }

    @PostMapping("/teacher/update")
    public String updateTeacher(@RequestParam("id") Integer id,
                                @RequestParam("first_name") String firstName,
                                @RequestParam("last_name") String lastName,
                                Model model) {
        Teacher updatedTeacher;
        try {
            Teacher teacher = new Teacher(firstName, lastName);
            teacher.setId(id);
            updatedTeacher = teacherService.updateTeacher(teacher);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_UPDATE_TEACHER, updatedTeacher);
        return "redirect:/teachers";
    }

    @PostMapping("/teacher/add_course")
    public String addCourseToTeacher(@RequestParam("teacher_id") Integer teacherId,
                                     @RequestParam("course_id") Integer courseId,
                                     Model model) {
        LOGGER.info(Messages.TRY_ADD_COURSE_TO_TEACHER, teacherId, courseId);
        Course course;
        try {
            Teacher teacher = teacherService.getTeacherByIdWithCourses(teacherId);
            course = courseService.getCourseById(courseId);
            course.setTeacher(teacher);
            courseService.updateCourse(course);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_ADD_COURSE_TO_TEACHER, teacherId, courseId, course);
        return "redirect:/teacher?&id=" + teacherId;
    }

    @PostMapping("/teacher/remove_course")
    public String removeCourseFromTeacher(@RequestParam("teacher_id") Integer teacherId,
                                          @RequestParam("course_id") Integer courseId,
                                          Model model) {
        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId);
        Course course;
        try {
            course = courseService.getCourseById(courseId);
            course.setTeacher(null);
            courseService.updateCourse(course);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId, course);
        return "redirect:/teacher?&id=" + teacherId;
    }

}
