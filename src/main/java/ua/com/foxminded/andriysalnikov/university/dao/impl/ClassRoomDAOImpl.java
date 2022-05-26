package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.dao.ClassRoomDAO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import javax.sql.DataSource;
import java.util.List;

import static ua.com.foxminded.andriysalnikov.university.constants.DBConstants.*;

@Repository
public class ClassRoomDAOImpl implements ClassRoomDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassRoomDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ClassRoom> getAllClassRooms() {
        return jdbcTemplate.query(SQL_GET_ALL_CLASSROOMS, new BeanPropertyRowMapper<>(ClassRoom.class));
    }

    @Override
    public ClassRoom getClassRoomById(Integer id) {
        return assembleClassRoom(jdbcTemplate.queryForRowSet(SQL_GET_CLASSROOM_BY_ID, id));
    }

    @Override
    public ClassRoom createClassRoom(ClassRoom classRoom) {
        return assembleClassRoom(jdbcTemplate.queryForRowSet(SQL_CREATE_CLASSROOM,
                0, classRoom.getName()));
    }

    @Override
    public ClassRoom deleteClassRoomById(Integer id) {
        return assembleClassRoom(jdbcTemplate.queryForRowSet(SQL_DELETE_CLASSROOM_BY_ID, id));
    }

    @Override
    public ClassRoom updateClassRoom(ClassRoom classRoom) {
        return assembleClassRoom(jdbcTemplate.queryForRowSet(SQL_UPDATE_CLASSROOM,
                classRoom.getName(), classRoom.getId()));
    }

    private ClassRoom assembleClassRoom(SqlRowSet resultSet) {
        ClassRoom classRoom = null;
        if (resultSet.next()) {
            classRoom = new ClassRoom(resultSet.getInt("id"), resultSet.getString("name"));
        }
        return classRoom;
    }

}
