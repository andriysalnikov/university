package ua.com.foxminded.andriysalnikov.university.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeTable {

    private List<Event> events;

    public TimeTable() {
        this.events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = new ArrayList<>(events);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeTable timeTable = (TimeTable) o;
        return events.equals(timeTable.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events);
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "events=" + events +
                '}';
    }

}
