package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.ClassRoomDAO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.util.List;
import java.util.Optional;

@Repository
public class ClassRoomDAOImpl implements ClassRoomDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public ClassRoomDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ClassRoom> getAllClassRooms() {
        return sessionFactory.getCurrentSession()
                .createQuery(DBConstants.HQL_GET_ALL_CLASSROOMS, ClassRoom.class)
                .getResultList();
    }

    @Override
    public List<ClassRoom> getAllClassRoomsWithoutFaculty() {
        return sessionFactory.getCurrentSession()
                .createQuery(DBConstants.HQL_GET_ALL_CLASSROOMS_WITHOUT_FACULTY, ClassRoom.class)
                .getResultList();
    }

    @Override
    public Optional<ClassRoom> getClassRoomById(Integer id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(ClassRoom.class, id));
    }

    @Override
    public Optional<ClassRoom> createClassRoom(ClassRoom classRoom) {
        sessionFactory.getCurrentSession().persist(classRoom);
        return Optional.ofNullable(classRoom);
    }

    @Override
    public Optional<ClassRoom> deleteClassRoomById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        ClassRoom classRoom = session.find(ClassRoom.class, id);
        session.remove(classRoom);
        return Optional.ofNullable(classRoom);
    }

    @Override
    public Optional<ClassRoom> updateClassRoom(ClassRoom classRoom) {
        sessionFactory.getCurrentSession().merge(classRoom);
        return Optional.ofNullable(classRoom);
    }

}
