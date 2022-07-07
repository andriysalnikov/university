package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.EventDAO;
import ua.com.foxminded.andriysalnikov.university.mapper.EventMapper;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Repository
public class EventDAOImpl implements EventDAO {

    private final JdbcTemplate jdbcTemplate;
    private final EventMapper eventMapper;

    @Autowired
    public EventDAOImpl(DataSource dataSource, EventMapper eventMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.eventMapper = eventMapper;
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

}
