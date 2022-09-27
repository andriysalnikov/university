package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class EventCreateDTO {

    @NotBlank(message = "Day of Event cannot be Blank")
    private final String dayOfEvent;

    @NotBlank(message = "Start Time of Event cannot be Blank")
    private final String startTime;

    @NotBlank(message = "End Time of Event cannot be Blank")
    private final String endTime;

}
