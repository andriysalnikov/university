package ua.com.foxminded.andriysalnikov.university.dto;

public class ClassRoomDTO {

    private final String id;
    private final String name;

    public ClassRoomDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClassRoomDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
