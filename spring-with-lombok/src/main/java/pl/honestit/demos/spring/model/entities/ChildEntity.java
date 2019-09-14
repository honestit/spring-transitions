package pl.honestit.demos.spring.model.entities;

import pl.honestit.demos.spring.model.entities.base.ParentEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "example_child_entities")
public class ChildEntity extends ParentEntity {

    @Column(unique = true, nullable = false)
    private String name;
    private Integer value;
    private LocalDateTime posted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChildEntity that = (ChildEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "ChildEntity{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", posted=" + posted +
                "} " + super.toString();
    }
}
