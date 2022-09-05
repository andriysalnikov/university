package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.repository.ClassRoomRepository;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;
import ua.com.foxminded.andriysalnikov.university.validation.Validation;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClassRoomServiceImpl implements ClassRoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomServiceImpl.class);

    private final ClassRoomRepository classRoomRepository;

    @Autowired
    public ClassRoomServiceImpl(ClassRoomRepository classRoomRepository) {
        this.classRoomRepository = classRoomRepository;
    }

    @Override
    public List<ClassRoom> getAllClassRooms() {
        LOGGER.debug(Messages.TRY_GET_ALL_CLASSROOMS);
        List<ClassRoom> classRooms = classRoomRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        LOGGER.debug(Messages.OK_GET_ALL_CLASSROOMS, classRooms);
        return classRooms;
    }

    @Override
    public List<ClassRoom> getAllClassRoomsWithoutFaculty() {
        LOGGER.debug(Messages.TRY_GET_ALL_CLASSROOMS_WITHOUT_FACULTY);
        List<ClassRoom> classRooms = classRoomRepository.findClassRoomsByFacultyIsNullOrderByIdAsc();
        LOGGER.debug(Messages.OK_GET_ALL_CLASSROOMS_WITHOUT_FACULTY, classRooms);
        return classRooms;
    }

    @Override
    public ClassRoom getClassRoomById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_CLASSROOM_BY_ID, id);
        Validation.validateId(id);
        ClassRoom classRoom = classRoomRepository.getClassRoomById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_CLASSROOM_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_CLASSROOM_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_CLASSROOM_BY_ID, id, classRoom);
        return classRoom;
    }


    @Modifying
    @Transactional
    @Override
    public ClassRoom createClassRoom(ClassRoom classRoom) {
        LOGGER.debug(Messages.TRY_CREATE_CLASSROOM);
        Validation.validateClassRoom(classRoom);
        ClassRoom createdClassRoom;
        try {
            createdClassRoom = classRoomRepository.save(classRoom);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_CREATE_CLASSROOM);
            throw new ServiceException(Messages.ERROR_CREATE_CLASSROOM);
        }
        LOGGER.debug(Messages.OK_CREATE_CLASSROOM, createdClassRoom);
        return createdClassRoom;
    }

    @Modifying
    @Transactional
    @Override
    public void deleteClassRoomById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_CLASSROOM_BY_ID, id);
        Validation.validateId(id);
        if (classRoomRepository.deleteClassRoomById(id) == 0) {
            LOGGER.error(Messages.ERROR_DELETE_CLASSROOM_BY_ID);
            throw new ServiceException(Messages.ERROR_DELETE_CLASSROOM_BY_ID);
        }
        LOGGER.debug(Messages.OK_DELETE_CLASSROOM_BY_ID, id);
    }

    @Modifying
    @Transactional
    @Override
    public ClassRoom updateClassRoom(ClassRoom classRoom) {
        LOGGER.debug(Messages.TRY_UPDATE_CLASSROOM, classRoom);
        Validation.validateClassRoom(classRoom);
        ClassRoom updatedClassRoom;
        try {
            updatedClassRoom = classRoomRepository.save(classRoom);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_UPDATE_CLASSROOM);
            throw new ServiceException(Messages.ERROR_UPDATE_CLASSROOM);
        }
        LOGGER.debug(Messages.OK_UPDATE_CLASSROOM, updatedClassRoom);
        return updatedClassRoom;
    }

}
