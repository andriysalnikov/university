package ua.com.foxminded.andriysalnikov.university.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClassRoomCreateDTO {

    @NotBlank(message = "Classroom Name cannot be blank")
    @Size(max = 5, message = "Classroom Name length must be no longer than 5 symbols")
    private final String name;

    @JsonCreator
    public ClassRoomCreateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClassRoomCreateDTO{" +
                "name='" + name + '\'' +
                '}';
    }

}
