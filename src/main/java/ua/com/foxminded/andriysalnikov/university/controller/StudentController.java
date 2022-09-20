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
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithCourses;
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithoutDependencies;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    @JsonView(ViewWithoutDependencies.class)
    public List<Student> getAllStudents() {
        LOGGER.info(Messages.TRY_GET_ALL_STUDENTS);
        List<Student> students = studentService.getAllStudents();
        LOGGER.info(Messages.OK_GET_ALL_STUDENTS, students);
        return students;
    }

    @GetMapping("/without-faculty")
    @JsonView(ViewWithoutDependencies.class)
    public List<Student> getAllStudentsWithoutFaculty() {
        LOGGER.info(Messages.TRY_GET_ALL_STUDENTS_WITHOUT_FACULTY);
        List<Student> students = studentService.getAllStudentsWithoutFaculty();
        LOGGER.info(Messages.OK_GET_ALL_STUDENTS_WITHOUT_FACULTY, students);
        return students;
    }

    @GetMapping("/{id}")
    @JsonView(ViewWithoutDependencies.class)
    public Student getStudentById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_STUDENT_BY_ID, id);
        Student student;
        try {
            student = studentService.getStudentById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_STUDENT_BY_ID, id, student);
        return student;
    }

    @GetMapping("/{id}/courses")
    @JsonView(ViewWithCourses.class)
    public Student getStudentByIdWithCourses(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_STUDENT_BY_ID, id);
        Student student;
        try {
            student = studentService.getStudentByIdWithCourses(id);
        } catch (ServiceException exception) {
            System.out.println(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_STUDENT_BY_ID, id, student);
        return student;
    }

    @PostMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_STUDENT_BY_ID,id);
        try {
            studentService.deleteStudentById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_DELETE_STUDENT_BY_ID, id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ViewWithoutDependencies.class)
    public Student createStudent(@Valid @RequestBody Student student) {
        LOGGER.info(Messages.TRY_CREATE_STUDENT);
        Student createdStudent;
        try {
            createdStudent = studentService.createStudent(student);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_STUDENT, createdStudent);
        return createdStudent;
    }

    @PostMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithoutDependencies.class)
    public Student updateStudent(@PathVariable Integer id, @Valid @RequestBody Student student) {
        LOGGER.info(Messages.TRY_UPDATE_STUDENT, student);
        Student updatedStudent;
        try {
            studentService.getStudentById(id);
            student.setId(id);
            updatedStudent = studentService.updateStudent(student);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_STUDENT, updatedStudent);
        return updatedStudent;
    }

    @PostMapping("/{studentId}/add-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithCourses.class)
    public Student addCourseToStudent(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_ADD_COURSE_TO_STUDENT, studentId, courseId);
        Course course;
        Student student;
        try {
            course = courseService.getCourseById(courseId);
            student = studentService.getStudentByIdWithCourses(studentId);
            student.getCourses().add(course);
            studentService.updateStudent(student);
            student = studentService.getStudentByIdWithCourses(studentId);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_ADD_COURSE_TO_STUDENT, studentId, courseId, course);
        return student;
    }

    @PostMapping("/{studentId}/remove-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithCourses.class)
    public Student removeCourseFromStudent(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_STUDENT, courseId, studentId);
        Course course;
        Student student;
        try {
            student = studentService.getStudentByIdWithCourses(studentId);
            course = courseService.getCourseById(courseId);
            student.getCourses().remove(course);
            studentService.updateStudent(student);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_STUDENT, courseId, studentId, course);
        return student;
    }

}