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
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.utils.ExceptionUtil;

import java.util.List;

@Controller
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_STUDENTS);
        List<Student> students = studentService.getAllStudents();
        LOGGER.info(Messages.OK_GET_ALL_STUDENTS, students);
        model.addAttribute("students", students);
        return "student/students";
    }

    @GetMapping("/student")
    public String showStudent(@RequestParam("id") Integer id, Model model) {
        Student student;
        List<Course> otherAvailableCourses;
        try {
            student = studentService.getStudentByIdWithCourses(id);
            otherAvailableCourses = courseService.getAllCourses();
            otherAvailableCourses.removeAll(student.getCourses());
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("student", student);
        model.addAttribute("othercourses", otherAvailableCourses);
        return "student/student";
    }

    @GetMapping("/student/create")
    public String getCreationStudentModalWindow(Model model) {
        LOGGER.info(Messages.TRY_CREATE_STUDENT);
        return "student/student_create";
    }

    @PostMapping("/student/create")
    public String createStudent(@RequestParam("first_name") String firstName,
                                @RequestParam("last_name") String lastName,
                                Model model) {
        Student createdStudent;
        try {
            createdStudent = studentService.createStudent(new Student(firstName, lastName));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_CREATE_STUDENT, createdStudent);
        return "redirect:/students";
    }

    @PostMapping("/student/delete")
    public String deleteStudent(@RequestParam("id") Integer id, Model model) {
        LOGGER.info(Messages.TRY_DELETE_STUDENT_BY_ID,id);
        Student deletedStudent;
        try {
            deletedStudent = studentService.deleteStudentById(id);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_DELETE_STUDENT_BY_ID, id, deletedStudent);
        return "redirect:/students";
    }

    @GetMapping("/student/update")
    public String getUpdationStudentModalWindow(@RequestParam("id") Integer id, Model model) {
        Student student;
        try {
            student = studentService.getStudentById(id);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.TRY_UPDATE_STUDENT, student);
        model.addAttribute("student", student);
        return "student/student_update";
    }

    @PostMapping("/student/update")
    public String updateStudent(@RequestParam("id") Integer id,
                                @RequestParam("first_name") String firstName,
                                @RequestParam("last_name") String lastName,
                                Model model) {
        Student updatedStudent;
        try {
            Student student = studentService.getStudentById(id);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            updatedStudent = studentService.updateStudent(student);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_UPDATE_STUDENT, updatedStudent);
        return "redirect:/students";
    }

    @PostMapping("/student/add_course")
    public String addCourseToStudent(@RequestParam("student_id") Integer studentId,
                                     @RequestParam("course_id") Integer courseId,
                                     Model model) {
        LOGGER.info(Messages.TRY_ADD_COURSE_TO_STUDENT, courseId, studentId);
        Course course;
        try {
            Student student = studentService.getStudentByIdWithCourses(studentId);
            course = courseService.getCourseById(courseId);
            student.addCourseToStudent(course);
            studentService.updateStudent(student);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_ADD_COURSE_TO_STUDENT, courseId, studentId, course);
        return "redirect:/student?&id=" + studentId;
    }

    @PostMapping("/student/remove_course")
    public String removeCourseFromStudent(@RequestParam("student_id") Integer studentId,
                                          @RequestParam("course_id") Integer courseId,
                                          Model model) {
        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_STUDENT, courseId, studentId);
        Course course;
        try {
            Student student = studentService.getStudentByIdWithCourses(studentId);
            course = courseService.getCourseById(courseId);
            student.getCourses().remove(course);
            studentService.updateStudent(student);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_STUDENT, courseId, studentId, course);
        return "redirect:/student?&id=" + studentId;
    }

}
