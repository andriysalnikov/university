package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.TeacherDAO;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.List;
import java.util.Optional;

@Repository
public class TeacherDAOImpl implements TeacherDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TeacherDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return sessionFactory.getCurrentSession()
                .createQuery(DBConstants.HQL_GET_ALL_TEACHERS, Teacher.class)
                .getResultList();
    }

    @Override
    public Optional<Teacher> getTeacherById(Integer id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(Teacher.class, id));
    }

    @Override
    public Optional<Teacher> getTeacherByIdWithCourses(Integer id) {
        Teacher teacher = sessionFactory.getCurrentSession().find(Teacher.class, id);
        if (teacher != null && !teacher.getCourses().isEmpty()) {
            teacher.getCourses().get(0);
        }
        return Optional.ofNullable(teacher);
    }

    @Override
    public Optional<Teacher> createTeacher(Teacher teacher) {
        sessionFactory.getCurrentSession().persist(teacher);
        return Optional.ofNullable(teacher);
    }

    @Override
    public Optional<Teacher> deleteTeacherById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Teacher teacher = session.find(Teacher.class, id);
        session.remove(teacher);
        return Optional.ofNullable(teacher);
    }

    @Override
    public Optional<Teacher> updateTeacher(Teacher teacher) {
        sessionFactory.getCurrentSession().merge(teacher);
        return Optional.ofNullable(teacher);
    }

}
