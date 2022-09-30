package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class FacultyDTOWithStudents {

    private final String id;
    private final String fullName;
    private final List<String> students = new ArrayList<>();

}
