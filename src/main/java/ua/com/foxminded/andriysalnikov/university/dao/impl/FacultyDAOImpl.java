package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.assembler.Assembler;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.FacultyDAO;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FacultyDAOImpl implements FacultyDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FacultyDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Faculty> getAllFaculties() {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_FACULTIES, new BeanPropertyRowMapper<>(Faculty.class));
    }

    @Override
    public Faculty getFacultyById(Integer id) {
        return Assembler.assembleFaculty(jdbcTemplate.queryForRowSet(DBConstants.SQL_GET_FACULTY_BY_ID, id));
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return Assembler.assembleFaculty(jdbcTemplate.queryForRowSet(DBConstants.SQL_CREATE_FACULTY,
                faculty.getFullName()));
    }

    @Override
    public Faculty deleteFacultyById(Integer id) {
        return Assembler.assembleFaculty(jdbcTemplate.queryForRowSet(DBConstants.SQL_DELETE_FACULTY_BY_ID, id));
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        return Assembler.assembleFaculty(jdbcTemplate.queryForRowSet(DBConstants.SQL_UPDATE_FACULTY,
                faculty.getFullName(), faculty.getId()));
    }

}
