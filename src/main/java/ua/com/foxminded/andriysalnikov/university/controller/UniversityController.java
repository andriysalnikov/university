package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.exceptions.TimeTableManagerException;
import ua.com.foxminded.andriysalnikov.university.utils.ExceptionUtil;
import ua.com.foxminded.andriysalnikov.university.model.User;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;
import ua.com.foxminded.andriysalnikov.university.service.TimeTableManager;

import java.time.LocalDate;

@Controller
public class UniversityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityController.class);

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final TimeTableManager timeTableManager;

    @Autowired
    public UniversityController (StudentService studentService, TeacherService teacherService,
                                 TimeTableManager timeTableManager) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.timeTableManager = timeTableManager;
    }

    @GetMapping("/")
    public String universityStartPage() {
        return "university";
    }

    @GetMapping("/timetable")
    public String showTimeTable(@RequestParam("user_type") String userType,
                                @RequestParam("id") Integer userId,
                                @RequestParam("start_date") String startDate,
                                @RequestParam("end_date") String endDate,
                                Model model) {
        User user;
        try {
            user = getUserById(userType, userId);
        } catch (ServiceException exception) {
            LOGGER.error(exception.getMessage());
            model.addAttribute("error_message", exception.getMessage());
            return "error";
        }
        LOGGER.info(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER,
                startDate, endDate, user);
        TimeTable timeTable;
        try {
            timeTable = timeTableManager.getTimeTableFromStartDateToEndDateByUser(
                    LocalDate.parse(startDate), LocalDate.parse(endDate), user);
        } catch (TimeTableManagerException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_USER,
                startDate, endDate, user, timeTable);
        model.addAttribute("user_type", userType);
        model.addAttribute("user", user);
        model.addAttribute("start_date", startDate);
        model.addAttribute("end_date", endDate);
        model.addAttribute("timetable", timeTable);
        return "timetable";
    }

    private User getUserById(String userType, Integer userId) {
        User user = null;
        if (userType.equals("Teacher")) {
            user = teacherService.getTeacherById(userId);
        }
        if (userType.equals("Student")) {
            user = studentService.getStudentById(userId);
        }
        return user;
    }

}


