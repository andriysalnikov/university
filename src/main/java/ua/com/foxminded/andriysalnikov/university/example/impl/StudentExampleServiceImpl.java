package ua.com.foxminded.andriysalnikov.university.example.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.example.StudentExampleRepository;
import ua.com.foxminded.andriysalnikov.university.example.StudentIdAndLastNameProjection;
import ua.com.foxminded.andriysalnikov.university.example.StudentExampleService;
import ua.com.foxminded.andriysalnikov.university.model.Student;

@Service
public class StudentExampleServiceImpl implements StudentExampleService {

    private final StudentExampleRepository studentExampleRepository;

    @Autowired
    public StudentExampleServiceImpl(StudentExampleRepository studentExampleRepository) {
        this.studentExampleRepository = studentExampleRepository;
    }

    @Override
    public StudentIdAndLastNameProjection getStudentById(Integer id) {
        return studentExampleRepository.getStudentById(id);
    }

    @Override
    public Student getStudentByIdWithQuery(Integer id) {
        return studentExampleRepository.getStudentByIdWithQuery(3);
    }
}

