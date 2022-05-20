package ua.com.foxminded.andriysalnikov.university.domain.model;

import java.util.ArrayList;
import java.util.List;

public class TimeTable {

    private List<Event> events = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = new ArrayList<>(events);
    }

}
