package ua.com.foxminded.andriysalnikov.university.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TimeTable {

    private List<Event> events = new ArrayList<>();

}
