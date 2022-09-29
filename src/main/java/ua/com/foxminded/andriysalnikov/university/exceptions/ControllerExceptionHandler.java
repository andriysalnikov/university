package ua.com.foxminded.andriysalnikov.university.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(
            ServiceException serviceException, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                serviceException.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private static class ErrorMessage {
        private final int statusCode;
        private final LocalDateTime timestamp;
        private final String message;
        private final String description;
        public ErrorMessage(int statusCode, LocalDateTime timestamp, String message, String description) {
            this.statusCode = statusCode;
            this.timestamp = timestamp;
            this.message = message;
            this.description = description;
        }
        public int getStatusCode() {
            return statusCode;
        }
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        public String getMessage() {
            return message;
        }
        public String getDescription() {
            return description;
        }
    }

}