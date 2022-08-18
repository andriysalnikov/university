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
    public String getAllTeachers(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_TEACHERS);
        List<Student> students = studentService.getAllStudents();
        LOGGER.info(Messages.OK_GET_ALL_STUDENTS, students);
        model.addAttribute("students", students);
        return "student/students";
    }

    @GetMapping("/student")
    public String showStudent(@RequestParam("id") Integer id, Model model) {
        Student student;
        List<Course> studentCourses;
        List<Course> otherAvailableCourses;
        try {
            student = studentService.getStudentById(id);
//            userCourses = getUserCoursesByUserId(userType, userId);
//            otherAvailableCourses = getOtherCoursesAvailableForUser(userType, userId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("student", student);
//        model.addAttribute("courses", userCourses);
//        model.addAttribute("othercourses", otherAvailableCourses);
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
            Student student = new Student(firstName, lastName);
            student.setId(id);
            updatedStudent = studentService.updateStudent(student);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_UPDATE_STUDENT, updatedStudent);
        return "redirect:/students";
    }

//    @PostMapping("/user/add_course")
//    public String addCourseToUser(@RequestParam("user_type") String userType,
//                                  @RequestParam("user_id") Integer userId,
//                                  @RequestParam("course_id") Integer courseId,
//                                  Model model) {
//        LOGGER.info(Messages.TRY_SET_USER_TO_COURSE,
//                userType.equals("Teacher") ? Teacher_h.class.getSimpleName() : Student.class.getSimpleName(),
//                userId, courseId);
//        try {
//            addCourseToUser(userType, userId, courseId);
//        } catch (ServiceException exception) {
//            return ExceptionUtil.handleException(exception, LOGGER, model);
//        }
//        LOGGER.info(Messages.OK_SET_USER_TO_COURSE,
//                userType.equals("Teacher") ? Teacher_h.class.getSimpleName() : Student.class.getSimpleName(),
//                userId, courseId);
//        return "redirect:/user?user_type=" + userType + "&id=" + userId;
//    }
//
//    @PostMapping("/user/remove_course")
//    public String removeCourseFromUser(@RequestParam("user_type") String userType,
//                                       @RequestParam("user_id") Integer userId,
//                                       @RequestParam("course_id") Integer courseId,
//                                       Model model) {
//        LOGGER.info(Messages.TRY_REMOVE_USER_FROM_COURSE,
//                userType.equals("Teacher") ? Teacher_h.class.getSimpleName() : Student.class.getSimpleName(),
//                userId, courseId);
//        try {
//            removeCourseFromUser(userType, userId, courseId);
//        } catch (ServiceException exception) {
//            return ExceptionUtil.handleException(exception, LOGGER, model);
//        }
//        LOGGER.info(Messages.OK_REMOVE_USER_FROM_COURSE,
//                userType.equals("Teacher") ? Teacher_h.class.getSimpleName() : Student.class.getSimpleName(),
//                userId, courseId);
//        return "redirect:/user?user_type=" + userType + "&id=" + userId;
//    }
//
//    private List<Course> getUserCoursesByUserId(String userType, Integer userId) {
//        List<Course> courses = new ArrayList<>();
//        if (userType.equals("Teacher")) {
//            courses = teacherService.getTeacherCoursesByTeacherId(userId);
//        }
//        if (userType.equals("Student")) {
//            courses = studentService.getStudentCoursesByStudentId(userId);
//        }
//        return courses;
//    }
//
//    private List<Course> getOtherCoursesAvailableForUser(String userType, Integer userId) {
//        List<Course> courses = new ArrayList<>();
//        if (userType.equals("Teacher")) {
//            courses = courseService.getAllCoursesWithoutTeacher();
//        }
//        if (userType.equals("Student")) {
//            courses = courseService.getAllOtherAvailableCoursesForStudent(userId);
//        }
//        return courses;
//    }
//
//    private void addCourseToUser(String userType, Integer userId, Integer courseId) {
//        if (userType.equals("Teacher")) {
//            courseService.setTeacherToCourse(userId, courseId);
//        }
//        if (userType.equals("Student")) {
//            courseService.setStudentToCourse(userId, courseId);
//        }
//    }
//
//    private void removeCourseFromUser(String userType, Integer userId, Integer courseId) {
//        if (userType.equals("Teacher")) {
//            courseService.removeTeacherFromCourse(courseId);
//        }
//        if (userType.equals("Student")) {
//            courseService.removeStudentFromCourse(userId, courseId);
//        }
//    }

}
