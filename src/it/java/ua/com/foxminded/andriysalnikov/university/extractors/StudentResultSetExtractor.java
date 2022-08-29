package ua.com.foxminded.andriysalnikov.university.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentResultSetExtractor implements ResultSetExtractor<Student> {
    @Override
    public Student extractData(ResultSet rs) throws SQLException, DataAccessException {
        Student student = null;
        if (rs.next()) {
            student = new Student(rs.getString("first_name"), rs.getString("last_name"));
            student.setId(rs.getInt("id"));
        }
        return student;
    }
}
