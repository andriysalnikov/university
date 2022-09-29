package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StudentDTO {

    private final String id;
    private final String firstName;
    private final String lastName;

}
