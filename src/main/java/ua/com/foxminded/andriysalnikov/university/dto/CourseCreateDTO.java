package ua.com.foxminded.andriysalnikov.university.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CourseCreateDTO {

    @NotBlank(message = "Course Name cannot be blank")
    @Size(max = 20, message = "Course Name length must be no longer than 20 symbols")
    private final String name;

    // Description can be Blank
    @Size(max = 100, message = "Course Description length must be no longer than 100 symbols")
    private final String description;

    public CourseCreateDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CourseCreateDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
