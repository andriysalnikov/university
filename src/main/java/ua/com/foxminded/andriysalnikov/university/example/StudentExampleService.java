package ua.com.foxminded.andriysalnikov.university.example;

import ua.com.foxminded.andriysalnikov.university.model.Student;

public interface StudentExampleService {

    StudentIdAndLastName getStudentById(Integer id);

    Student getStudentByIdWithQuery(Integer id);

}
