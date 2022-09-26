package ua.com.foxminded.andriysalnikov.university.dto;

import java.util.ArrayList;
import java.util.List;

public class FacultyWithStudentsDTO {

    private final String id;
    private final String fullName;
    private final List<String> students;

    public FacultyWithStudentsDTO(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        this.students = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public List<String> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "FacultyWithStudentsDTO{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", students=" + students +
                '}';
    }

}
