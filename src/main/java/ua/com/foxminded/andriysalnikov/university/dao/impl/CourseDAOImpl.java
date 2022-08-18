package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.dao.CourseDAO;
import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CourseDAOImpl implements CourseDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public CourseDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Course> getAllCourses() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Course", Course.class).getResultList()
                .stream().sorted(Comparator.comparingInt(Course::getId)).collect(Collectors.toList());
    }

//    @Override
//    public List<Course> getAllCoursesWithoutTeacher() {
//        return jdbcTemplate.query(
//                DBConstants.SQL_GET_ALL_COURSES_WITHOUT_TEACHER,
//                    new BeanPropertyRowMapper<>(Course.class));
//    }

//    @Override
//    public List<Course> getAllCoursesWithoutFaculty() {
//        return jdbcTemplate.query(
//                DBConstants.SQL_GET_ALL_COURSES_WITHOUT_FACULTY,
//                new BeanPropertyRowMapper<>(Course.class));
//    }

//    @Override
//    public List<Course> getAllOtherAvailableCoursesForStudent(Integer studentId) {
//        return jdbcTemplate.query(
//                DBConstants.SQL_GET_ALL_OTHER_AVAILABLE_COURSES_FOR_STUDENT,
//                    courseMapper, studentId);
//    }

    @Override
    public Optional<Course> getCourseById(Integer id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(Course.class, id));
    }

    @Override
    public Optional<Course> createCourse(Course course) {
        sessionFactory.getCurrentSession().persist(course);
        return Optional.ofNullable(course);
    }

    @Override
    public Optional<Course> deleteCourserById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.find(Course.class, id);
        session.remove(course);
        return Optional.ofNullable(course);
    }

    @Override
    public Optional<Course> updateCourse(Course course) {
        sessionFactory.getCurrentSession().merge(course);
        return Optional.ofNullable(course);
    }

//    @Override
//    public Optional<Course> setTeacherToCourse(Integer teacherId, Integer courseId) {
//        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_SET_TEACHER_TO_COURSE,
//                courseResultSetExtractor, teacherId, courseId));
//    }

//    @Override
//    public Optional<Course> removeTeacherFromCourse(Integer courseId) {
//        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_REMOVE_TEACHER_FROM_COURSE,
//                courseResultSetExtractor, courseId));
//    }

//    @Override
//    public Optional<Course> setFacultyToCourse(Integer facultyId, Integer courseId) {
//        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_SET_FACULTY_TO_COURSE,
//                courseResultSetExtractor, facultyId, courseId));
//    }

//    @Override
//    public Optional<Course> removeFacultyFromCourse(Integer courseId) {
//        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_REMOVE_FACULTY_FROM_COURSE,
//                courseResultSetExtractor, courseId));
//    }

//    @Override
//    public void setStudentToCourse(Integer studentId, Integer courseId) {
//        jdbcTemplate.update(DBConstants.SQL_SET_STUDENT_TO_COURSE, studentId, courseId);
//    }

//    @Override
//    public void removeStudentFromCourse(Integer studentId, Integer courseId) {
//        jdbcTemplate.update(DBConstants.SQL_REMOVE_STUDENT_FROM_COURSE, studentId, courseId);
//    }

}
