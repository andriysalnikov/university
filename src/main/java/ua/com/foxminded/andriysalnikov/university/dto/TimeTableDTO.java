package ua.com.foxminded.andriysalnikov.university.dto;

import java.util.List;

public class TimeTableDTO {

    private final List<EventDTO> events;

    public TimeTableDTO(List<EventDTO> events) {
        this.events = events;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return "TimeTableDTO{" +
                "events=" + events +
                '}';
    }

}
