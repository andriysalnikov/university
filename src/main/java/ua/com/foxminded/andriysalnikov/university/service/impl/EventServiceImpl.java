package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.EventDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Event;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.service.EventService;

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
    public List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer id) {
        LOGGER.debug(Messages.TRY_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                startDate, endDate, id);
        if (id == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_NULL);
            throw new ServiceException(Messages.ERROR_ARGUMENT_NULL);
        }
        if (id <= 0) {
            LOGGER.error(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
            throw new ServiceException(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
        }
        if (startDate == null || endDate == null) {
            LOGGER.error(Messages.ERROR_DATE_NULL);
            throw new ServiceException(Messages.ERROR_DATE_NULL);
        }
        if (startDate.isAfter(endDate)) {
            LOGGER.error(Messages.ERROR_STARTDATE_AFTER_ENDDATE);
            throw new ServiceException(Messages.ERROR_STARTDATE_AFTER_ENDDATE);
        }
        List<Event> events = eventDAO.getAllEventsFromStartDateToEndDateByCourseId(startDate, endDate, id);
        events.sort(new EventComparatorByDateAndTime());
        LOGGER.debug(Messages.OK_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                startDate, endDate, id, events);
        return events;
    }

    private static class EventComparatorByDateAndTime implements Comparator<Event> {
        @Override
        public int compare(Event o1, Event o2) {
            if (o1.getDayOfEvent().isBefore(o2.getDayOfEvent())) {
                return -1;
            } else if (o1.getDayOfEvent().isAfter(o2.getDayOfEvent())) {
                return 1;
            } else {
                if (o1.getStartTime().isBefore(o2.getStartTime())) {
                    return -1;
                } else if (o1.getStartTime().isAfter(o2.getStartTime())) {
                    return 1;
                }
            }
            return 0;
        }
    }

}
