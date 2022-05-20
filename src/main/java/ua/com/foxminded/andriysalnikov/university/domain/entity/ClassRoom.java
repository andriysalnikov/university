package ua.com.foxminded.andriysalnikov.university.domain.entity;

import java.util.Objects;

public class ClassRoom {

    private Integer id;
    private String name;

    public ClassRoom() { }

    public ClassRoom(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return Objects.equals(name, classRoom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
