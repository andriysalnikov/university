package ua.com.foxminded.andriysalnikov.university.dto;

public class FacultyDTO {

    private final String id;
    private final String fullName;

    public FacultyDTO(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "FacultyDTO{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

}
