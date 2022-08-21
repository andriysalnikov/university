package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.CourseDAO;
import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.util.List;
import java.util.Optional;

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
                .createQuery(DBConstants.HQL_GET_ALL_COURSES, Course.class)
                .getResultList();
    }

    @Override
    public List<Course> getAllCoursesWithoutFaculty() {
        return sessionFactory.getCurrentSession()
                .createQuery(DBConstants.HQL_GET_ALL_COURSES_WITHOUT_FACULTY, Course.class)
                .getResultList();
    }

    @Override
    public List<Course> getAllCoursesWithoutTeacher() {
        return sessionFactory.getCurrentSession()
                .createQuery(DBConstants.HQL_GET_ALL_COURSES_WITHOUT_TEACHER, Course.class)
                .getResultList();
    }

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

}
