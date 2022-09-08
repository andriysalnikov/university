package ua.com.foxminded.andriysalnikov.university.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;

import java.time.LocalDate;

public class Validation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validation.class);

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

    private Validation() {}

}
