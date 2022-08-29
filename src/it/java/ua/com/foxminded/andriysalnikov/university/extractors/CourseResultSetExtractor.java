package ua.com.foxminded.andriysalnikov.university.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseResultSetExtractor implements ResultSetExtractor<Course> {
    @Override
    public Course extractData(ResultSet rs) throws SQLException, DataAccessException {
        Course course = null;
        if (rs.next()) {
            course = new Course(rs.getString("name"), rs.getString("description"));
            course.setId(rs.getInt("id"));
        }
        return course;
    }
}
