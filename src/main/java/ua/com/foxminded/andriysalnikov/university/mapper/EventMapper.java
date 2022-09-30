package ua.com.foxminded.andriysalnikov.university.mapper;

import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.dto.EventCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.EventDTO;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class EventMapper {

    public EventDTO toDTO(Event event) {
        return new EventDTO(event.getId().toString(), event.getDayOfEvent().toString(),
                event.getStartTime().toString(), event.getEndTime().toString(),
                event.getClassRoom() != null ? event.getClassRoom().getName() : "null",
                event.getCourse().getName());
    }

    public Event fromDTO(EventCreateDTO eventCreateDTO) {
        return new Event(LocalDate.parse(eventCreateDTO.getDayOfEvent()),
                LocalTime.parse(eventCreateDTO.getStartTime()),
                        LocalTime.parse(eventCreateDTO.getEndTime()));
    }

}
