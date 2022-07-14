package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.assembler.Assembler;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.TeacherDAO;
import ua.com.foxminded.andriysalnikov.university.mapper.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherDAOImpl implements TeacherDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;

    @Autowired
    public TeacherDAOImpl(DataSource dataSource, CourseMapper courseMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_TEACHERS, new BeanPropertyRowMapper<>(Teacher.class));
    }

    @Override
    public Optional<Teacher> getTeacherById(Integer id) {
        return Optional.ofNullable(Assembler.assembleTeacher(
                jdbcTemplate.queryForRowSet(DBConstants.SQL_GET_TEACHER_BY_ID, id)));
    }

    @Override
    public Optional<Teacher> createTeacher(Teacher teacher) {
        return Optional.ofNullable(Assembler.assembleTeacher(
                jdbcTemplate.queryForRowSet(DBConstants.SQL_CREATE_TEACHER,
                        teacher.getFirstName(), teacher.getLastName())));
    }

    @Override
    public Optional<Teacher> deleteTeacherById(Integer id) {
        return Optional.ofNullable(Assembler.assembleTeacher(
                jdbcTemplate.queryForRowSet(DBConstants.SQL_DELETE_TEACHER_BY_ID, id)));
    }

    @Override
    public Optional<Teacher> updateTeacher(Teacher teacher) {
        return Optional.ofNullable(Assembler.assembleTeacher(
                jdbcTemplate.queryForRowSet(DBConstants.SQL_UPDATE_TEACHER,
                        teacher.getFirstName(), teacher.getLastName(), teacher.getId())));
    }

    @Override
    public List<Course> getTeacherCoursesByTeacherId(Integer id) {
        return jdbcTemplate.query(DBConstants.SQL_GET_TEACHER_COURSES_BY_TEACHER_ID,
                courseMapper, id);
    }

}
