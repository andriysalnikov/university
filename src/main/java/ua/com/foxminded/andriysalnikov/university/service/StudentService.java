package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.User;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();
    List<Student> getAllStudentsWithoutFaculty();
    Student getStudentById(Integer id);
    Student createStudent(User user);
    Student deleteStudentById(Integer id);
    Student updateStudent(User user);
    List<Course> getStudentCoursesByStudentId(Integer id);
    Student setFacultyToStudent(Integer facultyId, Integer studentId);
    Student removeFacultyFromStudent(Integer studentId);

}
