package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.*;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.mapper.FacultyMapper;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.FacultyService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyController.class);

    private final FacultyService facultyService;
    private final ClassRoomService classRoomService;
    private final CourseService courseService;
    private final StudentService studentService;
    private final FacultyMapper facultyMapper;

    @Autowired
    public FacultyController(FacultyService facultyService, ClassRoomService classRoomService,
                             CourseService courseService, StudentService studentService,
                             FacultyMapper facultyMapper) {
        this.facultyService = facultyService;
        this.classRoomService = classRoomService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.facultyMapper = facultyMapper;
    }

    @GetMapping
    public ResponseEntity<List<FacultyDTO>> getAllFaculties() {
        LOGGER.info(Messages.TRY_GET_ALL_FACULTIES);
        List<Faculty> faculties = facultyService.getAllFaculties();
        LOGGER.info(Messages.OK_GET_ALL_FACULTIES, faculties);
        return new ResponseEntity<>(faculties.stream()
                .map(facultyMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return new ResponseEntity<>(facultyMapper.toDTO(faculty), HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<FacultyWithCoursesDTO> getFacultyByIdWithCourses(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyByIdWithCourses(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return new ResponseEntity<>(facultyMapper.toDTOWithCourses(faculty), HttpStatus.OK);
    }

    @GetMapping("/{id}/classrooms")
    public ResponseEntity<FacultyWithClassRoomsDTO> getFacultyByIdWithClassRooms(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyByIdWithClassRooms(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return new ResponseEntity<>(facultyMapper.toDTOWithClassRooms(faculty), HttpStatus.OK);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<FacultyWithStudentsDTO> getFacultyByIdWithStudents(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyByIdWithStudents(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return new ResponseEntity<>(facultyMapper.toDTOWithStudents(faculty), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteFaculty(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_FACULTY_BY_ID,id);
        facultyService.deleteFacultyById(id);
        LOGGER.info(Messages.OK_DELETE_FACULTY_BY_ID, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<FacultyDTO> createFaculty(@Valid @RequestBody FacultyCreateDTO facultyCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_FACULTY);
        Faculty createdFaculty;
        try {
            createdFaculty = facultyService.createFaculty(facultyMapper.fromDTO(facultyCreateDTO));
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_FACULTY, createdFaculty);
        return new ResponseEntity<>(facultyMapper.toDTO(createdFaculty), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<FacultyDTO> updateFaculty(@PathVariable Integer id,
                                                    @Valid @RequestBody FacultyCreateDTO facultyCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_FACULTY, facultyCreateDTO);
        Faculty updatedfaculty;
        try {
            facultyService.getFacultyById(id);
            Faculty faculty = facultyMapper.fromDTO(facultyCreateDTO);
            faculty.setId(id);
            updatedfaculty = facultyService.updateFaculty(faculty);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_FACULTY, updatedfaculty);
        return new ResponseEntity<>(facultyMapper.toDTO(updatedfaculty), HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/add-course/{courseId}")
    public ResponseEntity<FacultyWithCoursesDTO> addCourseToFaculty(@PathVariable Integer facultyId,
                                                                    @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_ADD_COURSE_TO_FACULTY, facultyId, courseId);
        Course course;
        Faculty faculty;
        try {
            course = courseService.getCourseById(courseId);
            if (course.getFaculty() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        Messages.ERROR_ADD_COURSE_TO_FACULTY);
            }
            faculty = facultyService.getFacultyById(facultyId);
            course.setFaculty(faculty);
            courseService.updateCourse(course);
            faculty = facultyService.getFacultyByIdWithCourses(facultyId);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_ADD_COURSE_TO_FACULTY, facultyId, courseId, course);
        return new ResponseEntity<>(facultyMapper.toDTOWithCourses(faculty), HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/remove-course/{courseId}")
    public ResponseEntity<FacultyWithCoursesDTO> removeCourseFromFaculty(@PathVariable Integer facultyId,
                                                                         @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_FACULTY, courseId, facultyId);
        Course course;
        Faculty faculty;
        try {
            course = courseService.getCourseById(courseId);
            if (course.getFaculty() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        Messages.ERROR_REMOVE_COURSE_FROM_FACULTY);
            }
            course.setFaculty(null);
            courseService.updateCourse(course);
            faculty = facultyService.getFacultyByIdWithCourses(facultyId);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_FACULTY, courseId, facultyId, course);
        return new ResponseEntity<>(facultyMapper.toDTOWithCourses(faculty), HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/add-classroom/{classRoomId}")
    public ResponseEntity<FacultyWithClassRoomsDTO> addClassRoomToFaculty(@PathVariable Integer facultyId,
                                                                          @PathVariable Integer classRoomId) {
        LOGGER.info(Messages.TRY_ADD_CLASSROOM_TO_FACULTY, facultyId, classRoomId);
        ClassRoom classRoom;
        Faculty faculty;
        try {
            classRoom = classRoomService.getClassRoomById(classRoomId);
            if (classRoom.getFaculty() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        Messages.ERROR_ADD_CLASSROOM_TO_FACULTY);
            }
            faculty = facultyService.getFacultyById(facultyId);
            classRoom.setFaculty(faculty);
            classRoomService.updateClassRoom(classRoom);
            faculty = facultyService.getFacultyByIdWithCourses(facultyId);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_ADD_CLASSROOM_TO_FACULTY, facultyId, classRoomId, classRoom);
        return new ResponseEntity<>(facultyMapper.toDTOWithClassRooms(faculty), HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/remove-classroom/{classRoomId}")
    public ResponseEntity<FacultyWithClassRoomsDTO> removeClassRoomFromFaculty(@PathVariable Integer facultyId,
                                                                               @PathVariable Integer classRoomId) {
        LOGGER.info(Messages.TRY_REMOVE_CLASSROOM_FROM_FACULTY, classRoomId, facultyId);
        ClassRoom classRoom;
        Faculty faculty;
        try {
            classRoom = classRoomService.getClassRoomById(classRoomId);
            if (classRoom.getFaculty() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        Messages.ERROR_REMOVE_CLASSROOM_FROM_FACULTY);
            }
            classRoom.setFaculty(null);
            classRoomService.updateClassRoom(classRoom);
            faculty = facultyService.getFacultyByIdWithCourses(facultyId);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_REMOVE_CLASSROOM_FROM_FACULTY, classRoomId, facultyId, classRoom);
        return new ResponseEntity<>(facultyMapper.toDTOWithClassRooms(faculty), HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/add-student/{studentId}")
    public ResponseEntity<FacultyWithStudentsDTO> addStudentToFaculty(@PathVariable Integer facultyId,
                                                                      @PathVariable Integer studentId) {
        LOGGER.info(Messages.TRY_ADD_STUDENT_TO_FACULTY, facultyId, studentId);
        Student student;
        Faculty faculty;
        try {
            student = studentService.getStudentById(studentId);
            if (student.getFaculty() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        Messages.ERROR_ADD_STUDENT_TO_FACULTY);
            }
            faculty = facultyService.getFacultyById(facultyId);
            student.setFaculty(faculty);
            studentService.updateStudent(student);
            faculty = facultyService.getFacultyByIdWithCourses(facultyId);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_ADD_STUDENT_TO_FACULTY, facultyId, studentId, student);
        return new ResponseEntity<>(facultyMapper.toDTOWithStudents(faculty), HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/remove-student/{studentId}")
    public ResponseEntity<FacultyWithStudentsDTO> removeStudentFromFaculty(@PathVariable Integer facultyId,
                                                                           @PathVariable Integer studentId) {
        LOGGER.info(Messages.TRY_REMOVE_STUDENT_FROM_FACULTY, studentId, facultyId);
        Student student;
        Faculty faculty;
        try {
            student = studentService.getStudentById(studentId);
            if (student.getFaculty() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        Messages.ERROR_REMOVE_STUDENT_FROM_FACULTY);
            }
            student.setFaculty(null);
            studentService.updateStudent(student);
            faculty = facultyService.getFacultyByIdWithCourses(facultyId);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_REMOVE_STUDENT_FROM_FACULTY, studentId, facultyId, student);
        return new ResponseEntity<>(facultyMapper.toDTOWithStudents(faculty), HttpStatus.OK);
    }

}
