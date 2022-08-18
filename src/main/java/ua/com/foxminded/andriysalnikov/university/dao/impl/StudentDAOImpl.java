package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .createQuery("from Student", Student.class).getResultList()
                .stream().sorted(Comparator.comparingInt(Student::getId)).collect(Collectors.toList());
    }

    @Override
    public Optional<Student> getStudentById(Integer id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(Student.class, id));
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

//    @Override
//    public List<Course> getStudentCoursesByStudentId(Integer id) {
//        return jdbcTemplate.query(
//                DBConstants.SQL_GET_STUDENT_COURSES_BY_STUDENT_ID, courseMapper, id);
//    }

//    @Override
//    public Optional<Student> setFacultyToStudent(Integer facultyId, Integer studentId) {
//        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_SET_FACULTY_TO_STUDENT,
//                studentResultSetExtractor, facultyId, studentId));
//    }

//    @Override
//    public Optional<Student> removeFacultyFromStudent(Integer studentId) {
//        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_REMOVE_FACULTY_FROM_STUDENT,
//                studentResultSetExtractor, studentId));
//    }

}
