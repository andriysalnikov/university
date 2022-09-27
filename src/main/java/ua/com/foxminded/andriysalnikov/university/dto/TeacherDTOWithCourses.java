package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class TeacherDTOWithCourses {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final List<String> courses = new ArrayList<>();

}
