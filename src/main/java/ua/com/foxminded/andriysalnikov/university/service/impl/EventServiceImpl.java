package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.dao.EventDAO;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.service.EventService;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventDAO eventDAO;

    @Autowired
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    @Override
    public List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("'id' cannot be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("'id' cannot be less or equals 0");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("'StartDate' or 'EndDate' or both cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("'StartDate' cannot be after 'EndDate'");
        }
        return eventDAO.getAllEventsFromStartDateToEndDateByCourseId(startDate, endDate, id);
    }

}
