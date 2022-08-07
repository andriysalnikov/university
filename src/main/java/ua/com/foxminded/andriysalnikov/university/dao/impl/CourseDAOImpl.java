package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.CourseDAO;
import ua.com.foxminded.andriysalnikov.university.mappers.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.extractors.CourseResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseDAOImpl implements CourseDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;
    private final CourseResultSetExtractor courseResultSetExtractor;

    @Autowired
    public CourseDAOImpl(JdbcTemplate jdbcTemplate, CourseMapper courseMapper,
                         CourseResultSetExtractor courseResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseMapper = courseMapper;
        this.courseResultSetExtractor = courseResultSetExtractor;
    }

    @Override
    public List<Course> getAllCourses() {
        return jdbcTemplate.query(
                DBConstants.SQL_GET_ALL_COURSES, new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public List<Course> getAllCoursesWithoutTeacher() {
        return jdbcTemplate.query(
                DBConstants.SQL_GET_ALL_COURSES_WITHOUT_TEACHER,
                    new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public List<Course> getAllCoursesWithoutFaculty() {
        return jdbcTemplate.query(
                DBConstants.SQL_GET_ALL_COURSES_WITHOUT_FACULTY,
                new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public List<Course> getAllOtherAvailableCoursesForStudent(Integer studentId) {
        return jdbcTemplate.query(
                DBConstants.SQL_GET_ALL_OTHER_AVAILABLE_COURSES_FOR_STUDENT,
                    courseMapper, studentId);
    }

    @Override
    public Optional<Course> getCourseById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_GET_COURSE_BY_ID,
                courseResultSetExtractor, id));
    }

    @Override
    public Optional<Course> createCourse(Course course) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_CREATE_COURSE,
                courseResultSetExtractor, course.getName(), course.getDescription()));
    }

    @Override
    public Optional<Course> deleteCourserById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_DELETE_COURSE_BY_ID,
                courseResultSetExtractor, id));
    }

    @Override
    public Optional<Course> updateCourse(Course course) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_UPDATE_COURSE,
                courseResultSetExtractor, course.getName(), course.getDescription(), course.getId()));
    }

    @Override
    public Optional<Course> setTeacherToCourse(Integer teacherId, Integer courseId) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_SET_TEACHER_TO_COURSE,
                courseResultSetExtractor, teacherId, courseId));
    }

    @Override
    public Optional<Course> removeTeacherFromCourse(Integer courseId) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_REMOVE_TEACHER_FROM_COURSE,
                courseResultSetExtractor, courseId));
    }

    @Override
    public Optional<Course> setFacultyToCourse(Integer facultyId, Integer courseId) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_SET_FACULTY_TO_COURSE,
                courseResultSetExtractor, facultyId, courseId));
    }

    @Override
    public Optional<Course> removeFacultyFromCourse(Integer courseId) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_REMOVE_FACULTY_FROM_COURSE,
                courseResultSetExtractor, courseId));
    }

    @Override
    public void setStudentToCourse(Integer studentId, Integer courseId) {
        jdbcTemplate.update(DBConstants.SQL_SET_STUDENT_TO_COURSE, studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(Integer studentId, Integer courseId) {
        jdbcTemplate.update(DBConstants.SQL_REMOVE_STUDENT_FROM_COURSE, studentId, courseId);
    }

}
