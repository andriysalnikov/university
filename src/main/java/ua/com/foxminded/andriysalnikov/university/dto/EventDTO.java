package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventDTO {

    private final String id;
    private final String dayOfEvent;
    private final String startTime;
    private final String endTime;
    private final String classRoom;
    private final String course;

}
