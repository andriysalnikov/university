package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.ClassRoomDAO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .createQuery("from ClassRoom", ClassRoom.class).getResultList()
                .stream().sorted(Comparator.comparingInt(ClassRoom::getId)).collect(Collectors.toList());
    }

//    @Override
//    public List<ClassRoom> getAllClassRoomsWithoutFaculty() {
//        return jdbcTemplate.query(
//                DBConstants.SQL_GET_ALL_CLASSROOMS_WITHOUT_FACULTY,
//                new BeanPropertyRowMapper<>(ClassRoom.class));
//    }

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

//    @Override
//    public Optional<ClassRoom> setFacultyToClassRoom(Integer facultyId, Integer classRoomId) {
//        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_SET_FACULTY_TO_CLASSROOM,
//                        classRoomResultSetExtractor, facultyId, classRoomId));
//    }
//
//    @Override
//    public Optional<ClassRoom> removeFacultyFromClassRoom(Integer classRoomId) {
//        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_REMOVE_FACULTY_FROM_CLASSROOM,
//                        classRoomResultSetExtractor, classRoomId));
//    }

}
