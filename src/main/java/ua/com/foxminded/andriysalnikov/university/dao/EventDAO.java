package ua.com.foxminded.andriysalnikov.university.dao;

import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventDAO {

    List<Event> getAllEvents();
    Optional<Event> getEventById(Integer id);
    Optional<Event> createEvent(Event event);
    Optional<Event> deleteEventById(Integer id);
    Optional<Event> updateEvent(Event event);
    List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer id);

}
