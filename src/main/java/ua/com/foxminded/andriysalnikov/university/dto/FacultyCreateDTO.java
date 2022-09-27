package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import ua.com.foxminded.andriysalnikov.university.validation.FacultyFullNameConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FacultyCreateDTO {

    @NotBlank(message = "Faculty Full Name cannot be blank")
    @Size(max = 100, message = "Faculty Full Name length must be no longer than 100 symbols")
    @FacultyFullNameConstraint
    private String fullName;

}
