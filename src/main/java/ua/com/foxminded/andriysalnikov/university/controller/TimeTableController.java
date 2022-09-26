package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.TimeTableDTO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.mapper.TimeTableMapper;
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;
import ua.com.foxminded.andriysalnikov.university.service.TimeTableManager;

import java.time.LocalDate;

@RestController
@RequestMapping("/timetable")
public class TimeTableController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTableController.class);

    private final TimeTableManager timeTableManager;
    private final TimeTableMapper timeTableMapper;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public TimeTableController(TimeTableManager timeTableManager, TimeTableMapper timeTableMapper,
                               StudentService studentService, TeacherService teacherService) {
        this.timeTableManager = timeTableManager;
        this.timeTableMapper = timeTableMapper;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @GetMapping("/teacher/{id}/start-date/{startDate}/end-date/{endDate}")
    public ResponseEntity<TimeTableDTO> showTeacherTimeTable(@PathVariable Integer id,
                @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Teacher teacher;
        try {
            teacher = teacherService.getTeacherByIdWithCourses(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_TEACHER,
                startDate, endDate);
        TimeTable timeTable;
        try {
            timeTable = timeTableManager.getTimeTableFromStartDateToEndDateByTeacher(
                    startDate, endDate, teacher);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_TEACHER,
                startDate, endDate, timeTable);
        return new ResponseEntity<>(timeTableMapper.toDTO(timeTable), HttpStatus.OK);
    }

    @GetMapping("/student/{id}/start-date/{startDate}/end-date/{endDate}")
    public ResponseEntity<TimeTableDTO> showStudentTimeTable(@PathVariable Integer id,
                @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Student student;
        try {
            student = studentService.getStudentByIdWithCourses(id);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_STUDENT,
                startDate, endDate);
        TimeTable timeTable;
        try {
            timeTable = timeTableManager.getTimeTableFromStartDateToEndDateByStudent(
                    startDate, endDate, student);
        } catch (ServiceException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        LOGGER.info(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_STUDENT,
                startDate, endDate, timeTable);
        return new ResponseEntity<>(timeTableMapper.toDTO(timeTable), HttpStatus.OK);
    }

}


