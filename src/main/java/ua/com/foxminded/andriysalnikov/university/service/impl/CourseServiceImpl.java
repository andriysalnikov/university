package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.CourseDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseDAO courseDAO;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public List<Course> getAllCourses() {
        LOGGER.debug(Messages.TRY_GET_ALL_COURSES);
        List<Course> courses = courseDAO.getAllCourses();
        LOGGER.debug(Messages.OK_GET_ALL_COURSES, courses);
        return courses;
    }

    @Override
    public List<Course> getAllCoursesWithoutTeacher() {
        LOGGER.debug(Messages.TRY_GET_ALL_COURSES_WITHOUT_TEACHER);
        List<Course> courses = courseDAO.getAllCoursesWithoutTeacher();
        LOGGER.debug(Messages.OK_GET_ALL_COURSES_WITHOUT_TEACHER, courses);
        return courses;
    }

    @Override
    public List<Course> getAllCoursesWithoutFaculty() {
        LOGGER.debug(Messages.TRY_GET_ALL_COURSES_WITHOUT_FACULTY);
        List<Course> courses = courseDAO.getAllCoursesWithoutFaculty();
        LOGGER.debug(Messages.OK_GET_ALL_COURSES_WITHOUT_FACULTY, courses);
        return courses;
    }

    @Override
    public Course getCourseById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_COURSE_BY_ID, id);
        Validation.validateId(id);
        Course course = courseDAO.getCourseById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_COURSE_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_COURSE_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_COURSE_BY_ID, id, course);
        return course;
    }

    @Override
    public Course createCourse(Course course) {
        LOGGER.debug(Messages.TRY_CREATE_COURSE);
        Validation.validateCourse(course);
        Course createdCourse = courseDAO.createCourse(course).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_CREATE_COURSE);
            throw new ServiceException(Messages.ERROR_CREATE_COURSE);
        });
        LOGGER.debug(Messages.OK_CREATE_COURSE, createdCourse);
        return createdCourse;
    }

    @Override
    public Course deleteCourseById(Integer id) {
        Validation.validateId(id);
        Course deletedCourse = courseDAO.deleteCourserById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_DELETE_COURSE_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_COURSE_BY_ID);
        });
        LOGGER.debug(Messages.OK_DELETE_COURSE_BY_ID, id, deletedCourse);
        return deletedCourse;
    }

    @Override
    public Course updateCourse(Course course) {
        LOGGER.debug(Messages.TRY_UPDATE_COURSE, course);
        Validation.validateCourse(course);
        Course updatedCourse = courseDAO.updateCourse(course).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_UPDATE_COURSE);
            throw new ServiceException(Messages.ERROR_UPDATE_COURSE);
        });
        LOGGER.debug(Messages.OK_UPDATE_COURSE, updatedCourse);
        return updatedCourse;
    }

}
