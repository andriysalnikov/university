package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.repository.CourseRepository;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        LOGGER.debug(Messages.TRY_GET_ALL_COURSES);
        List<Course> courses = courseRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        LOGGER.debug(Messages.OK_GET_ALL_COURSES, courses);
        return courses;
    }

    @Override
    public List<Course> getAllCoursesWithoutTeacher() {
        LOGGER.debug(Messages.TRY_GET_ALL_COURSES_WITHOUT_TEACHER);
        List<Course> courses = courseRepository.findCoursesByTeacherIsNullOrderByIdAsc();
        LOGGER.debug(Messages.OK_GET_ALL_COURSES_WITHOUT_TEACHER, courses);
        return courses;
    }

    @Override
    public List<Course> getAllCoursesWithoutFaculty() {
        LOGGER.debug(Messages.TRY_GET_ALL_COURSES_WITHOUT_FACULTY);
        List<Course> courses = courseRepository.findCoursesByFacultyIsNullOrderByIdAsc();
        LOGGER.debug(Messages.OK_GET_ALL_COURSES_WITHOUT_FACULTY, courses);
        return courses;
    }

    @Override
    public Course getCourseById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_COURSE_BY_ID, id);
        Validation.validateId(id);
        Course course = courseRepository.getCourseById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_COURSE_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_COURSE_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_COURSE_BY_ID, id, course);
        return course;
    }

    @Modifying
    @Transactional
    @Override
    public Course createCourse(Course course) {
        LOGGER.debug(Messages.TRY_CREATE_COURSE);
        Validation.validateCourse(course);
        Course createdCourse;
        try {
            createdCourse = courseRepository.save(course);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_CREATE_COURSE);
            throw new ServiceException(Messages.ERROR_CREATE_COURSE);
        }
        LOGGER.debug(Messages.OK_CREATE_COURSE, createdCourse);
        return createdCourse;
    }

    @Modifying
    @Transactional
    @Override
    public void deleteCourseById(Integer id) {
        Validation.validateId(id);
        if (courseRepository.deleteCourseById(id) == 0) {
            LOGGER.error(Messages.ERROR_DELETE_COURSE_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_COURSE_BY_ID);
        }
        LOGGER.debug(Messages.OK_DELETE_COURSE_BY_ID, id);
    }

    @Modifying
    @Transactional
    @Override
    public Course updateCourse(Course course) {
        LOGGER.debug(Messages.TRY_UPDATE_COURSE, course);
        Validation.validateCourse(course);
        Course updatedCourse;
        try {
            updatedCourse = courseRepository.save(course);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_UPDATE_COURSE);
            throw new ServiceException(Messages.ERROR_UPDATE_COURSE);
        }
        LOGGER.debug(Messages.OK_UPDATE_COURSE, updatedCourse);
        return updatedCourse;
    }

}
