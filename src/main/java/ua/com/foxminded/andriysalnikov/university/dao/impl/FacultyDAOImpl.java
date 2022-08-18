package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.dao.FacultyDAO;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FacultyDAOImpl implements FacultyDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public FacultyDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Faculty> getAllFaculties() {

        return sessionFactory.getCurrentSession()
            .createQuery("from Faculty", Faculty.class).getResultList()
            .stream().sorted(Comparator.comparingInt(Faculty::getId)).collect(Collectors.toList());

    }

    @Override
    public Optional<Faculty> getFacultyById(Integer id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(Faculty.class, id));
    }

    @Override
    public Optional<Faculty> createFaculty(Faculty faculty) {
        sessionFactory.getCurrentSession().persist(faculty);
        return Optional.ofNullable(faculty);
    }

    @Override
    public Optional<Faculty> deleteFacultyById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Faculty faculty = session.find(Faculty.class, id);
        session.remove(faculty);
        return Optional.ofNullable(faculty);
    }

    @Override
    public Optional<Faculty> updateFaculty(Faculty faculty) {
        sessionFactory.getCurrentSession().merge(faculty);
        return Optional.ofNullable(faculty);
    }

}
