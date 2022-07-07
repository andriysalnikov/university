package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.assembler.Assembler;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.CourseDAO;
import ua.com.foxminded.andriysalnikov.university.model.Course;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CourseDAOImpl implements CourseDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Course> getAllCourses() {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_COURSES, new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public Course getCourseById(Integer id) {
        return Assembler.assembleCourse(jdbcTemplate.queryForRowSet(DBConstants.SQL_GET_COURSE_BY_ID, id));
    }

    @Override
    public Course createCourse(Course course) {
        return Assembler.assembleCourse(jdbcTemplate.queryForRowSet(DBConstants.SQL_CREATE_COURSE,
                0, 0, course.getName(), course.getDescription()));
    }

    @Override
    public Course deleteCourserById(Integer id) {
        return Assembler.assembleCourse(jdbcTemplate.queryForRowSet(DBConstants.SQL_DELETE_COURSE_BY_ID, id));
    }

    @Override
    public Course updateCourse(Course course) {
        return Assembler.assembleCourse(jdbcTemplate.queryForRowSet(DBConstants.SQL_UPDATE_COURSE,
                course.getName(), course.getDescription(), course.getId()));
    }

}
