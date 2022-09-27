package ua.com.foxminded.andriysalnikov.university.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.andriysalnikov.university.constants.Messages;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomCreateDTO;
import ua.com.foxminded.andriysalnikov.university.dto.ClassRoomDTO;
import ua.com.foxminded.andriysalnikov.university.mapper.ClassRoomMapper;
import ua.com.foxminded.andriysalnikov.university.repository.ClassRoomRepository;
import ua.com.foxminded.andriysalnikov.university.exceptions.ServiceException;
import ua.com.foxminded.andriysalnikov.university.model.ClassRoom;
import ua.com.foxminded.andriysalnikov.university.service.ClassRoomService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ClassRoomServiceImpl implements ClassRoomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomServiceImpl.class);

    private final ClassRoomRepository classRoomRepository;
    private final ClassRoomMapper classRoomMapper;

    @Autowired
    public ClassRoomServiceImpl(ClassRoomRepository classRoomRepository, ClassRoomMapper classRoomMapper) {
        this.classRoomRepository = classRoomRepository;
        this.classRoomMapper = classRoomMapper;
    }

    @Override
    public List<ClassRoom> getAllClassRooms() {
        LOGGER.debug(Messages.TRY_GET_ALL_CLASSROOMS);
        List<ClassRoom> classRooms = classRoomRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        LOGGER.debug(Messages.OK_GET_ALL_CLASSROOMS, classRooms);
        return classRooms;
    }

    @Override
    public List<ClassRoomDTO> getAllClassRoomDTOs() {
        return getAllClassRooms()
                .stream()
                .map(classRoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassRoom> getAllClassRoomsWithoutFaculty() {
        LOGGER.debug(Messages.TRY_GET_ALL_CLASSROOMS_WITHOUT_FACULTY);
        List<ClassRoom> classRooms = classRoomRepository.findClassRoomsByFacultyIsNullOrderByIdAsc();
        LOGGER.debug(Messages.OK_GET_ALL_CLASSROOMS_WITHOUT_FACULTY, classRooms);
        return classRooms;
    }

    @Override
    public List<ClassRoomDTO> getAllClassRoomDTOsWithoutFaculty() {
        return getAllClassRoomsWithoutFaculty()
                .stream()
                .map(classRoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassRoom getClassRoomById(Integer id) {
        LOGGER.debug(Messages.TRY_GET_CLASSROOM_BY_ID, id);
        ClassRoom classRoom = classRoomRepository.getClassRoomById(id).orElseThrow(() -> {
            LOGGER.error(Messages.ERROR_GET_CLASSROOM_BY_ID);
            throw new ServiceException(Messages.ERROR_GET_CLASSROOM_BY_ID);
        });
        LOGGER.debug(Messages.OK_GET_CLASSROOM_BY_ID, id, classRoom);
        return classRoom;
    }

    @Override
    public ClassRoomDTO getClassRoomDTOById(Integer id) {
        return classRoomMapper.toDTO(getClassRoomById(id));
    }

    @Modifying
    @Transactional
    @Override
    public ClassRoom createClassRoom(ClassRoom classRoom) {
        LOGGER.debug(Messages.TRY_CREATE_CLASSROOM);
        ClassRoom createdClassRoom;
        try {
            createdClassRoom = classRoomRepository.save(classRoom);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_CREATE_CLASSROOM_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_CREATE_CLASSROOM, exception);
        }
        LOGGER.debug(Messages.OK_CREATE_CLASSROOM, createdClassRoom);
        return createdClassRoom;
    }

    @Modifying
    @Transactional
    @Override
    public ClassRoomDTO createClassRoomDTO(ClassRoomCreateDTO classRoomCreateDTO) {
        return classRoomMapper.toDTO(createClassRoom(classRoomMapper.fromDTO(classRoomCreateDTO)));
    }

    @Modifying
    @Transactional
    @Override
    public void deleteClassRoomById(Integer id) {
        LOGGER.debug(Messages.TRY_DELETE_CLASSROOM_BY_ID, id);
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
        ClassRoom updatedClassRoom;
        try {
            updatedClassRoom = classRoomRepository.save(classRoom);
        } catch (RuntimeException exception) {
            LOGGER.error(Messages.ERROR_UPDATE_CLASSROOM_SERVICE, exception.getMessage());
            throw new ServiceException(Messages.ERROR_UPDATE_CLASSROOM, exception);
        }
        LOGGER.debug(Messages.OK_UPDATE_CLASSROOM, updatedClassRoom);
        return updatedClassRoom;
    }

    @Modifying
    @Transactional
    @Override
    public ClassRoomDTO updateClassRoomDTO(Integer id, ClassRoomCreateDTO classRoomCreateDTO) {
        ClassRoom classRoomToUpdate = classRoomMapper.fromDTO(classRoomCreateDTO);
        classRoomToUpdate.setId(id);
        return classRoomMapper.toDTO(updateClassRoom(classRoomToUpdate));
    }

}
