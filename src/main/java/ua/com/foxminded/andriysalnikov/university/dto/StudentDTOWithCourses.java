package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class StudentDTOWithCourses {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final List<String> courses = new ArrayList<>();

}
