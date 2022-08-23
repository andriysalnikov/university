package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.FacultyDAO;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class FacultyDAOImpl implements FacultyDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Faculty> getAllFaculties() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_FACULTIES, Faculty.class)
                .getResultList();
    }

    @Override
    public Optional<Faculty> getFacultyById(Integer id) {
        return Optional.ofNullable(entityManager.find(Faculty.class, id));
    }

    @Override
    public Optional<Faculty> getFacultyByIdWithClassRooms(Integer id) {
        Faculty faculty = entityManager.find(Faculty.class, id);
        if (faculty != null && !faculty.getClassRooms().isEmpty()) {
            faculty.getClassRooms().get(0);
        }
        return Optional.ofNullable(faculty);
    }

    @Override
    public Optional<Faculty> getFacultyByIdWithCourses(Integer id) {
        Faculty faculty = entityManager.find(Faculty.class, id);
        if (faculty != null && !faculty.getCourses().isEmpty()) {
            faculty.getCourses().get(0);
        }
        return Optional.ofNullable(faculty);
    }

    @Override
    public Optional<Faculty> getFacultyByIdWithStudents(Integer id) {
        Faculty faculty = entityManager.find(Faculty.class, id);
        if (faculty != null && !faculty.getStudents().isEmpty()) {
            faculty.getStudents().get(0);
        }
        return Optional.ofNullable(faculty);
    }

    @Override
    public Optional<Faculty> createFaculty(Faculty faculty) {
        entityManager.persist(faculty);
        return Optional.ofNullable(faculty);
    }

    @Override
    public Optional<Faculty> deleteFacultyById(Integer id) {
        Faculty faculty = entityManager.find(Faculty.class, id);
        entityManager.remove(faculty);
        return Optional.ofNullable(faculty);
    }

    @Override
    public Optional<Faculty> updateFaculty(Faculty faculty) {
        entityManager.merge(faculty);
        return Optional.ofNullable(faculty);
    }

}
