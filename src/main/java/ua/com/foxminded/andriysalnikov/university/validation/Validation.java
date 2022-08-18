package ua.com.foxminded.andriysalnikov.university.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.model.Course;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.model.Student;

import java.time.LocalDate;

public class Validation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validation.class);

    public static void validateId(Integer id) {
        if (id == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_NULL);
            throw new ServiceException(Messages.ERROR_ARGUMENT_NULL);
        }
        if (id <= 0) {
            LOGGER.error(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
            throw new ServiceException(Messages.ERROR_ARGUMENT_LESS_OR_EQUALS_ZERO);
        }
    }

    public static void validateClassRoom(ClassRoom classRoom) {
        if (classRoom == null || classRoom.getName() == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_CLASSROOM);
            throw new ServiceException(Messages.ERROR_ARGUMENT_CLASSROOM);
        }
    }

    public static void validateCourse(Course course) {
        if (course == null || course.getName() == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_COURSE);
            throw new ServiceException(Messages.ERROR_ARGUMENT_COURSE);
        }
    }

    public static void validateFaculty(Faculty faculty) {
        if (faculty == null || faculty.getFullName() == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_FACULTY);
            throw new ServiceException(Messages.ERROR_ARGUMENT_FACULTY);
        }
    }

//    public static void validateEvent(Event event) {
//        if (event == null || event.getDayOfEvent() == null
//                || event.getStartTime() == null || event.getEndTime() == null ) {
//            LOGGER.error(Messages.ERROR_ARGUMENT_EVENT);
//            throw new ServiceException(Messages.ERROR_ARGUMENT_EVENT);
//        }
//    }

    public static void validateDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            LOGGER.error(Messages.ERROR_DATE_NULL);
            throw new ServiceException(Messages.ERROR_DATE_NULL);
        }
        if (startDate.isAfter(endDate)) {
            LOGGER.error(Messages.ERROR_STARTDATE_AFTER_ENDDATE);
            throw new ServiceException(Messages.ERROR_STARTDATE_AFTER_ENDDATE);
        }
    }

    public static void validateTeacher(Teacher teacher) {
        if (teacher == null || teacher.getFirstName() == null || teacher.getLastName() == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_TEACHER);
            throw new ServiceException(Messages.ERROR_ARGUMENT_TEACHER);
        }
    }

    public static void validateStudent(Student student) {
        if (student == null || student.getFirstName() == null || student.getLastName() == null) {
            LOGGER.error(Messages.ERROR_ARGUMENT_STUDENT);
            throw new ServiceException(Messages.ERROR_ARGUMENT_STUDENT);
        }
    }

    private Validation() {}

}
