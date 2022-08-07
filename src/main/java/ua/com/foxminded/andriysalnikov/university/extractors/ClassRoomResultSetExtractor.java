package ua.com.foxminded.andriysalnikov.university.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClassRoomResultSetExtractor implements ResultSetExtractor<ClassRoom> {
    public ClassRoom extractData(ResultSet rs) throws SQLException, DataAccessException {
        ClassRoom classRoom = null;
        if (rs.next()) {
            classRoom = new ClassRoom(rs.getInt("id"), rs.getString("name"));
        }
        return classRoom;
    }
}
