package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.CourseDAO;
import ua.com.foxminded.andriysalnikov.university.model.Course;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDAOImpl implements CourseDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Course> getAllCourses() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_COURSES, Course.class)
                .getResultList();
    }

    @Override
    public List<Course> getAllCoursesWithoutFaculty() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_COURSES_WITHOUT_FACULTY, Course.class)
                .getResultList();
    }

    @Override
    public List<Course> getAllCoursesWithoutTeacher() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_COURSES_WITHOUT_TEACHER, Course.class)
                .getResultList();
    }

    @Override
    public Optional<Course> getCourseById(Integer id) {
        return Optional.ofNullable(entityManager.find(Course.class, id));
    }

    @Override
    public Optional<Course> createCourse(Course course) {
        entityManager.persist(course);
        return Optional.ofNullable(course);
    }

    @Override
    public Optional<Course> deleteCourserById(Integer id) {
        Course course = entityManager.find(Course.class, id);
        entityManager.remove(course);
        return Optional.ofNullable(course);
    }

    @Override
    public Optional<Course> updateCourse(Course course) {
        entityManager.merge(course);
        return Optional.ofNullable(course);
    }

}
