package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dao.ClassRoomDAO;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.util.List;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomServiceImpl.class);

    private final ClassRoomDAO classRoomDAO;

    @Autowired
    public ClassRoomServiceImpl(ClassRoomDAO classRoomDAO) {
        this.classRoomDAO = classRoomDAO;
    }

    @Override
    public List<ClassRoom> getAllClassRooms() {
        LOGGER.debug(Messages.TRY_GET_ALL_CLASSROOMS);
        List<ClassRoom> classRooms = classRoomDAO.getAllClassRooms();
        LOGGER.debug(Messages.OK_GET_ALL_CLASSROOMS, classRooms);
        return classRooms;
    }

    @Override
    public List<ClassRoom> getAllClassRoomsWithoutFaculty() {
        LOGGER.debug(Messages.TRY_GET_ALL_CLASSROOMS_WITHOUT_FACULTY);
        List<ClassRoom> classRooms = classRoomDAO.getAllClassRoomsWithoutFaculty();
        LOGGER.debug(Messages.OK_GET_ALL_CLASSROOMS_WITHOUT_FACULTY, classRooms);
        return classRooms;
    }

    @Override
    public ClassRoom getClassRoomById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_CLASSROOM_BY_ID, id);
        Validation.validateId(id);
        ClassRoom classRoom = classRoomDAO.getClassRoomById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_CLASSROOM_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_CLASSROOM_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_CLASSROOM_BY_ID, id, classRoom);
        return classRoom;
    }

    @Override
    public ClassRoom createClassRoom(ClassRoom classRoom) {
        LOGGER.debug(Messages.TRY_CREATE_CLASSROOM);
        Validation.validateClassRoom(classRoom);
        ClassRoom createdClassRoom = classRoomDAO.createClassRoom(classRoom).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_CREATE_CLASSROOM);
            throw new ServiceException(Messages.ERROR_CREATE_CLASSROOM);
        });
        LOGGER.debug(Messages.OK_CREATE_CLASSROOM, createdClassRoom);
        return createdClassRoom;
    }

    @Override
    public ClassRoom deleteClassRoomById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_CLASSROOM_BY_ID, id);
        Validation.validateId(id);
        ClassRoom deletedClassRoom = classRoomDAO.deleteClassRoomById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_DELETE_CLASSROOM_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_CLASSROOM_BY_ID);
        });
        LOGGER.debug(Messages.OK_DELETE_CLASSROOM_BY_ID, id, deletedClassRoom);
        return deletedClassRoom;
    }

    @Override
    public ClassRoom updateClassRoom(ClassRoom classRoom) {
        LOGGER.debug(Messages.TRY_UPDATE_CLASSROOM, classRoom);
        Validation.validateClassRoom(classRoom);
        ClassRoom updatedClassRoom = classRoomDAO.updateClassRoom(classRoom).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_UPDATE_CLASSROOM);
            throw new ServiceException(Messages.ERROR_UPDATE_CLASSROOM);
        });
        LOGGER.debug(Messages.OK_UPDATE_CLASSROOM, updatedClassRoom);
        return updatedClassRoom;
    }

    @Override
    public ClassRoom setFacultyToClassRoom(Integer facultyId, Integer classRoomId) {
        LOGGER.debug(Messages.TRY_SET_FACULTY_TO_CLASSROOM, facultyId, classRoomId);
        Validation.validateId(facultyId);
        Validation.validateId(classRoomId);
        ClassRoom updatedClassroom = classRoomDAO.setFacultyToClassRoom(facultyId, classRoomId).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_SET_FACULTY_TO_CLASSROOM);
            throw new ServiceException(Messages.ERROR_SET_FACULTY_TO_CLASSROOM);
        });
        LOGGER.debug(Messages.OK_SET_FACULTY_TO_CLASSROOM, facultyId, classRoomId, updatedClassroom);
        return updatedClassroom;
    }

    @Override
    public ClassRoom removeFacultyFromClassRoom(Integer classRoomId) {
        LOGGER.debug(Messages.TRY_REMOVE_FACULTY_FROM_CLASSROOM, classRoomId);
        Validation.validateId(classRoomId);
        ClassRoom updatedClassroom = classRoomDAO.removeFacultyFromClassRoom(classRoomId).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_REMOVE_FACULTY_FROM_CLASSROOM);
            throw new ServiceException(Messages.ERROR_REMOVE_FACULTY_FROM_CLASSROOM);
        });
        LOGGER.debug(Messages.OK_REMOVE_FACULTY_FROM_CLASSROOM, classRoomId, updatedClassroom);
        return updatedClassroom;
    }

}
