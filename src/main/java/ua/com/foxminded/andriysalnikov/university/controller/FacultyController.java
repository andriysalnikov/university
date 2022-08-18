package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.utils.ExceptionUtil;
import ua.com.foxminded.andriysalnikov.university.model.Faculty;
import ua.com.foxminded.andriysalnikov.university.service.FacultyService;

import java.util.List;

@Controller
public class FacultyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyController.class);

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/faculties")
    public String getAllFaculties(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_FACULTIES);
        List<Faculty> faculties = facultyService.getAllFaculties();
        LOGGER.info(Messages.OK_GET_ALL_FACULTIES, faculties);
        model.addAttribute("faculties", faculties);
        return "faculty/faculties";
    }

    @GetMapping("/faculty/create")
    public String getCreationFacultyModalWindow() {
        LOGGER.info(Messages.TRY_CREATE_FACULTY);
        return "faculty/faculty_create";
    }

    @PostMapping("/faculty/create")
    public String createFaculty(@RequestParam("name") String fullName, Model model) {
        Faculty createdFaculty;
        try {
            createdFaculty = facultyService.createFaculty(new Faculty(fullName));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_CREATE_FACULTY, createdFaculty);
        return "redirect:/faculties";
    }

    @PostMapping("/faculty/delete")
    public String deleteFaculty(@RequestParam("id") Integer facultyId, Model model) {
        LOGGER.info(Messages.TRY_DELETE_FACULTY_BY_ID, facultyId);
        Faculty deletedFaculty;
        try {
            deletedFaculty = facultyService.deleteFacultyById(facultyId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_DELETE_FACULTY_BY_ID, facultyId, deletedFaculty);
        return "redirect:/faculties";
    }

    @GetMapping("/faculty/update")
    public String getUpdationFacultyModalWindow(@RequestParam("id") Integer facultyId, Model model) {
        Faculty faculty;
        try {
            faculty = facultyService.getFacultyById(facultyId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.TRY_UPDATE_FACULTY, faculty);
        model.addAttribute("faculty", faculty);
        return "faculty/faculty_update";
    }

    @PostMapping("/faculty/update")
    public String updateFaculty(@RequestParam("id") Integer facultyId,
                                @RequestParam("name") String fullName,
                                Model model) {
        Faculty updatedFaculty;
        try {
            Faculty faculty = new Faculty(fullName);
            faculty.setId(facultyId);
            updatedFaculty = facultyService.updateFaculty(faculty);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_UPDATE_FACULTY, updatedFaculty);
        return "redirect:/faculties";
    }

}
