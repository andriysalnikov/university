package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import javax.sql.DataSource;
import java.util.List;

import static ua.com.foxminded.andriysalnikov.university.constants.DBConstants.*;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Student> getAllStudents() {
        return jdbcTemplate.query(SQL_GET_ALL_STUDENTS, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public Student getStudentById(Integer id) {
        return assembleStudent(jdbcTemplate.queryForRowSet(SQL_GET_STUDENT_BY_ID, id));
    }

    @Override
    public Student createStudent(Student student) {
        return assembleStudent(jdbcTemplate.queryForRowSet(SQL_CREATE_STUDENT,
                0, student.getFirstName(), student.getLastName()));
    }

    @Override
    public Student deleteStudentById(Integer id) {
        return assembleStudent(jdbcTemplate.queryForRowSet(SQL_DELETE_STUDENT_BY_ID, id));
    }

    @Override
    public Student updateStudent(Student student) {
        return assembleStudent(jdbcTemplate.queryForRowSet(SQL_UPDATE_STUDENT,
                student.getFirstName(), student.getLastName(), student.getId()));
    }

    private Student assembleStudent(SqlRowSet resultSet) {
        Student student = new Student();
        if (resultSet.next()) {
            student = new Student(resultSet.getInt("id"),
                    resultSet.getString("first_name"), resultSet.getString("last_name"));
        }
        return student;
    }

}
