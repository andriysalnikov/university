package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.util.List;

public interface StudentDAO {

    List<Student> getAllStudents();

    Student getStudentById(Integer id);

    Student createStudent(Student student);

    Student deleteStudentById(Integer id);

    Student updateStudent(Student student);

    List<Course> getStudentCoursesByStudentId(Integer id);

}
