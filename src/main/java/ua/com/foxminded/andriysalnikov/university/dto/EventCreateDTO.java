package ua.com.foxminded.andriysalnikov.university.dto;

import javax.validation.constraints.NotBlank;

public class EventCreateDTO {

    @NotBlank(message = "Day of Event cannot be Blank")
    private final String dayOfEvent;

    @NotBlank(message = "Start Time of Event cannot be Blank")
    private final String startTime;

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
