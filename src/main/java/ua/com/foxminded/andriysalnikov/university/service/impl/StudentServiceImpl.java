package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.dao.StudentDAO;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public Student getStudentById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("'id' cannot be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("'id' cannot be less or equals 0");
        }
        return studentDAO.getStudentById(id);
    }

    @Override
    public List<Course> getStudentCoursesByStudentId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("'id' cannot be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("'id' cannot be less or equals 0");
        }
        return studentDAO.getStudentCoursesByStudentId(id);
    }

}
