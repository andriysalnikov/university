package ua.com.foxminded.andriysalnikov.university.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.marker.View;
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithCourses;
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithDependencies;
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithoutDependencies;
import ua.com.foxminded.andriysalnikov.university.model.*;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.utils.ExceptionUtil;
import ua.com.foxminded.andriysalnikov.university.service.FacultyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyController.class);

    private final FacultyService facultyService;
    private final ClassRoomService classRoomService;
    private final CourseService courseService;
    private final StudentService studentService;

    @Autowired
    public FacultyController(FacultyService facultyService, ClassRoomService classRoomService,
                             CourseService courseService, StudentService studentService) {
        this.facultyService = facultyService;
        this.classRoomService = classRoomService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping
    @JsonView(ViewWithoutDependencies.class)
    public List<Faculty> getAllFaculties() {
        LOGGER.info(Messages.TRY_GET_ALL_FACULTIES);
        List<Faculty> faculties = facultyService.getAllFaculties();
        LOGGER.info(Messages.OK_GET_ALL_FACULTIES, faculties);
        return faculties;
    }

    @GetMapping("/{id}")
    @JsonView(ViewWithoutDependencies.class)
    public Faculty getFacultyById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @GetMapping("/{id}/courses")
    @JsonView(ViewWithCourses.class)
    public Faculty getFacultyByIdWithCourses(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyByIdWithCourses(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }


//    @PostMapping("/{id}/delete")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteTeacher(@PathVariable Integer id) {
//        LOGGER.info(Messages.TRY_DELETE_TEACHER_BY_ID,id);
//        try {
//            teacherService.deleteTeacherById(id);
//        } catch (ServiceException exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
//        }
//        LOGGER.info(Messages.OK_DELETE_TEACHER_BY_ID, id);
//    }
//
//    @PostMapping("/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    @JsonView(View.WithoutCourses.class)
//    public Teacher createTeacher(@Valid @RequestBody Teacher teacher) {
//        LOGGER.info(Messages.TRY_CREATE_TEACHER);
//        Teacher createdTeacher;
//        try {
//            createdTeacher = teacherService.createTeacher(teacher);
//        } catch (ServiceException exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
//        }
//        LOGGER.info(Messages.OK_CREATE_TEACHER, createdTeacher);
//        return createdTeacher;
//    }
//
//    @PostMapping("/{id}/update")
//    @ResponseStatus(HttpStatus.OK)
//    @JsonView(View.WithoutCourses.class)
//    public Teacher updateTeacher(@PathVariable Integer id, @Valid @RequestBody Teacher teacher) {
//        LOGGER.info(Messages.TRY_UPDATE_TEACHER, teacher);
//        Teacher updatedTeacher;
//        try {
//            teacherService.getTeacherById(id);
//            teacher.setId(id);
//            updatedTeacher = teacherService.updateTeacher(teacher);
//        } catch (ServiceException exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
//        }
//        LOGGER.info(Messages.OK_UPDATE_TEACHER, updatedTeacher);
//        return updatedTeacher;
//    }
//
//    @PostMapping("/{teacherId}/add-course/{courseId}")
//    @ResponseStatus(HttpStatus.OK)
//    @JsonView(View.WithCourses.class)
//    public Teacher addCourseToTeacher(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
//        LOGGER.info(Messages.TRY_ADD_COURSE_TO_TEACHER, teacherId, courseId);
//        Course course;
//        Teacher teacher;
//        try {
//            course = courseService.getCourseById(courseId);
//            System.out.println(course.getTeacher());
//            if (course.getTeacher() != null) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                        Messages.ERROR_ADD_COURSE_TO_TEACHER);
//            }
//            teacher = teacherService.getTeacherById(teacherId);
//            course.setTeacher(teacher);
//            courseService.updateCourse(course);
//            teacher = teacherService.getTeacherByIdWithCourses(teacherId);
//        } catch (ServiceException exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
//        }
//        LOGGER.info(Messages.OK_ADD_COURSE_TO_TEACHER, teacherId, courseId, course);
//        return teacher;
//    }
//
//    @PostMapping("/{teacherId}/remove-course/{courseId}")
//    @ResponseStatus(HttpStatus.OK)
//    @JsonView(View.WithCourses.class)
//    public Teacher removeCourseFromTeacher(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
//        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId);
//        Course course;
//        Teacher teacher;
//        try {
//            course = courseService.getCourseById(courseId);
//            System.out.println(course.getTeacher());
//            if (course.getTeacher() == null) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                        Messages.ERROR_REMOVE_COURSE_FROM_TEACHER);
//            }
//            course.setTeacher(null);
//            courseService.updateCourse(course);
//            teacher = teacherService.getTeacherByIdWithCourses(teacherId);
//        } catch (ServiceException exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
//        }
//        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId, course);
//        return teacher;
//    }

//    @PostMapping("/faculty/add_classroom")
//    public String addClassRoomToFaculty(@RequestParam("faculty_id") Integer facultyId,
//                                        @RequestParam("classroom_id") Integer classRoomId,
//                                        Model model) {
//        LOGGER.info(Messages.TRY_ADD_CLASSROOM_TO_FACULTY, classRoomId, facultyId);
//        ClassRoom classRoom;
//        try {
//            Faculty faculty = facultyService.getFacultyByIdWithClassRooms(facultyId);
//            classRoom = classRoomService.getClassRoomById(classRoomId);
//            classRoom.setFaculty(faculty);
//            classRoomService.updateClassRoom(classRoom);
//        } catch (ServiceException exception) {
//            return ExceptionUtil.handleException(exception, LOGGER, model);
//        }
//        LOGGER.info(Messages.OK_ADD_CLASSROOM_TO_FACULTY, classRoomId, facultyId, classRoom);
//        return "redirect:/faculty_classrooms?&id=" + facultyId;
//    }
//
//    @PostMapping("/faculty/remove_classroom")
//    public String removeClassRoomFromFaculty(@RequestParam("faculty_id") Integer facultyId,
//                                             @RequestParam("classroom_id") Integer classRoomId,
//                                             Model model) {
//        LOGGER.info(Messages.TRY_REMOVE_CLASSROOM_FROM_FACULTY, classRoomId, facultyId);
//        ClassRoom classRoom;
//        try {
//            classRoom = classRoomService.getClassRoomById(classRoomId);
//            classRoom.setFaculty(null);
//            classRoomService.updateClassRoom(classRoom);
//        } catch (ServiceException exception) {
//            return ExceptionUtil.handleException(exception, LOGGER, model);
//        }
//        LOGGER.info(Messages.OK_REMOVE_CLASSROOM_FROM_FACULTY, classRoomId, facultyId, classRoom);
//        return "redirect:/faculty_classrooms?&id=" + facultyId;
//    }

//    @PostMapping("/faculty/add_course")
//    public String addCourseToFaculty(@RequestParam("faculty_id") Integer facultyId,
//                                     @RequestParam("course_id") Integer courseId,
//                                     Model model) {
//        LOGGER.info(Messages.TRY_ADD_COURSE_TO_FACULTY, facultyId, courseId);
//        Course course;
//        try {
//            Faculty faculty = facultyService.getFacultyByIdWithCourses(facultyId);
//            course = courseService.getCourseById(courseId);
//            course.setFaculty(faculty);
//            courseService.updateCourse(course);
//        } catch (ServiceException exception) {
//            return ExceptionUtil.handleException(exception, LOGGER, model);
//        }
//        LOGGER.info(Messages.OK_ADD_COURSE_TO_FACULTY, courseId, facultyId, course);
//        return "redirect:/faculty_courses?&id=" + facultyId;
//    }
//
//    @PostMapping("/faculty/remove_course")
//    public String removeCourseFromFaculty(@RequestParam("faculty_id") Integer facultyId,
//                                          @RequestParam("course_id") Integer courseId,
//                                          Model model) {
//        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_FACULTY, courseId, facultyId);
//        Course course;
//        try {
//            course = courseService.getCourseById(courseId);
//            course.setFaculty(null);
//            courseService.updateCourse(course);
//        } catch (ServiceException exception) {
//            return ExceptionUtil.handleException(exception, LOGGER, model);
//        }
//        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_FACULTY, courseId, facultyId, course);
//        return "redirect:/faculty_courses?&id=" + facultyId;
//    }


//    @PostMapping("/faculty/add_student")
//    public String addStudentToFaculty(@RequestParam("faculty_id") Integer facultyId,
//                                      @RequestParam("student_id") Integer studentId,
//                                      Model model) {
//        LOGGER.info(Messages.TRY_ADD_STUDENT_TO_FACULTY, studentId, facultyId);
//        Student student;
//        try {
//            Faculty faculty = facultyService.getFacultyByIdWithStudents(facultyId);
//            student = studentService.getStudentById(studentId);
//            student.setFaculty(faculty);
//            studentService.updateStudent(student);
//        } catch (ServiceException exception) {
//            return ExceptionUtil.handleException(exception, LOGGER, model);
//        }
//        LOGGER.info(Messages.OK_ADD_STUDENT_TO_FACULTY, studentId, facultyId, student);
//        return "redirect:/faculty_students?&id=" + facultyId;
//    }
//
//    @PostMapping("/faculty/remove_student")
//    public String removeStudentFromFaculty(@RequestParam("faculty_id") Integer facultyId,
//                                           @RequestParam("student_id") Integer studentId,
//                                           Model model) {
//        LOGGER.info(Messages.TRY_REMOVE_STUDENT_FROM_FACULTY, studentId, facultyId);
//        Student student;
//        try {
//            student = studentService.getStudentById(studentId);
//            student.setFaculty(null);
//            studentService.updateStudent(student);
//        } catch (ServiceException exception) {
//            return ExceptionUtil.handleException(exception, LOGGER, model);
//        }
//        LOGGER.info(Messages.OK_REMOVE_STUDENT_FROM_FACULTY, studentId, facultyId, student);
//        return "redirect:/faculty_students?&id=" + facultyId;
//    }

}
