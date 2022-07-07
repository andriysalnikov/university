package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.assembler.Assembler;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.mapper.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;

    @Autowired
    public StudentDAOImpl(DataSource dataSource, CourseMapper courseMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Student> getAllStudents() {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_STUDENTS, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public Student getStudentById(Integer id) {
        return Assembler.assembleStudent(jdbcTemplate.queryForRowSet(DBConstants.SQL_GET_STUDENT_BY_ID, id));
    }

    @Override
    public Student createStudent(Student student) {
        return Assembler.assembleStudent(jdbcTemplate.queryForRowSet(DBConstants.SQL_CREATE_STUDENT,
                0, student.getFirstName(), student.getLastName()));
    }

    @Override
    public Student deleteStudentById(Integer id) {
        return Assembler.assembleStudent(jdbcTemplate.queryForRowSet(DBConstants.SQL_DELETE_STUDENT_BY_ID, id));
    }

    @Override
    public Student updateStudent(Student student) {
        return Assembler.assembleStudent(jdbcTemplate.queryForRowSet(DBConstants.SQL_UPDATE_STUDENT,
                student.getFirstName(), student.getLastName(), student.getId()));
    }

    @Override
    public List<Course> getStudentCoursesByStudentId(Integer id) {
        return jdbcTemplate.query(DBConstants.SQL_GET_STUDENT_COURSES_BY_STUDENT_ID,
                courseMapper, id);
    }

}
