package ua.com.foxminded.andriysalnikov.university.dto;

import java.util.ArrayList;
import java.util.List;

public class FacultyDTOWithClassRooms {

    private final String id;
    private final String fullName;
    private final List<String> classRooms;

    public FacultyDTOWithClassRooms(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        this.classRooms = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public List<String> getClassRooms() {
        return classRooms;
    }

    @Override
    public String toString() {
        return "FacultyWithClassRoomsDTO{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", classRooms=" + classRooms +
                '}';
    }

}
