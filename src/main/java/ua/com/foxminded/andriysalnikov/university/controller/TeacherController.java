package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TeacherWithCoursesDTO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.mapper.TeacherMapper;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;
    private final CourseService courseService;

    @Autowired
    public TeacherController(TeacherService teacherService,TeacherMapper teacherMapper,
                             CourseService courseService) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        LOGGER.info(Messages.TRY_GET_ALL_TEACHERS);
        List<Teacher> teachers = teacherService.getAllTeachers();
        LOGGER.info(Messages.OK_GET_ALL_TEACHERS, teachers);
        return new ResponseEntity<>(teachers.stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_TEACHER_BY_ID, id);
        Teacher teacher = teacherService.getTeacherById(id);
        LOGGER.info(Messages.OK_GET_TEACHER_BY_ID, id, teacher);
        return new ResponseEntity<>(teacherMapper.toDTO(teacher), HttpStatus.OK);
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<TeacherWithCoursesDTO> getTeacherByIdWithCourses(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_GET_TEACHER_BY_ID, id);
        Teacher teacher = teacherService.getTeacherByIdWithCourses(id);
        LOGGER.info(Messages.OK_GET_TEACHER_BY_ID, id, teacher);
        return new ResponseEntity<>(teacherMapper.toDTOWithCourses(teacher), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable Integer id) {
        LOGGER.info(Messages.TRY_DELETE_TEACHER_BY_ID,id);
        teacherService.deleteTeacherById(id);
        LOGGER.info(Messages.OK_DELETE_TEACHER_BY_ID, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherCreateDTO teacherCreateDTO) {
        LOGGER.info(Messages.TRY_CREATE_TEACHER);
        Teacher createdTeacher =
                teacherService.createTeacher(teacherMapper.fromDTO(teacherCreateDTO));
        LOGGER.info(Messages.OK_CREATE_TEACHER, createdTeacher);
        return new ResponseEntity<>(teacherMapper.toDTO(createdTeacher), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Integer id,
                                                    @Valid @RequestBody TeacherCreateDTO teacherCreateDTO) {
        LOGGER.info(Messages.TRY_UPDATE_TEACHER, teacherCreateDTO);
        teacherService.getTeacherById(id);
        Teacher teacher = teacherMapper.fromDTO(teacherCreateDTO);
        teacher.setId(id);
        Teacher updatedTeacher = teacherService.updateTeacher(teacher);
        LOGGER.info(Messages.OK_UPDATE_TEACHER, updatedTeacher);
        return new ResponseEntity<>(teacherMapper.toDTO(updatedTeacher), HttpStatus.OK);
    }

    @PostMapping("/{teacherId}/add-course/{courseId}")
    public ResponseEntity<TeacherWithCoursesDTO> addCourseToTeacher(@PathVariable Integer teacherId,
                                                                    @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_ADD_COURSE_TO_TEACHER, teacherId, courseId);
        Course course = courseService.getCourseById(courseId);
        if (course.getTeacher() != null) {
                throw new ServiceException(  Messages.ERROR_ADD_COURSE_TO_TEACHER);
        }
        Teacher teacher = teacherService.getTeacherById(teacherId);
        course.setTeacher(teacher);
        courseService.updateCourse(course);
        teacher = teacherService.getTeacherByIdWithCourses(teacherId);
        LOGGER.info(Messages.OK_ADD_COURSE_TO_TEACHER, teacherId, courseId, course);
        return new ResponseEntity<>(teacherMapper.toDTOWithCourses(teacher), HttpStatus.OK);
    }

    @PostMapping("/{teacherId}/remove-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TeacherWithCoursesDTO> removeCourseFromTeacher(@PathVariable Integer teacherId,
                                                                         @PathVariable Integer courseId) {
        LOGGER.info(Messages.TRY_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId);
        Course course = courseService.getCourseById(courseId);
        if (course.getTeacher() == null) {
            throw new ServiceException(Messages.ERROR_REMOVE_COURSE_FROM_TEACHER);
        }
        course.setTeacher(null);
        courseService.updateCourse(course);
        Teacher teacher = teacherService.getTeacherByIdWithCourses(teacherId);
        LOGGER.info(Messages.OK_REMOVE_COURSE_FROM_TEACHER, courseId, teacherId, course);
        return new ResponseEntity<>(teacherMapper.toDTOWithCourses(teacher), HttpStatus.OK);
    }

}
