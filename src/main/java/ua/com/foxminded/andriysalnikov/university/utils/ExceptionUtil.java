package ua.com.foxminded.andriysalnikov.university.utils;

import org.slf4j.Logger;
import org.springframework.ui.Model;

public class ExceptionUtil {

    public static String handleException(RuntimeException exception, Logger logger, Model model) {
        logger.error(exception.getMessage());
        model.addAttribute("error_message", exception.getMessage());
        return "error";
    }

    private ExceptionUtil() {}

}
