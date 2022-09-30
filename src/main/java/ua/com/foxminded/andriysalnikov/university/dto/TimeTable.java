package ua.com.foxminded.andriysalnikov.university.dto;

import lombok.Data;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.util.ArrayList;
import java.util.List;

@Data
public class TimeTable {

    private List<Event> events = new ArrayList<>();

}
