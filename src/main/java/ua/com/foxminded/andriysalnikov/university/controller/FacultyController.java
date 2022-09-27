package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.*;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
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
    public ResponseEntity<List<FacultyDTO>> getAllFaculties() {
        LOGGER.info(Messages.TRY_GET_ALL_FACULTIES);
        List<FacultyDTO> facultyDTOs = facultyService.getAllFacultyDTOs();
        LOGGER.info(Messages.OK_GET_ALL_FACULTIES, facultyDTOs);
        return new ResponseEntity<>(facultyDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        FacultyDTO facultyDTO = facultyService.getFacultyDTOById(id);
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, facultyDTO);
        return new ResponseEntity<>(facultyDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<FacultyDTOWithCourses> getFacultyByIdWithCourses(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        FacultyDTOWithCourses facultyDTOWithCourses =
                facultyService.getFacultyDTOByIdWithCourses(id);
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, facultyDTOWithCourses);
        return new ResponseEntity<>(facultyDTOWithCourses, HttpStatus.OK);
    }

    @GetMapping("/{id}/classrooms")
    public ResponseEntity<FacultyDTOWithClassRooms> getFacultyByIdWithClassRooms(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        FacultyDTOWithClassRooms facultyDTOWithClassRooms
                = facultyService.getFacultyDTOByIdWithClassRooms(id);
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, facultyDTOWithClassRooms);
        return new ResponseEntity<>(facultyDTOWithClassRooms, HttpStatus.OK);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<FacultyDTOWithStudents> getFacultyByIdWithStudents(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        FacultyDTOWithStudents facultyDTOWithStudents =
                facultyService.getFacultyDTOByIdWithStudents(id);
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, facultyDTOWithStudents);
        return new ResponseEntity<>(facultyDTOWithStudents, HttpStatus.OK);
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
        FacultyDTO createdFacultyDTO =
                facultyService.createFacultyDTO(facultyCreateDTO);
        LOGGER.info(Messages.OK_CREATE_FACULTY, createdFacultyDTO);
        return new ResponseEntity<>(createdFacultyDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<FacultyDTO> updateFaculty(@PathVariable Integer id,
                                                    @Valid @RequestBody FacultyCreateDTO facultyCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_FACULTY, facultyCreateDTO);
        facultyService.getFacultyById(id);
        FacultyDTO updatedFacultyDTO = facultyService.updateFacultyDTO(id, facultyCreateDTO);
        LOGGER.info(Messages.OK_UPDATE_FACULTY, updatedFacultyDTO);
        return new ResponseEntity<>(updatedFacultyDTO, HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/add-course/{courseId}")
    public ResponseEntity<FacultyDTOWithCourses> addCourseToFaculty(@PathVariable Integer facultyId,
                                                                    @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_ADD_COURSE_TO_FACULTY, facultyId, courseId);
        Course course = courseService.getCourseById(courseId);
        if (course.getFaculty() != null) {
            throw new ServiceException(Messages.ERROR_ADD_COURSE_TO_FACULTY);
        }
        Faculty faculty = facultyService.getFacultyById(facultyId);
        course.setFaculty(faculty);
        courseService.updateCourse(course);
        FacultyDTOWithCourses facultyDTOWithCourses =
                facultyService.getFacultyDTOByIdWithCourses(facultyId);
        LOGGER.info(Messages.OK_ADD_COURSE_TO_FACULTY, facultyId, courseId, course);
        return new ResponseEntity<>(facultyDTOWithCourses, HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/remove-course/{courseId}")
    public ResponseEntity<FacultyDTOWithCourses> removeCourseFromFaculty(@PathVariable Integer facultyId,
                                                                         @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_FACULTY, courseId, facultyId);
        Course course = courseService.getCourseById(courseId);
        if (course.getFaculty() == null) {
            throw new ServiceException(Messages.ERROR_REMOVE_COURSE_FROM_FACULTY);
        }
        course.setFaculty(null);
        courseService.updateCourse(course);
        FacultyDTOWithCourses facultyDTOWithCourses =
                facultyService.getFacultyDTOByIdWithCourses(facultyId);
        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_FACULTY, courseId, facultyId, course);
        return new ResponseEntity<>(facultyDTOWithCourses, HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/add-classroom/{classRoomId}")
    public ResponseEntity<FacultyDTOWithClassRooms> addClassRoomToFaculty(@PathVariable Integer facultyId,
                                                                          @PathVariable Integer classRoomId) {
        LOGGER.info(Messages.TRY_ADD_CLASSROOM_TO_FACULTY, facultyId, classRoomId);
        ClassRoom classRoom = classRoomService.getClassRoomById(classRoomId);
        if (classRoom.getFaculty() != null) {
            throw new ServiceException(Messages.ERROR_ADD_CLASSROOM_TO_FACULTY);
        }
        Faculty faculty = facultyService.getFacultyById(facultyId);
        classRoom.setFaculty(faculty);
        classRoomService.updateClassRoom(classRoom);
        FacultyDTOWithClassRooms facultyDTOWithClassRooms =
                facultyService.getFacultyDTOByIdWithClassRooms(facultyId);
        LOGGER.info(Messages.OK_ADD_CLASSROOM_TO_FACULTY, facultyId, classRoomId, classRoom);
        return new ResponseEntity<>(facultyDTOWithClassRooms, HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/remove-classroom/{classRoomId}")
    public ResponseEntity<FacultyDTOWithClassRooms> removeClassRoomFromFaculty(@PathVariable Integer facultyId,
                                                                               @PathVariable Integer classRoomId) {
        LOGGER.info(Messages.TRY_REMOVE_CLASSROOM_FROM_FACULTY, classRoomId, facultyId);
        ClassRoom classRoom = classRoomService.getClassRoomById(classRoomId);
        if (classRoom.getFaculty() == null) {
            throw new ServiceException(Messages.ERROR_REMOVE_CLASSROOM_FROM_FACULTY);
        }
        classRoom.setFaculty(null);
        classRoomService.updateClassRoom(classRoom);
        FacultyDTOWithClassRooms facultyDTOWithClassRooms =
                facultyService.getFacultyDTOByIdWithClassRooms(facultyId);
        LOGGER.info(Messages.OK_REMOVE_CLASSROOM_FROM_FACULTY, classRoomId, facultyId, classRoom);
        return new ResponseEntity<>(facultyDTOWithClassRooms, HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/add-student/{studentId}")
    public ResponseEntity<FacultyDTOWithStudents> addStudentToFaculty(@PathVariable Integer facultyId,
                                                                      @PathVariable Integer studentId) {
        LOGGER.info(Messages.TRY_ADD_STUDENT_TO_FACULTY, facultyId, studentId);
        Student student = studentService.getStudentById(studentId);
        if (student.getFaculty() != null) {
            throw new ServiceException(Messages.ERROR_ADD_STUDENT_TO_FACULTY);
        }
        Faculty faculty = facultyService.getFacultyById(facultyId);
        student.setFaculty(faculty);
        studentService.updateStudent(student);
        FacultyDTOWithStudents facultyDTOWithStudents =
                facultyService.getFacultyDTOByIdWithStudents(facultyId);
        LOGGER.info(Messages.OK_ADD_STUDENT_TO_FACULTY, facultyId, studentId, student);
        return new ResponseEntity<>(facultyDTOWithStudents, HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/remove-student/{studentId}")
    public ResponseEntity<FacultyDTOWithStudents> removeStudentFromFaculty(@PathVariable Integer facultyId,
                                                                           @PathVariable Integer studentId) {
        LOGGER.info(Messages.TRY_REMOVE_STUDENT_FROM_FACULTY, studentId, facultyId);
        Student student = studentService.getStudentById(studentId);
        if (student.getFaculty() == null) {
            throw new ServiceException(Messages.ERROR_REMOVE_STUDENT_FROM_FACULTY);
        }
        student.setFaculty(null);
        studentService.updateStudent(student);
        FacultyDTOWithStudents facultyDTOWithStudents =
                facultyService.getFacultyDTOByIdWithStudents(facultyId);
        LOGGER.info(Messages.OK_REMOVE_STUDENT_FROM_FACULTY, studentId, facultyId, student);
        return new ResponseEntity<>(facultyDTOWithStudents, HttpStatus.OK);
    }

}
