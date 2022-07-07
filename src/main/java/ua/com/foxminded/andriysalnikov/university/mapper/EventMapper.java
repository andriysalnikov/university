package ua.com.foxminded.andriysalnikov.university.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class EventMapper implements RowMapper<Event> {
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