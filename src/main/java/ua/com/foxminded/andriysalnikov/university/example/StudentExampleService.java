package ua.com.foxminded.andriysalnikov.university.example;

import ua.com.foxminded.andriysalnikov.university.model.Student;

public interface StudentExampleService {

    StudentIdAndLastNameProjection getStudentById(Integer id);

    Student getStudentByIdWithQuery(Integer id);

}
