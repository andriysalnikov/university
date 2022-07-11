package ua.com.foxminded.andriysalnikov.university.exceptions;

public class DBException extends RuntimeException {

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

}
