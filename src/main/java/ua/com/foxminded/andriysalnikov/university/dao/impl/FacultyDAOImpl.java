package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.dao.FacultyDAO;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import javax.sql.DataSource;
import java.util.List;

import static ua.com.foxminded.andriysalnikov.university.constants.DBConstants.*;

@Repository
public class FacultyDAOImpl implements FacultyDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FacultyDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Faculty> getAllFaculties() {
        return jdbcTemplate.query(SQL_GET_ALL_FACULTIES, new BeanPropertyRowMapper<>(Faculty.class));
    }

    @Override
    public Faculty getFacultyById(Integer id) {
        return assembleFaculty(jdbcTemplate.queryForRowSet(SQL_GET_FACULTY_BY_ID, id));
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return assembleFaculty(jdbcTemplate.queryForRowSet(SQL_CREATE_FACULTY,
                faculty.getFullName()));
    }

    @Override
    public Faculty deleteFacultyById(Integer id) {
        return assembleFaculty(jdbcTemplate.queryForRowSet(SQL_DELETE_FACULTY_BY_ID, id));
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        return assembleFaculty(jdbcTemplate.queryForRowSet(SQL_UPDATE_FACULTY,
                faculty.getFullName(), faculty.getId()));
    }

    private Faculty assembleFaculty(SqlRowSet resultSet) {
        Faculty faculty = null;
        if (resultSet.next()) {
            faculty = new Faculty(resultSet.getInt("id"), resultSet.getString("full_name"));
        }
        return faculty;
    }

}
