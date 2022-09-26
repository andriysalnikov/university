package ua.com.foxminded.andriysalnikov.university.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import ua.com.foxminded.andriysalnikov.university.validation.FacultyFullNameConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FacultyCreateDTO {

    @NotBlank(message = "Faculty Full Name cannot be blank")
    @Size(max = 100, message = "Faculty Full Name length must be no longer than 100 symbols")
    @FacultyFullNameConstraint
    private final String fullName;

    @JsonCreator
    public FacultyCreateDTO(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "FacultyCreateDTO{" +
                "fullName='" + fullName + '\'' +
                '}';
    }

}
