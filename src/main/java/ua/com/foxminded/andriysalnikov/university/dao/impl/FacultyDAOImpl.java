package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.FacultyDAO;
import ua.com.foxminded.andriysalnikov.university.mappers.ClassRoomMapper;
import ua.com.foxminded.andriysalnikov.university.mappers.CourseMapper;
import ua.com.foxminded.andriysalnikov.university.extractors.FacultyResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.mappers.StudentMapper;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public class FacultyDAOImpl implements FacultyDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;
    private final ClassRoomMapper classRoomMapper;
    private final StudentMapper studentMapper;
    private final FacultyResultSetExtractor facultyResultSetExtractor;

    @Autowired
    public FacultyDAOImpl(JdbcTemplate jdbcTemplate, CourseMapper courseMapper,
                          ClassRoomMapper classRoomMapper, StudentMapper studentMapper,
                          FacultyResultSetExtractor facultyResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseMapper = courseMapper;
        this.classRoomMapper = classRoomMapper;
        this.studentMapper = studentMapper;
        this.facultyResultSetExtractor = facultyResultSetExtractor;
    }

    @Override
    public List<Faculty> getAllFaculties() {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_FACULTIES, new BeanPropertyRowMapper<>(Faculty.class));
    }

    @Override
    public Optional<Faculty> getFacultyById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_GET_FACULTY_BY_ID,
                facultyResultSetExtractor, id));
    }

    @Override
    public Optional<Faculty> createFaculty(Faculty faculty) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_CREATE_FACULTY,
                facultyResultSetExtractor, faculty.getFullName()));
    }

    @Override
    public Optional<Faculty> deleteFacultyById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_DELETE_FACULTY_BY_ID,
                facultyResultSetExtractor, id));
    }

    @Override
    public Optional<Faculty> updateFaculty(Faculty faculty) {
        return Optional.ofNullable(jdbcTemplate.query(DBConstants.SQL_UPDATE_FACULTY,
            facultyResultSetExtractor, faculty.getFullName(), faculty.getId()));
    }

    @Override
    public List<Course> getFacultyCoursesByFacultyId(Integer id) {
        return jdbcTemplate.query(DBConstants.SQL_GET_FACULTY_COURSES_BY_FACULTY_ID,
                courseMapper, id);
    }

    @Override
    public List<ClassRoom> getFacultyClassRoomsByFacultyId(Integer id) {
        return jdbcTemplate.query(DBConstants.SQL_GET_FACULTY_CLASSROOMS_BY_FACULTY_ID,
                classRoomMapper, id);
    }

    @Override
    public List<Student> getFacultyStudentsByFacultyId(Integer id) {
        return jdbcTemplate.query(DBConstants.SQL_GET_FACULTY_STUDENTS_BY_FACULTY_ID,
                studentMapper, id);
    }

}
