package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class StudentCreateDTO {

    @NotBlank(message = "First Name cannot be blank")
    @Size(max = 20, message = "First Name length must be no longer than 20 symbols")
    private final String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Size(max = 20, message = "Last Name length must be no longer than 20 symbols")
    private final String lastName;

}
