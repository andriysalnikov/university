package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.assembler.Assembler;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.ClassRoomDAO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ClassRoomDAOImpl implements ClassRoomDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassRoomDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ClassRoom> getAllClassRooms() {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_CLASSROOMS, new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public ClassRoom getClassRoomById(Integer id) {
        return Assembler.assembleClassRoom(jdbcTemplate.queryForRowSet(DBConstants.SQL_GET_CLASSROOM_BY_ID, id));
    }

    @Override
    public ClassRoom createClassRoom(ClassRoom classRoom) {
        return Assembler.assembleClassRoom(jdbcTemplate.queryForRowSet(DBConstants.SQL_CREATE_CLASSROOM,
                0, classRoom.getName()));
    }

    @Override
    public ClassRoom deleteClassRoomById(Integer id) {
        return Assembler.assembleClassRoom(jdbcTemplate.queryForRowSet(DBConstants.SQL_DELETE_CLASSROOM_BY_ID, id));
    }

    @Override
    public ClassRoom updateClassRoom(ClassRoom classRoom) {
        return Assembler.assembleClassRoom(jdbcTemplate.queryForRowSet(DBConstants.SQL_UPDATE_CLASSROOM,
                classRoom.getName(), classRoom.getId()));
    }

}
