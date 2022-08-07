package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.ClassRoomDAO;
import ua.com.foxminded.andriysalnikov.university.extractors.ClassRoomResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.util.List;
import java.util.Optional;

@Repository
public class ClassRoomDAOImpl implements ClassRoomDAO {

    private final JdbcTemplate jdbcTemplate;
    private final ClassRoomResultSetExtractor classRoomResultSetExtractor;

    @Autowired
    public ClassRoomDAOImpl(JdbcTemplate jdbcTemplate, ClassRoomResultSetExtractor classRoomResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.classRoomResultSetExtractor = classRoomResultSetExtractor;
    }

    @Override
    public List<ClassRoom> getAllClassRooms() {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_CLASSROOMS, new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public List<ClassRoom> getAllClassRoomsWithoutFaculty() {
        return jdbcTemplate.query(
                DBConstants.SQL_GET_ALL_CLASSROOMS_WITHOUT_FACULTY,
                new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public Optional<ClassRoom> getClassRoomById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(
                DBConstants.SQL_GET_CLASSROOM_BY_ID, classRoomResultSetExtractor, id));
    }

    @Override
    public Optional<ClassRoom> createClassRoom(ClassRoom classRoom) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_CREATE_CLASSROOM,
                        classRoomResultSetExtractor, classRoom.getName()));
    }

    @Override
    public Optional<ClassRoom> deleteClassRoomById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_DELETE_CLASSROOM_BY_ID,
                        classRoomResultSetExtractor, id));
    }

    @Override
    public Optional<ClassRoom> updateClassRoom(ClassRoom classRoom) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_UPDATE_CLASSROOM,
                        classRoomResultSetExtractor, classRoom.getName(), classRoom.getId()));
    }

    @Override
    public Optional<ClassRoom> setFacultyToClassRoom(Integer facultyId, Integer classRoomId) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_SET_FACULTY_TO_CLASSROOM,
                        classRoomResultSetExtractor, facultyId, classRoomId));
    }

    @Override
    public Optional<ClassRoom> removeFacultyFromClassRoom(Integer classRoomId) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_REMOVE_FACULTY_FROM_CLASSROOM,
                        classRoomResultSetExtractor, classRoomId));
    }

}
