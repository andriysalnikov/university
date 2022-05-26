package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.dao.EventDAO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import static ua.com.foxminded.andriysalnikov.university.constants.DBConstants.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class EventDAOImpl implements EventDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Event> getAllEvents() {
        return jdbcTemplate.query(SQL_GET_ALL_EVENTS, new EventRowMapper());
    }

    private static class EventRowMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setId(rs.getInt("id"));
            event.setDayOfEvent(rs.getObject("date_of_event", LocalDate.class));
            event.setStartTime(rs.getObject("start_time", LocalTime.class));
            event.setEndTime(rs.getObject("end_time", LocalTime.class));
            event.setClassRoom(new ClassRoom(0, rs.getString("classroom")));
            event.setCourse(new Course(0, rs.getString("course"), ""));
            return event;
        }
    }



}
