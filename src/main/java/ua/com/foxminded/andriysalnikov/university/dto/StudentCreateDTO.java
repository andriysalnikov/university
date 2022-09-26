package ua.com.foxminded.andriysalnikov.university.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class StudentCreateDTO {

    @NotBlank(message = "First Name cannot be blank")
    @Size(max = 20, message = "First Name length must be no longer than 20 symbols")
    private final String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Size(max = 20, message = "Last Name length must be no longer than 20 symbols")
    private final String lastName;

    public StudentCreateDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "StudentCreateDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
