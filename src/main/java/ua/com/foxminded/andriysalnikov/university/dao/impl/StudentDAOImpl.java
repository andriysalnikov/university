package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public StudentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Student> getAllStudents() {
        return sessionFactory.getCurrentSession()
                .createQuery(DBConstants.HQL_GET_ALL_STUDENTS, Student.class)
                .getResultList();
    }

    @Override
    public List<Student> getAllStudentsWithoutFaculty() {
        return sessionFactory.getCurrentSession()
                .createQuery(DBConstants.HQL_GET_ALL_STUDENTS_WITHOUT_FACULTY, Student.class)
                .getResultList();
    }

    @Override
    public Optional<Student> getStudentById(Integer id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(Student.class, id));
    }

    @Override
    public Optional<Student> getStudentByIdWithCourses(Integer id) {
        Student student = sessionFactory.getCurrentSession().find(Student.class, id);
        if (student != null && !student.getCourses().isEmpty()) {
            student.getCourses().get(0);
        }
        return Optional.ofNullable(student);
    }

    @Override
    public Optional<Student> createStudent(Student student) {
        sessionFactory.getCurrentSession().persist(student);
        return Optional.ofNullable(student);
    }

    @Override
    public Optional<Student> deleteStudentById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Student student = session.find(Student.class, id);
        session.remove(student);
        return Optional.ofNullable(student);
    }

    @Override
    public Optional<Student> updateStudent(Student student) {
        sessionFactory.getCurrentSession().merge(student);
        return Optional.ofNullable(student);
    }

}
