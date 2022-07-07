package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.dao.TeacherDAO;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDAO teacherDAO;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("'id' cannot be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("'id' cannot be less or equals 0");
        }
        return teacherDAO.getTeacherById(id);
    }

    @Override
    public List<Course> getTeacherCoursesByTeacherId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("'id' cannot be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("'id' cannot be less or equals 0");
        }
        return  teacherDAO.getTeacherCoursesByTeacherId(id);
    }

}
