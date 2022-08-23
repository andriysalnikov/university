package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.ClassRoomDAO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ClassRoomDAOImpl implements ClassRoomDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ClassRoom> getAllClassRooms() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_CLASSROOMS, ClassRoom.class)
                .getResultList();
    }

    @Override
    public List<ClassRoom> getAllClassRoomsWithoutFaculty() {
        return entityManager.createQuery(DBConstants.JPQL_GET_ALL_CLASSROOMS_WITHOUT_FACULTY, ClassRoom.class)
                .getResultList();
    }

    @Override
    public Optional<ClassRoom> getClassRoomById(Integer id) {
        return Optional.ofNullable(entityManager.find(ClassRoom.class, id));
    }

    @Override
    public Optional<ClassRoom> createClassRoom(ClassRoom classRoom) {
        entityManager.persist(classRoom);
        return Optional.ofNullable(classRoom);
    }

    @Override
    public Optional<ClassRoom> deleteClassRoomById(Integer id) {
        ClassRoom classRoom = entityManager.find(ClassRoom.class, id);
        entityManager.remove(classRoom);
        return Optional.ofNullable(classRoom);
    }

    @Override
    public Optional<ClassRoom> updateClassRoom(ClassRoom classRoom) {
        entityManager.merge(classRoom);
        return Optional.ofNullable(classRoom);
    }

}
