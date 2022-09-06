package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    List<Event> getAllEvents();
    Event getEventById(Integer id);
    Event createEvent(Event event);
    void deleteEventById(Integer id);
    Event updateEvent(Event event);
    List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer id);

}
