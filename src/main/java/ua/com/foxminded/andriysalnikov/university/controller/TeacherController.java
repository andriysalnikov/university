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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherDTOWithCourses;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public TeacherController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        LOGGER.info(Messages.TRY_GET_ALL_TEACHERS);
        List<TeacherDTO> teacherDTOs = teacherService.getAllTeacherDTOs();
        LOGGER.info(Messages.OK_GET_ALL_TEACHERS, teacherDTOs);
        return new ResponseEntity<>(teacherDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_TEACHER_BY_ID, id);
        TeacherDTO teacherDTO = teacherService.getTeacherDTOById(id);
        LOGGER.info(Messages.OK_GET_TEACHER_BY_ID, id, teacherDTO);
        return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<TeacherDTOWithCourses> getTeacherByIdWithCourses(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_TEACHER_BY_ID, id);
        TeacherDTOWithCourses teacherDTOWithCourses =
                teacherService.getTeacherDTOByIdWithCourses(id);
        LOGGER.info(Messages.OK_GET_TEACHER_BY_ID, id, teacherDTOWithCourses);
        return new ResponseEntity<>(teacherDTOWithCourses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_TEACHER_BY_ID,id);
        teacherService.deleteTeacherById(id);
        LOGGER.info(Messages.OK_DELETE_TEACHER_BY_ID, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherCreateDTO teacherCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_TEACHER);
        TeacherDTO createdTeacherDTO = teacherService.createTeacherDTO(teacherCreateDTO);
        LOGGER.info(Messages.OK_CREATE_TEACHER, createdTeacherDTO);
        return new ResponseEntity<>(createdTeacherDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Integer id,
                                                    @Valid @RequestBody TeacherCreateDTO teacherCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_TEACHER, teacherCreateDTO);
        teacherService.getTeacherById(id);
        TeacherDTO updatedTeacherDTO = teacherService.updateTeacherDTO(id, teacherCreateDTO);
        LOGGER.info(Messages.OK_UPDATE_TEACHER, updatedTeacherDTO);
        return new ResponseEntity<>(updatedTeacherDTO, HttpStatus.OK);
    }

    @PostMapping("/{teacherId}/add-course/{courseId}")
    public ResponseEntity<TeacherDTOWithCourses> addCourseToTeacher(@PathVariable Integer teacherId,
                                                                    @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_ADD_COURSE_TO_TEACHER, teacherId, courseId);
        Course course = courseService.getCourseById(courseId);
        if (course.getTeacher() != null) {
                throw new ServiceException(  Messages.ERROR_ADD_COURSE_TO_TEACHER);
        }
        Teacher teacher = teacherService.getTeacherById(teacherId);
        course.setTeacher(teacher);
        courseService.updateCourse(course);
        TeacherDTOWithCourses teacherDTOWithCourses =
                teacherService.getTeacherDTOByIdWithCourses(teacherId);
        LOGGER.info(Messages.OK_ADD_COURSE_TO_TEACHER, teacherId, courseId, course);
        return new ResponseEntity<>(teacherDTOWithCourses, HttpStatus.OK);
    }

    @PostMapping("/{teacherId}/remove-course/{courseId}")
    public ResponseEntity<TeacherDTOWithCourses> removeCourseFromTeacher(@PathVariable Integer teacherId,
                                                                         @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId);
        Course course = courseService.getCourseById(courseId);
        if (course.getTeacher() == null) {
            throw new ServiceException(Messages.ERROR_REMOVE_COURSE_FROM_TEACHER);
        }
        course.setTeacher(null);
        courseService.updateCourse(course);
        TeacherDTOWithCourses teacherDTOWithCourses =
                teacherService.getTeacherDTOByIdWithCourses(teacherId);
        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId, course);
        return new ResponseEntity<>(teacherDTOWithCourses, HttpStatus.OK);
    }

}
