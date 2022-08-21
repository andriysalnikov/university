package ua.com.foxminded.andriysalnikov.university.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.andriysalnikov.university.constants.DBConstants;
import ua.com.foxminded.andriysalnikov.university.dao.EventDAO;
import ua.com.foxminded.andriysalnikov.university.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class EventDAOImpl implements EventDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public EventDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Event> getAllEvents() {
        return sessionFactory.getCurrentSession()
                .createQuery(DBConstants.HQL_GET_ALL_EVENTS, Event.class)
                .getResultList();
    }

    @Override
    public List<Event> getAllEventsFromStartDateToEndDateByCourseId(
            LocalDate startDate, LocalDate endDate, Integer id) {
        return sessionFactory.getCurrentSession()
                .createQuery(DBConstants.HQL_GET_ALL_EVENTS_FROM_STARTDATE_TO_ENDDATE_BY_COURSE_ID, Event.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("courseId", id)
                .getResultList();
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(Event.class, id));
    }

    @Override
    public Optional<Event> createEvent(Event event) {
        sessionFactory.getCurrentSession().persist(event);
        return Optional.ofNullable(event);
    }

    @Override
    public Optional<Event> deleteEventById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Event event = session.find(Event.class, id);
        session.remove(event);
        return Optional.ofNullable(event);
    }

    @Override
    public Optional<Event> updateEvent(Event event) {
        sessionFactory.getCurrentSession().merge(event);
        return Optional.ofNullable(event);
    }

}
