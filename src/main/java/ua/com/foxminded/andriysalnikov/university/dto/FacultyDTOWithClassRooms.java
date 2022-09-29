package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class FacultyDTOWithClassRooms {

    private final String id;
    private final String fullName;
    private final List<String> classRooms = new ArrayList<>();

}
