package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.EventDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.service.EventService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventDAO eventDAO;

    @Autowired
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public List<Event> getAllEvents() {
        LOGGER.debug(Messages.TRY_GET_ALL_EVENTS);
        List<Event> events = eventDAO.getAllEvents();
        LOGGER.debug(Messages.OK_GET_ALL_EVENTS, events);
        return events;
    }

    @Override
    public Event getEventById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_EVENT_BY_ID, id);
        Validation.validateId(id);
        Event event = eventDAO.getEventById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_EVENT_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_EVENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_EVENT_BY_ID, id, event);
        return event;
    }

    @Override
    public Event createEvent(Event event) {
        LOGGER.debug(Messages.TRY_CREATE_EVENT);
        Validation.validateEvent(event);
        Event createdEvent = eventDAO.createEvent(event).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_CREATE_EVENT);
            throw new ServiceException(Messages.ERROR_CREATE_EVENT);
        });
        LOGGER.debug(Messages.OK_CREATE_EVENT, createdEvent);
        return createdEvent;
    }

    @Override
    public Event deleteEventById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_EVENT_BY_ID, id);
        Validation.validateId(id);
        Event deletedEvent = eventDAO.deleteEventById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_DELETE_EVENT_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_EVENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_DELETE_EVENT_BY_ID, id, deletedEvent);
        return deletedEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        LOGGER.debug(Messages.TRY_UPDATE_EVENT, event);
        Validation.validateEvent(event);
        Event updatedEvent = eventDAO.updateEvent(event).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_UPDATE_EVENT);
            throw new ServiceException(Messages.ERROR_UPDATE_EVENT);
        });
        LOGGER.debug(Messages.OK_UPDATE_EVENT, updatedEvent);
        return updatedEvent;
    }

    @Override
    public List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer id) {
        LOGGER.debug(Messages.TRY_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                startDate, endDate, id);
        Validation.validateId(id);
        Validation.validateDate(startDate, endDate);
        List<Event> events = eventDAO.getAllEventsFromStartDateToEndDateByCourseId(startDate, endDate, id);
        LOGGER.debug(Messages.OK_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                startDate, endDate, id, events);
        return events;
    }

}
