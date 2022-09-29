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
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "teachers", schema = "university")
@NoArgsConstructor
@ToString
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Column(name = "first_name")
    @Getter @Setter
    private String firstName;

    @Column(name = "last_name")
    @Getter @Setter
    private String lastName;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH },
            mappedBy = "teacher")
    @OrderBy(value = "id")
    @Size(max = 5, message = "Every Teacher may have maximum 5 Courses ")
    @Getter
    @ToString.Exclude
    private final List<Course> courses = new ArrayList<>();

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(firstName, teacher.firstName) &&
                Objects.equals(lastName, teacher.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

}
