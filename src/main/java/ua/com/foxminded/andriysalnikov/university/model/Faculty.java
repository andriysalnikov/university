package ua.com.foxminded.andriysalnikov.university.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "faculties", schema = "university")
@NoArgsConstructor
@ToString
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Column(name = "full_name")
    @Getter @Setter
    private String fullName;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            mappedBy = "faculty")
    @OrderBy(value = "id")
    @Getter
    @ToString.Exclude
    private final List<ClassRoom> classRooms = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            mappedBy = "faculty")
    @OrderBy(value = "id")
    @Getter
    @ToString.Exclude
    private final List<Course> courses = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            mappedBy = "faculty")
    @OrderBy(value = "id")
    @Getter
    @ToString.Exclude
    private final List<Student> students = new ArrayList<>();

    public Faculty(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(fullName, faculty.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

}
