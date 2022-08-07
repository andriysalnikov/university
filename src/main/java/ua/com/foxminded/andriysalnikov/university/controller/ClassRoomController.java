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
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;

import java.util.List;

@Controller
public class ClassRoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomController.class);

    private final ClassRoomService classRoomService;

    @Autowired
    public ClassRoomController (ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @GetMapping("/classrooms")
    public String getAllClassRooms(Model model) {
        LOGGER.info(Messages.TRY_GET_ALL_CLASSROOMS);
        List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
        LOGGER.info(Messages.OK_GET_ALL_CLASSROOMS, classRooms);
        model.addAttribute("classrooms", classRooms);
        return "classroom/classrooms";
    }

    @GetMapping("/classroom/create")
    public String getCreationClassRoomModalWindow() {
        LOGGER.info(Messages.TRY_CREATE_CLASSROOM);
        return "classroom/classroom_create";
    }

    @PostMapping("/classroom/create")
    public String createClassRoom(@RequestParam("name") String name, Model model) {
        ClassRoom createdClassRoom;
        try {
            createdClassRoom = classRoomService.createClassRoom(new ClassRoom(0, name));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_CREATE_CLASSROOM, createdClassRoom);
        return "redirect:/classrooms";
    }

    @PostMapping("/classroom/delete")
    public String deleteClassRoom(@RequestParam("id") Integer classRoomId, Model model) {
        LOGGER.info(Messages.TRY_DELETE_CLASSROOM_BY_ID, classRoomId);
        ClassRoom deletedClassRoom;
        try {
            deletedClassRoom = classRoomService.deleteClassRoomById(classRoomId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_DELETE_CLASSROOM_BY_ID, classRoomId, deletedClassRoom);
        return "redirect:/classrooms";
    }

    @GetMapping("/classroom/update")
    public String getUpdationClassRoomModalWindow(@RequestParam("id") Integer classRoomId, Model model) {
        ClassRoom classRoom;
        try {
            classRoom = classRoomService.getClassRoomById(classRoomId);
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.TRY_UPDATE_CLASSROOM, classRoom);
        model.addAttribute("classroom", classRoom);
        return "classroom/classroom_update";
    }

    @PostMapping("/classroom/update")
    public String updateClassRoom(@RequestParam("id") Integer classRoomId,
                                  @RequestParam("name") String name,
                                  Model model) {
        ClassRoom updatedClassRoom;
        try {
            updatedClassRoom = classRoomService.updateClassRoom(new ClassRoom(classRoomId, name));
        } catch (ServiceException exception) {
            return ExceptionUtil.handleException(exception, LOGGER, model);
        }
        LOGGER.info(Messages.OK_UPDATE_CLASSROOM, updatedClassRoom);
        return "redirect:/classrooms";
    }

}
