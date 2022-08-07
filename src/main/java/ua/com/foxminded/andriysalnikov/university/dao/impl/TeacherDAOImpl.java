package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.TeacherDAO;
import ua.com.foxminded.andriysalnikov.university.mappers.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.extractors.TeacherResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;

import java.util.List;
import java.util.Optional;

@Repository
public class TeacherDAOImpl implements TeacherDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;
    private final TeacherResultSetExtractor teacherResultSetExtractor;

    @Autowired
    public TeacherDAOImpl(JdbcTemplate jdbcTemplate, CourseMapper courseMapper,
                          TeacherResultSetExtractor teacherResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseMapper = courseMapper;
        this.teacherResultSetExtractor = teacherResultSetExtractor;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_TEACHERS, new BeanPropertyRowMapper<>(Teacher.class));
    }

    @Override
    public Optional<Teacher> getTeacherById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_GET_TEACHER_BY_ID,
                teacherResultSetExtractor, id));
    }

    @Override
    public Optional<Teacher> createTeacher(Teacher teacher) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_CREATE_TEACHER,
            teacherResultSetExtractor, teacher.getFirstName(), teacher.getLastName()));
    }

    @Override
    public Optional<Teacher> deleteTeacherById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_DELETE_TEACHER_BY_ID,
                teacherResultSetExtractor, id));
    }

    @Override
    public Optional<Teacher> updateTeacher(Teacher teacher) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_UPDATE_TEACHER,
                teacherResultSetExtractor, teacher.getFirstName(), teacher.getLastName(), teacher.getId()));
    }

    @Override
    public List<Course> getTeacherCoursesByTeacherId(Integer id) {
        return jdbcTemplate.query(DBConstants.SQL_GET_TEACHER_COURSES_BY_TEACHER_ID,
                courseMapper, id);
    }

}
