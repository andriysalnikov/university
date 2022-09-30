package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class EventCreateDTO {

    @NotBlank(message = "Day of Event cannot be Blank")
    private final String dayOfEvent;

    @NotBlank(message = "Start Time of Event cannot be Blank")
    private final String startTime;

    @NotBlank(message = "End Time of Event cannot be Blank")
    private final String endTime;

}
