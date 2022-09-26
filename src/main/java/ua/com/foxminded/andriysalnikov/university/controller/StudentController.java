package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.StudentCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.StudentDTO;
import ua.com.foxminded.andriysalnikov.university.dto.StudentWithCoursesDTO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.mapper.StudentMapper;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final CourseService courseService;

    @Autowired
    public StudentController(StudentService studentService, StudentMapper studentMapper,
                             CourseService courseService) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        LOGGER.info(Messages.TRY_GET_ALL_STUDENTS);
        List<Student> students = studentService.getAllStudents();
        LOGGER.info(Messages.OK_GET_ALL_STUDENTS, students);
        return new ResponseEntity<>(students.stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/without-faculty")
    public ResponseEntity<List<StudentDTO>> getAllStudentsWithoutFaculty() {
        LOGGER.info(Messages.TRY_GET_ALL_STUDENTS_WITHOUT_FACULTY);
        List<Student> students = studentService.getAllStudentsWithoutFaculty();
        LOGGER.info(Messages.OK_GET_ALL_STUDENTS_WITHOUT_FACULTY, students);
        return new ResponseEntity<>(students.stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_STUDENT_BY_ID, id);
        Student student;
        try {
            student = studentService.getStudentById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_STUDENT_BY_ID, id, student);
        return new ResponseEntity<>(studentMapper.toDTO(student), HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<StudentWithCoursesDTO> getStudentByIdWithCourses(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_STUDENT_BY_ID, id);
        Student student;
        try {
            student = studentService.getStudentByIdWithCourses(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_STUDENT_BY_ID, id, student);
        return new ResponseEntity<>(studentMapper.toDTOWithCourses(student), HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
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
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentCreateDTO studentCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_STUDENT);
        Student createdStudent;
        try {
            createdStudent = studentService.createStudent(studentMapper.fromDTO(studentCreateDTO));
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_STUDENT, createdStudent);
        return new ResponseEntity<>(studentMapper.toDTO(createdStudent), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Integer id,
                                                    @Valid @RequestBody StudentCreateDTO studentCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_STUDENT, studentCreateDTO);
        Student updatedStudent;
        try {
            studentService.getStudentById(id);
            Student student = studentMapper.fromDTO(studentCreateDTO);
            student.setId(id);
            updatedStudent = studentService.updateStudent(student);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_STUDENT, updatedStudent);
        return new ResponseEntity<>(studentMapper.toDTO(updatedStudent), HttpStatus.OK);
    }

    @PostMapping("/{studentId}/add-course/{courseId}")
    public ResponseEntity<StudentWithCoursesDTO> addCourseToStudent(@PathVariable Integer studentId,
                                                                    @PathVariable Integer courseId) {
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
        return new ResponseEntity<>(studentMapper.toDTOWithCourses(student), HttpStatus.OK);
    }

    @PostMapping("/{studentId}/remove-course/{courseId}")
    public ResponseEntity<StudentWithCoursesDTO> removeCourseFromStudent(@PathVariable Integer studentId,
                                                                         @PathVariable Integer courseId) {
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
        return new ResponseEntity<>(studentMapper.toDTOWithCourses(student), HttpStatus.OK);
    }

}