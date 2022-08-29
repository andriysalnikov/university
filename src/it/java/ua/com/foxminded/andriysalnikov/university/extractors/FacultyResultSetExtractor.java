package ua.com.foxminded.andriysalnikov.university.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FacultyResultSetExtractor implements ResultSetExtractor<Faculty> {
    @Override
    public Faculty extractData(ResultSet rs) throws SQLException, DataAccessException {
        Faculty faculty = new Faculty();
        if (rs.next()) {
            faculty = new Faculty(rs.getString("full_name"));
            faculty.setId(rs.getInt("id"));
        }
        return faculty;
    }
}
