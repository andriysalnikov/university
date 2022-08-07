package ua.com.foxminded.andriysalnikov.university.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassRoomMapper implements RowMapper<ClassRoom> {
    @Override
    public ClassRoom mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(rs.getInt("id"));
        classRoom.setName(rs.getString("name"));
        return classRoom;
    }
}
