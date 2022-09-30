package ua.com.foxminded.andriysalnikov.university.service;

import ua.com.foxminded.andriysalnikov.university.dto.EventCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.EventDTO;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    List<Event> getAllEvents();
    List<EventDTO> getAllEventDTOs();

    Event getEventById(Integer id);
    EventDTO getEventDTOById(Integer id);

    Event createEvent(Event event);
    EventDTO createEventDTO(EventCreateDTO eventCreateDTO, Course course, ClassRoom classRoom);

    Event updateEvent(Event event);
    EventDTO updateEventDTO(Integer id, EventCreateDTO eventCreateDTO,
                            Course course, ClassRoom classRoom);

    void deleteEventById(Integer id);

    List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer id);

}
