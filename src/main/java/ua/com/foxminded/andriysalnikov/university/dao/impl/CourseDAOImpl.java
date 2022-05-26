package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.dao.CourseDAO;
import ua.com.foxminded.andriysalnikov.university.model.Course;

import javax.sql.DataSource;
import java.util.List;

import static ua.com.foxminded.andriysalnikov.university.constants.DBConstants.*;

@Repository
public class CourseDAOImpl implements CourseDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Course> getAllCourses() {
        return jdbcTemplate.query(SQL_GET_ALL_COURSES, new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public Course getCourseById(Integer id) {
        return assembleCourse(jdbcTemplate.queryForRowSet(SQL_GET_COURSE_BY_ID, id));
    }

    @Override
    public Course createCourse(Course course) {
        return assembleCourse(jdbcTemplate.queryForRowSet(SQL_CREATE_COURSE,
                0, 0, course.getName(), course.getDescription()));
    }

    @Override
    public Course deleteCourserById(Integer id) {
        return assembleCourse(jdbcTemplate.queryForRowSet(SQL_DELETE_COURSE_BY_ID, id));
    }

    @Override
    public Course updateCourse(Course course) {
        return assembleCourse(jdbcTemplate.queryForRowSet(SQL_UPDATE_COURSE,
                course.getName(), course.getDescription(), course.getId()));
    }

    private Course assembleCourse(SqlRowSet resultSet) {
        Course course = null;
        if (resultSet.next()) {
            course = new Course(resultSet.getInt("id"),
                    resultSet.getString("name"), resultSet.getString("description"));
        }
        return course;
    }

}
