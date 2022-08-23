package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.TeacherDAO;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherDAOImpl implements TeacherDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Teacher> getAllTeachers() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_TEACHERS, Teacher.class)
                .getResultList();
    }

    @Override
    public Optional<Teacher> getTeacherById(Integer id) {
        return Optional.ofNullable(entityManager.find(Teacher.class, id));
    }

    @Override
    public Optional<Teacher> getTeacherByIdWithCourses(Integer id) {
        Teacher teacher = entityManager
                .createQuery(DBConstants.JPQL_GET_TEACHER_BY_ID_WITH_COURSES,Teacher.class)
                .setParameter("teacherId", id)
                .getSingleResult();
        return Optional.ofNullable(teacher);
    }

    @Override
    public Optional<Teacher> createTeacher(Teacher teacher) {
        entityManager.persist(teacher);
        return Optional.ofNullable(teacher);
    }

    @Override
    public Optional<Teacher> deleteTeacherById(Integer id) {
        Teacher teacher = entityManager.find(Teacher.class, id);
        entityManager.remove(teacher);
        return Optional.ofNullable(teacher);
    }

    @Override
    public Optional<Teacher> updateTeacher(Teacher teacher) {
        entityManager.merge(teacher);
        return Optional.ofNullable(teacher);
    }

}
