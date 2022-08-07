package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.EventDAO;
import ua.com.foxminded.andriysalnikov.university.mappers.EventMapper;
import ua.com.foxminded.andriysalnikov.university.extractors.EventResultSetExtractor;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class EventDAOImpl implements EventDAO {

    private final JdbcTemplate jdbcTemplate;
    private final EventMapper eventMapper;
    private final EventResultSetExtractor eventResultSetExtractor;

    @Autowired
    public EventDAOImpl(JdbcTemplate jdbcTemplate, EventMapper eventMapper,
                        EventResultSetExtractor eventResultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventMapper = eventMapper;
        this.eventResultSetExtractor = eventResultSetExtractor;
    }

    @Override
    public List<Event> getAllEvents() {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_EVENTS, eventMapper);
    }

    @Override
    public List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer id) {
        return jdbcTemplate.query(DBConstants.SQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID,
                eventMapper, startDate, endDate, id);
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(
                DBConstants.SQL_GET_EVENT_BY_ID, eventResultSetExtractor, id));
    }

    @Override
    public Optional<Event> createEvent(Event event) {
        return Optional.ofNullable(jdbcTemplate.query(
                DBConstants.SQL_CREATE_EVENT, eventResultSetExtractor,
                event.getDayOfEvent(), event.getStartTime(), event.getEndTime(),
                event.getClassRoom().getId(), event.getCourse().getId()));
    }

    @Override
    public Optional<Event> deleteEventById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(
                DBConstants.SQL_DELETE_EVENT_BY_ID, eventResultSetExtractor, id));
    }

    @Override
    public Optional<Event> updateEvent(Event event) {
        return Optional.ofNullable(jdbcTemplate.query(
                DBConstants.SQL_UPDATE_EVENT, eventResultSetExtractor,
                event.getDayOfEvent(), event.getStartTime(), event.getEndTime(),
                event.getClassRoom().getId(), event.getCourse().getId(),
                event.getId()));
    }

}
