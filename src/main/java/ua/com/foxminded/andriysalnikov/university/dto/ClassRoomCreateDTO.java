package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClassRoomCreateDTO {

    @NotBlank(message = "Classroom Name cannot be blank")
    @Size(max = 5, message = "Classroom Name length must be no longer than 5 symbols")
    private String name;

}
