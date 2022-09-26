package ua.com.foxminded.andriysalnikov.university.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EventCreateDTO {

    @NotNull(message = "Day of Event cannot be Null")
    @NotBlank(message = "Day of Event cannot be Blank")
    private final String dayOfEvent;

    @NotNull(message = "Start Time of Event cannot be Null")
    @NotBlank(message = "Start Time of Event cannot be Blank")
    private final String startTime;

    @NotNull(message = "End Time of Event cannot be Null")
    @NotBlank(message = "End Time of Event cannot be Blank")
    private final String endTime;

    public EventCreateDTO(String dayOfEvent, String startTime, String endTime) {
        this.dayOfEvent = dayOfEvent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDayOfEvent() {
        return dayOfEvent;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "EventCreateDTO{" +
                "dayOfEvent='" + dayOfEvent + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

}
