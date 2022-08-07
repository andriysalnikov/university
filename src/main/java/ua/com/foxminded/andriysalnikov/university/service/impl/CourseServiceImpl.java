package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.CourseDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.service.CourseService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.util.List;

@Service
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
    public List<Course> getAllOtherAvailableCoursesForStudent(Integer studentId) {
        LOGGER.debug(Messages.TRY_GET_ALL_OTHER_AVAILABLE_COURSES_FOR_STUDENT, studentId);
        List<Course> courses = courseDAO.getAllOtherAvailableCoursesForStudent(studentId);
        LOGGER.debug(Messages.OK_GET_ALL_OTHER_AVAILABLE_COURSES_FOR_STUDENT, studentId, courses);
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

    @Override
    public Course setTeacherToCourse(Integer teacherId, Integer courseId) {
        LOGGER.debug(Messages.TRY_SET_TEACHER_TO_COURSE, teacherId, courseId);
        Validation.validateId(teacherId);
        Validation.validateId(courseId);
        Course updatedCourse = courseDAO.setTeacherToCourse(teacherId, courseId).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_SET_TEACHER_TO_COURSE);
            throw new ServiceException(Messages.ERROR_SET_TEACHER_TO_COURSE);
        });
        LOGGER.debug(Messages.OK_SET_TEACHER_TO_COURSE, teacherId, courseId, updatedCourse);
        return updatedCourse;
    }

    @Override
    public Course removeTeacherFromCourse(Integer courseId) {
        LOGGER.debug(Messages.TRY_REMOVE_TEACHER_FROM_COURSE, courseId);
        Validation.validateId(courseId);
        Course updatedCourse = courseDAO.removeTeacherFromCourse(courseId).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_REMOVE_TEACHER_FROM_COURSE);
            throw new ServiceException(Messages.ERROR_REMOVE_TEACHER_FROM_COURSE);
        });
        LOGGER.debug(Messages.OK_REMOVE_TEACHER_FROM_COURSE, courseId, updatedCourse);
        return updatedCourse;
    }

    @Override
    public Course setFacultyToCourse(Integer facultyId, Integer courseId) {
        LOGGER.debug(Messages.TRY_SET_FACULTY_TO_COURSE, facultyId, courseId);
        Validation.validateId(facultyId);
        Validation.validateId(courseId);
        Course updatedCourse = courseDAO.setFacultyToCourse(facultyId, courseId).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_SET_FACULTY_TO_COURSE);
            throw new ServiceException(Messages.ERROR_SET_FACULTY_TO_COURSE);
        });
        LOGGER.debug(Messages.OK_SET_FACULTY_TO_COURSE, facultyId, courseId, updatedCourse);
        return updatedCourse;
    }

    @Override
    public Course removeFacultyFromCourse(Integer courseId) {
        LOGGER.debug(Messages.TRY_REMOVE_FACULTY_FROM_COURSE, courseId);
        Validation.validateId(courseId);
        Course updatedCourse = courseDAO.removeFacultyFromCourse(courseId).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_REMOVE_FACULTY_FROM_COURSE);
            throw new ServiceException(Messages.ERROR_REMOVE_TEACHER_FROM_COURSE);
        });
        LOGGER.debug(Messages.OK_REMOVE_FACULTY_FROM_COURSE, courseId, updatedCourse);
        return updatedCourse;
    }

    @Override
    public void setStudentToCourse(Integer studentId, Integer courseId) {
        LOGGER.debug(Messages.TRY_SET_STUDENT_TO_COURSE, studentId, courseId);
        Validation.validateId(studentId);
        Validation.validateId(courseId);
        try {
            courseDAO.setStudentToCourse(studentId, courseId);
        } catch (DataAccessException exception) {
            LOGGER.error(Messages.ERROR_SET_STUDENT_TO_COURSE);
            throw new ServiceException(Messages.ERROR_SET_STUDENT_TO_COURSE);
        }
        LOGGER.debug(Messages.OK_SET_STUDENT_TO_COURSE, studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(Integer studentId, Integer courseId) {
        LOGGER.debug(Messages.TRY_REMOVE_STUDENT_FROM_COURSE, studentId, courseId);
        Validation.validateId(studentId);
        Validation.validateId(courseId);
        try {
            courseDAO.removeStudentFromCourse(studentId, courseId);
        } catch (DataAccessException exception) {
            LOGGER.error(Messages.ERROR_REMOVE_STUDENT_FROM_COURSE);
            throw new ServiceException(Messages.ERROR_REMOVE_STUDENT_FROM_COURSE);
        }
        LOGGER.debug(Messages.OK_REMOVE_STUDENT_FROM_COURSE, studentId, courseId);
    }

}
