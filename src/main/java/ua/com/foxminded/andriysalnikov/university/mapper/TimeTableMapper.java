package ua.com.foxminded.andriysalnikov.university.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.dto.EventDTO;
import ua.com.foxminded.andriysalnikov.university.dto.TimeTableDTO;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeTableMapper {

    private final EventMapper eventMapper;

    @Autowired
    public TimeTableMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public TimeTableDTO toDTO(TimeTable timeTable) {
        List<EventDTO> events = timeTable.getEvents()
                .stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
        return new TimeTableDTO(events);
    }

}
