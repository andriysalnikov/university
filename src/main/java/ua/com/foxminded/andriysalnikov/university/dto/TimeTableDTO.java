package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeTableDTO {

    private List<EventDTO> events;

}
