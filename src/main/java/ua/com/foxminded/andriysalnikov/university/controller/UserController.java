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
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.User;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public UserController(StudentService studentService, TeacherService teacherService,
                          CourseService courseService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_STUDENTS);
        List<Student> students = studentService.getAllStudents();
        LOGGER.info(Messages.OK_GET_ALL_STUDENTS, students);
        model.addAttribute("title", "Students");
        model.addAttribute("header", "List of All Students");
        model.addAttribute("user_type", "Student");
        model.addAttribute("users", students);
        return "user/users";
    }

    @GetMapping("/teachers")
    public String getAllTeachers(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_TEACHERS);
        List<Teacher> teachers = teacherService.getAllTeachers();
        LOGGER.info(Messages.OK_GET_ALL_TEACHERS, teachers);
        model.addAttribute("title", "Teachers");
        model.addAttribute("header", "List of All Teachers");
        model.addAttribute("user_type", "Teacher");
        model.addAttribute("users", teachers);
        return "user/users";
    }

    @GetMapping("/user")
    public String showUser(@RequestParam("user_type") String userType,
                           @RequestParam("id") Integer userId,
                           Model model) {
        User user;
        List<Course> userCourses;
        List<Course> otherAvailableCourses;
        try {
            user = getUserById(userType, userId);
            userCourses = getUserCoursesByUserId(userType, userId);
            otherAvailableCourses = getOtherCoursesAvailableForUser(userType, userId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("user_type", userType);
        model.addAttribute("user", user);
        model.addAttribute("courses", userCourses);
        model.addAttribute("othercourses", otherAvailableCourses);
        return "user/user";
    }

    @GetMapping("/user/create")
    public String getCreationUserModalWindow(@RequestParam("user_type") String userType, Model model) {
        LOGGER.info(Messages.TRY_CREATE_USER,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName());
        model.addAttribute("user_type", userType);
        return "user/user_create";
    }

    @PostMapping("/user/create")
    public String createUser(@RequestParam("user_type") String userType,
                             @RequestParam("first_name") String firstName,
                             @RequestParam("last_name") String lastName,
                             Model model) {
        User createdUser;
        try {
            createdUser = createUser(userType, firstName, lastName);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_CREATE_USER,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName(),
                createdUser);
        return userType.equals("Teacher") ? "redirect:/teachers" : "redirect:/students";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam("user_type") String userType,
                             @RequestParam("id") Integer userId, Model model) {
        LOGGER.info(Messages.TRY_DELETE_USER_BY_ID,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName(),
                userId);
        User deletedUser;
        try {
            deletedUser = deleteUserById(userType, userId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_DELETE_USER_BY_ID,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName(),
                userId, deletedUser);
        return userType.equals("Teacher") ? "redirect:/teachers" : "redirect:/students";
    }

    @GetMapping("/user/update")
    public String getUpdationUserModalWindow(@RequestParam("user_type") String userType,
                                             @RequestParam("id") Integer userId, Model model) {
        User user;
        try {
            user = getUserById(userType, userId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.TRY_UPDATE_USER,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName(), user);
        model.addAttribute("user_type", userType);
        model.addAttribute("user", user);
        return "user/user_update";
    }

    @PostMapping("/user/update")
    public String updateUser(@RequestParam("user_type") String userType,
                             @RequestParam("id") Integer userId,
                             @RequestParam("first_name") String firstName,
                             @RequestParam("last_name") String lastName,
                             Model model) {
        User updatedUser;
        try {
            updatedUser = updateUser(userType, userId, firstName, lastName);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_UPDATE_USER,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName(),
                updatedUser);
        return userType.equals("Teacher") ? "redirect:/teachers" : "redirect:/students";
    }

    @PostMapping("/user/add_course")
    public String addCourseToUser(@RequestParam("user_type") String userType,
                                  @RequestParam("user_id") Integer userId,
                                  @RequestParam("course_id") Integer courseId,
                                  Model model) {
        LOGGER.info(Messages.TRY_SET_USER_TO_COURSE,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName(),
                userId, courseId);
        try {
            addCourseToUser(userType, userId, courseId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_SET_USER_TO_COURSE,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName(),
                userId, courseId);
        return "redirect:/user?user_type=" + userType + "&id=" + userId;
    }

    @PostMapping("/user/remove_course")
    public String removeCourseFromUser(@RequestParam("user_type") String userType,
                                       @RequestParam("user_id") Integer userId,
                                       @RequestParam("course_id") Integer courseId,
                                       Model model) {
        LOGGER.info(Messages.TRY_REMOVE_USER_FROM_COURSE,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName(),
                userId, courseId);
        try {
            removeCourseFromUser(userType, userId, courseId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_REMOVE_USER_FROM_COURSE,
                userType.equals("Teacher") ? Teacher.class.getSimpleName() : Student.class.getSimpleName(),
                userId, courseId);
        return "redirect:/user?user_type=" + userType + "&id=" + userId;
    }

    private User createUser(String userType, String firstName, String lastName) {
        User user = null;
        if (userType.equals("Teacher")) {
            user = teacherService.createTeacher(new Teacher(0, firstName, lastName));
        }
        if (userType.equals("Student")) {
            user = studentService.createStudent(new Student(0, firstName, lastName));
        }
        return user;
    }

    private User deleteUserById(String userType, Integer userId) {
        User user = null;
        if (userType.equals("Teacher")) {
            user = teacherService.deleteTeacherById(userId);
        }
        if (userType.equals("Student")) {
            user = studentService.deleteStudentById(userId);
        }
        return user;
    }

    private User getUserById(String userType, Integer userId) {
        User user = null;
        if (userType.equals("Teacher")) {
            user = teacherService.getTeacherById(userId);
        }
        if (userType.equals("Student")) {
            user = studentService.getStudentById(userId);
        }
        return user;
    }

    private User updateUser(String userType, Integer userId, String firstName, String lastName) {
        User user = null;
        if (userType.equals("Teacher")) {
            user = teacherService.updateTeacher(new Teacher(userId, firstName, lastName));
        }
        if (userType.equals("Student")) {
            user = studentService.updateStudent(new Student(userId, firstName, lastName));
        }
        return user;
    }

    private List<Course> getUserCoursesByUserId(String userType, Integer userId) {
        List<Course> courses = new ArrayList<>();
        if (userType.equals("Teacher")) {
            courses = teacherService.getTeacherCoursesByTeacherId(userId);
        }
        if (userType.equals("Student")) {
            courses = studentService.getStudentCoursesByStudentId(userId);
        }
        return courses;
    }

    private List<Course> getOtherCoursesAvailableForUser(String userType, Integer userId) {
        List<Course> courses = new ArrayList<>();
        if (userType.equals("Teacher")) {
            courses = courseService.getAllCoursesWithoutTeacher();
        }
        if (userType.equals("Student")) {
            courses = courseService.getAllOtherAvailableCoursesForStudent(userId);
        }
        return courses;
    }

    private void addCourseToUser(String userType, Integer userId, Integer courseId) {
        if (userType.equals("Teacher")) {
            courseService.setTeacherToCourse(userId, courseId);
        }
        if (userType.equals("Student")) {
            courseService.setStudentToCourse(userId, courseId);
        }
    }

    private void removeCourseFromUser(String userType, Integer userId, Integer courseId) {
        if (userType.equals("Teacher")) {
            courseService.removeTeacherFromCourse(courseId);
        }
        if (userType.equals("Student")) {
            courseService.removeStudentFromCourse(userId, courseId);
        }
    }

}
