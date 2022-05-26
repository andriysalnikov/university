package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.dao.TeacherDAO;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import javax.sql.DataSource;
import java.util.List;

import static ua.com.foxminded.andriysalnikov.university.constants.DBConstants.*;

@Repository
public class TeacherDAOImpl implements TeacherDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return jdbcTemplate.query(SQL_GET_ALL_TEACHERS, new BeanPropertyRowMapper<>(Teacher.class));
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        return assembleTeacher(jdbcTemplate.queryForRowSet(SQL_GET_TEACHER_BY_ID, id));
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return assembleTeacher(jdbcTemplate.queryForRowSet(SQL_CREATE_TEACHER,
                teacher.getFirstName(), teacher.getLastName()));
    }

    @Override
    public Teacher deleteTeacherById(Integer id) {
        return assembleTeacher(jdbcTemplate.queryForRowSet(SQL_DELETE_TEACHER_BY_ID, id));
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        return assembleTeacher(jdbcTemplate.queryForRowSet(SQL_UPDATE_TEACHER,
                teacher.getFirstName(), teacher.getLastName(), teacher.getId()));
    }

    private Teacher assembleTeacher(SqlRowSet resultSet) {
        Teacher teacher = null;
        if (resultSet.next()) {
            teacher = new Teacher(resultSet.getInt("id"),
                    resultSet.getString("first_name"), resultSet.getString("last_name"));
        }
        return teacher;
    }

}
