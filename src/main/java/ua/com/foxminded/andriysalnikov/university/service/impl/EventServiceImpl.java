package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.repository.EventRepository;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.service.EventService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        LOGGER.debug(Messages.TRY_GET_ALL_EVENTS);
        List<Event> events = eventRepository.getAllEvents();
        LOGGER.debug(Messages.OK_GET_ALL_EVENTS, events);
        return events;
    }

    @Override
    public Event getEventById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_EVENT_BY_ID, id);
        Event event = eventRepository.findEventById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_EVENT_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_EVENT_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_EVENT_BY_ID, id, event);
        return event;
    }

    @Modifying
    @Transactional
    @Override
    public Event createEvent(Event event) {
        LOGGER.debug(Messages.TRY_CREATE_EVENT);
        Event createdEvent;
        try {
            createdEvent = eventRepository.save(event);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_CREATE_EVENT_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_CREATE_EVENT, exception);
        }
        LOGGER.debug(Messages.OK_CREATE_EVENT, createdEvent);
        return createdEvent;
    }

    @Modifying
    @Transactional
    @Override
    public void deleteEventById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_EVENT_BY_ID, id);
        if (eventRepository.deleteEventById(id) == 0) {
            LOGGER.error(Messages.ERROR_DELETE_EVENT_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_EVENT_BY_ID);
        }
        LOGGER.debug(Messages.OK_DELETE_EVENT_BY_ID, id);
    }

    @Modifying
    @Transactional
    @Override
    public Event updateEvent(Event event) {
        LOGGER.debug(Messages.TRY_UPDATE_EVENT, event);
        Event updatedEvent;
        try {
            updatedEvent = eventRepository.save(event);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_UPDATE_EVENT_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_UPDATE_EVENT, exception);
        }
        LOGGER.debug(Messages.OK_UPDATE_EVENT, updatedEvent);
        return updatedEvent;
    }

    @Override
    public List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer courseId) {
        LOGGER.debug(Messages.TRY_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                startDate, endDate, courseId);
        Validation.validateDate(startDate, endDate);
        List<Event> events = eventRepository.getAllEventsFromStartDateToEndDateByCourseId(
                        startDate, endDate, courseId);
        LOGGER.debug(Messages.OK_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                startDate, endDate, courseId, events);
        return events;
    }

}
