package ua.com.foxminded.andriysalnikov.university.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithClassRooms;
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithCourses;
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithStudents;
import ua.com.foxminded.andriysalnikov.university.marker.ViewWithoutDependencies;
import ua.com.foxminded.andriysalnikov.university.model.*;
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

    @GetMapping("/{id}/classrooms")
    @JsonView(ViewWithClassRooms.class)
    public Faculty getFacultyByIdWithClassRooms(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyByIdWithClassRooms(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @GetMapping("/{id}/students")
    @JsonView(ViewWithStudents.class)
    public Faculty getFacultyByIdWithStudents(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_FACULTY_BY_ID, id);
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyByIdWithStudents(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_FACULTY_BY_ID, id, faculty);
        return faculty;
    }

    @PostMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFaculty(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_FACULTY_BY_ID,id);
        try {
            facultyService.deleteFacultyById(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_DELETE_FACULTY_BY_ID, id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(ViewWithoutDependencies.class)
    public Faculty createFaculty(@Valid @RequestBody Faculty faculty) {
        LOGGER.info(Messages.TRY_CREATE_FACULTY);
        Faculty createdFaculty;
        try {
            createdFaculty = facultyService.createFaculty(faculty);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_CREATE_FACULTY, createdFaculty);
        return createdFaculty;
    }

    @PostMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithoutDependencies.class)
    public Faculty updateFaculty(@PathVariable Integer id, @Valid @RequestBody Faculty faculty) {
        LOGGER.info(Messages.TRY_UPDATE_FACULTY, faculty);
        Faculty updatedfaculty;
        try {
            facultyService.getFacultyById(id);
            faculty.setId(id);
            updatedfaculty = facultyService.updateFaculty(faculty);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_UPDATE_FACULTY, updatedfaculty);
        return updatedfaculty;
    }

    @PostMapping("/{facultyId}/add-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithCourses.class)
    public Faculty addCourseToFaculty(@PathVariable Integer facultyId, @PathVariable Integer courseId) {
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
        return faculty;
    }

    @PostMapping("/{facultyId}/remove-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithCourses.class)
    public Faculty removeCourseFromFaculty(@PathVariable Integer facultyId, @PathVariable Integer courseId) {
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
        return faculty;
    }

    @PostMapping("/{facultyId}/add-classroom/{classRoomId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithClassRooms.class)
    public Faculty addClassRoomToFaculty(@PathVariable Integer facultyId, @PathVariable Integer classRoomId) {
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
        return faculty;
    }

    @PostMapping("/{facultyId}/remove-classroom/{classRoomId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithClassRooms.class)
    public Faculty removeClassRoomFromFaculty(@PathVariable Integer facultyId, @PathVariable Integer classRoomId) {
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
        return faculty;
    }

    @PostMapping("/{facultyId}/add-student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithStudents.class)
    public Faculty addStudentToFaculty(@PathVariable Integer facultyId, @PathVariable Integer studentId) {
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
        return faculty;
    }

    @PostMapping("/{facultyId}/remove-student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(ViewWithStudents.class)
    public Faculty removeStudentFromFaculty(@PathVariable Integer facultyId, @PathVariable Integer studentId) {
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
        return faculty;
    }
}
