package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDAOImpl implements StudentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> getAllStudents() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_STUDENTS, Student.class)
                .getResultList();
    }

    @Override
    public List<Student> getAllStudentsWithoutFaculty() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_STUDENTS_WITHOUT_FACULTY, Student.class)
                .getResultList();
    }

    @Override
    public Optional<Student> getStudentById(Integer id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

    @Override
    public Optional<Student> getStudentByIdWithCourses(Integer id) {
        Student student = entityManager.find(Student.class, id);
        if (student != null && !student.getCourses().isEmpty()) {
            student.getCourses().get(0);
        }
        return Optional.ofNullable(student);
    }

    @Override
    public Optional<Student> createStudent(Student student) {
        entityManager.persist(student);
        return Optional.ofNullable(student);
    }

    @Override
    public Optional<Student> deleteStudentById(Integer id) {
        Student student = entityManager.find(Student.class, id);
        entityManager.remove(student);
        return Optional.ofNullable(student);
    }

    @Override
    public Optional<Student> updateStudent(Student student) {
        entityManager.merge(student);
        return Optional.ofNullable(student);
    }

}
