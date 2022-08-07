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
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.FacultyService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;

import java.util.List;

@Controller
public class FacultyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyController.class);

    private final FacultyService facultyService;
    private final CourseService courseService;
    private final ClassRoomService classRoomService;
    private final StudentService studentService;

    @Autowired
    public FacultyController(FacultyService facultyService, CourseService courseService,
                             ClassRoomService classRoomService, StudentService studentService) {
        this.facultyService = facultyService;
        this.courseService = courseService;
        this.classRoomService = classRoomService;
        this.studentService = studentService;
    }

    @GetMapping("/faculties")
    public String getAllFaculties(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_FACULTIES);
        List<Faculty> faculties = facultyService.getAllFaculties();
        LOGGER.info(Messages.OK_GET_ALL_FACULTIES, faculties);
        model.addAttribute("faculties", faculties);
        return "faculty/faculties";
    }

    @GetMapping("/faculty/create")
    public String getCreationFacultyModalWindow() {
        LOGGER.info(Messages.TRY_CREATE_FACULTY);
        return "faculty/faculty_create";
    }

    @PostMapping("/faculty/create")
    public String createFaculty(@RequestParam("name") String fullName, Model model) {
        Faculty createdFaculty;
        try {
            createdFaculty = facultyService.createFaculty(new Faculty(0, fullName));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_CREATE_FACULTY, createdFaculty);
        return "redirect:/faculties";
    }

    @PostMapping("/faculty/delete")
    public String deleteFaculty(@RequestParam("id") Integer facultyId, Model model) {
        LOGGER.info(Messages.TRY_DELETE_FACULTY_BY_ID, facultyId);
        Faculty deletedFaculty;
        try {
            deletedFaculty = facultyService.deleteFacultyById(facultyId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_DELETE_FACULTY_BY_ID, facultyId, deletedFaculty);
        return "redirect:/faculties";
    }

    @GetMapping("/faculty/update")
    public String getUpdationFacultyModalWindow(@RequestParam("id") Integer facultyId, Model model) {
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyById(facultyId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.TRY_UPDATE_FACULTY, faculty);
        model.addAttribute("faculty", faculty);
        return "faculty/faculty_update";
    }

    @PostMapping("/faculty/update")
    public String updateFaculty(@RequestParam("id") Integer facultyId,
                                @RequestParam("name") String fullName,
                                Model model) {
        Faculty updatedFaculty;
        try {
            updatedFaculty = facultyService.updateFaculty(new Faculty(facultyId, fullName));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_UPDATE_FACULTY, updatedFaculty);
        return "redirect:/faculties";
    }

    @GetMapping("/faculty_courses")
    public String showFacultyCourses(@RequestParam("id") Integer facultyId, Model model) {
        Faculty faculty;
        List<Course> facultyCourses;
        List<Course> otherAvailableCourses;
        try {
            faculty = facultyService.getFacultyById(facultyId);
            facultyCourses = facultyService.getFacultyCoursesByFacultyId(facultyId);
            otherAvailableCourses = courseService.getAllCoursesWithoutFaculty();
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("faculty", faculty);
        model.addAttribute("courses", facultyCourses);
        model.addAttribute("othercourses", otherAvailableCourses);
        return "faculty/faculty_courses";
    }

    @PostMapping("/faculty/add_course")
    public String addCourseToFaculty(@RequestParam("faculty_id") Integer facultyId,
                                     @RequestParam("course_id") Integer courseId,
                                     Model model) {
        LOGGER.info(Messages.TRY_SET_FACULTY_TO_COURSE, facultyId, courseId);
        Course updatedCourse;
        try {
            updatedCourse = courseService.setFacultyToCourse(facultyId, courseId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_SET_FACULTY_TO_COURSE, facultyId, courseId, updatedCourse);
        return "redirect:/faculty_courses?&id=" + facultyId;
    }

    @PostMapping("/faculty/remove_course")
    public String removeCourseFromFaculty(@RequestParam("faculty_id") Integer facultyId,
                                          @RequestParam("course_id") Integer courseId,
                                          Model model) {
        LOGGER.info(Messages.TRY_REMOVE_FACULTY_FROM_COURSE, courseId);
        Course updatedCourse;
        try {
            updatedCourse = courseService.removeFacultyFromCourse(courseId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_REMOVE_FACULTY_FROM_COURSE, courseId, updatedCourse);
        return "redirect:/faculty_courses?&id=" + facultyId;
    }

    @GetMapping("/faculty_classrooms")
    public String showFacultyClassRooms(@RequestParam("id") Integer facultyId, Model model) {
        Faculty faculty;
        List<ClassRoom> facultyClassRooms;
        List<ClassRoom> otherAvailableClassRooms;
        try {
            faculty = facultyService.getFacultyById(facultyId);
            facultyClassRooms = facultyService.getFacultyClassRoomsByFacultyId(facultyId);
            otherAvailableClassRooms = classRoomService.getAllClassRoomsWithoutFaculty();
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("faculty", faculty);
        model.addAttribute("classrooms", facultyClassRooms);
        model.addAttribute("otherclassrooms", otherAvailableClassRooms);
        return "faculty/faculty_classrooms";
    }

    @PostMapping("/faculty/add_classroom")
    public String addClassRoomToFaculty(@RequestParam("faculty_id") Integer facultyId,
                                        @RequestParam("classroom_id") Integer classRoomId,
                                        Model model) {
        LOGGER.info(Messages.TRY_SET_FACULTY_TO_CLASSROOM, facultyId, classRoomId);
        ClassRoom updatedClassRoom;
        try {
            updatedClassRoom = classRoomService.setFacultyToClassRoom(facultyId, classRoomId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_SET_FACULTY_TO_CLASSROOM, facultyId, classRoomId, updatedClassRoom);
        return "redirect:/faculty_classrooms?&id=" + facultyId;
    }

    @PostMapping("/faculty/remove_classroom")
    public String removeClassRoomFromFaculty(@RequestParam("faculty_id") Integer facultyId,
                                             @RequestParam("classroom_id") Integer classRoomId,
                                             Model model) {
        LOGGER.info(Messages.TRY_REMOVE_FACULTY_FROM_CLASSROOM, classRoomId);
        ClassRoom updatedClassRoom;
        try {
            updatedClassRoom = classRoomService.removeFacultyFromClassRoom(classRoomId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_REMOVE_FACULTY_FROM_CLASSROOM, classRoomId, updatedClassRoom);
        return "redirect:/faculty_classrooms?&id=" + facultyId;
    }

    @GetMapping("/faculty_students")
    public String showFacultyStudents(@RequestParam("id") Integer facultyId, Model model) {
        Faculty faculty;
        List<Student> facultyStudents;
        List<Student> otherAvailableStudents;
        try {
            faculty = facultyService.getFacultyById(facultyId);
            facultyStudents = facultyService.getFacultyStudentsByFacultyId(facultyId);
            otherAvailableStudents = studentService.getAllStudentsWithoutFaculty();
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("faculty", faculty);
        model.addAttribute("students", facultyStudents);
        model.addAttribute("otherstudents", otherAvailableStudents);
        return "faculty/faculty_students";
    }

    @PostMapping("/faculty/add_student")
    public String addStudentToFaculty(@RequestParam("faculty_id") Integer facultyId,
                                      @RequestParam("student_id") Integer studentId,
                                      Model model) {
        LOGGER.info(Messages.TRY_SET_FACULTY_TO_STUDENT, facultyId, studentId);
        Student updatedStudent;
        try {
            updatedStudent = studentService.setFacultyToStudent(facultyId, studentId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_SET_FACULTY_TO_STUDENT, facultyId, studentId, updatedStudent);
        return "redirect:/faculty_students?&id=" + facultyId;
    }

    @PostMapping("/faculty/remove_student")
    public String removeStudentFromFaculty(@RequestParam("faculty_id") Integer facultyId,
                                           @RequestParam("student_id") Integer studentId,
                                           Model model) {
        LOGGER.info(Messages.TRY_REMOVE_FACULTY_FROM_STUDENT, studentId);
        Student updatedStudent;
        try {
            updatedStudent = studentService.removeFacultyFromStudent(studentId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_REMOVE_FACULTY_FROM_STUDENT, studentId, updatedStudent);
        return "redirect:/faculty_students?&id=" + facultyId;
    }

}
