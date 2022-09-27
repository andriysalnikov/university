package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CourseCreateDTO {

    @NotBlank(message = "Course Name cannot be blank")
    @Size(max = 20, message = "Course Name length must be no longer than 20 symbols")
    private final String name;

    // Description can be Blank
    @Size(max = 100, message = "Course Description length must be no longer than 100 symbols")
    private final String description;

}
