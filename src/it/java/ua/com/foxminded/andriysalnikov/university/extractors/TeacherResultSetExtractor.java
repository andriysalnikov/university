package ua.com.foxminded.andriysalnikov.university.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TeacherResultSetExtractor implements ResultSetExtractor<Teacher> {
    @Override
    public Teacher extractData(ResultSet rs) throws SQLException, DataAccessException {
        Teacher teacher = null;
        if (rs.next()) {
            teacher = new Teacher(rs.getString("first_name"), rs.getString("last_name"));
            teacher.setId(rs.getInt("id"));
        }
        return teacher;
    }
}
