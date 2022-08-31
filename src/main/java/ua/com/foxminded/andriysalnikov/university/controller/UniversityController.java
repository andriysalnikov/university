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
import ua.com.foxminded.andriysalnikov.university.model.Student;
import ua.com.foxminded.andriysalnikov.university.model.Teacher;
import ua.com.foxminded.andriysalnikov.university.model.TimeTable;
import ua.com.foxminded.andriysalnikov.university.service.StudentService;
import ua.com.foxminded.andriysalnikov.university.service.TeacherService;
import ua.com.foxminded.andriysalnikov.university.service.TimeTableManager;
import ua.com.foxminded.andriysalnikov.university.utils.ExceptionUtil;

import java.time.LocalDate;

@Controller
public class UniversityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityController.class);

    private final TimeTableManager timeTableManager;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public UniversityController(TimeTableManager timeTableManager,
                                StudentService studentService, TeacherService teacherService) {
        this.timeTableManager = timeTableManager;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @GetMapping("/")
    public String universityStartPage() {
        return "university";
    }

    @GetMapping("/timetable/teacher")
    public String showTeacherTimeTable(@RequestParam("id") Integer id,
                                       @RequestParam("start_date") String startDate,
                                       @RequestParam("end_date") String endDate,
                                       Model model) {
        Teacher teacher = null;
        try {
            teacher = teacherService.getTeacherByIdWithCourses(id);
        } catch (ServiceException exception) {
            ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("teacher", teacher);
        LOGGER.info(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_TEACHER,
                startDate, endDate);
        TimeTable timeTable;
        try {
            timeTable = timeTableManager.getTimeTableFromStartDateToEndDateByTeacher(
                    LocalDate.parse(startDate), LocalDate.parse(endDate), teacher);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("start_date", startDate);
        model.addAttribute("end_date", endDate);
        model.addAttribute("timetable", timeTable);
        LOGGER.info(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_TEACHER,
                startDate, endDate, timeTable);
        return "teacher_timetable";
    }

    @GetMapping("/timetable/student")
    public String showStudentTimeTable(@RequestParam("id") Integer id,
                                       @RequestParam("start_date") String startDate,
                                       @RequestParam("end_date") String endDate,
                                       Model model) {
        Student student = null;
        try {
            student = studentService.getStudentByIdWithCourses(id);
        } catch (ServiceException exception) {
            ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("student", student);
        LOGGER.info(Messages.TRY_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_STUDENT,
                startDate, endDate);
        TimeTable timeTable;
        try {
            timeTable = timeTableManager.getTimeTableFromStartDateToEndDateByStudent(
                    LocalDate.parse(startDate), LocalDate.parse(endDate), student);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        model.addAttribute("start_date", startDate);
        model.addAttribute("end_date", endDate);
        model.addAttribute("timetable", timeTable);
        LOGGER.info(Messages.OK_GET_TIMETABLE_FROM_STARTDATE_TO_ENDDATE_BY_STUDENT,
                startDate, endDate, timeTable);
        return "student_timetable";
    }

}


