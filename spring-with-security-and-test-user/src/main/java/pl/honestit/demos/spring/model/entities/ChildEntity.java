package pl.honestit.demos.spring.model.entities;

import lombok.*;
import pl.honestit.demos.spring.model.entities.base.ParentEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "example_child_entities")
@Getter @Setter @NoArgsConstructor
/*
    Przy metodzie toString należy uważać, bo nie powinniśmy w niej
    uwzględniać pól mnogich (kolekcji) ani pól, które są typu innych encji
 */
@ToString
/*
    Metody equals i hashCode nadpisujemy w encjach zawsze ręcznie, z dużą
    ostrożnością. Wybieramy do nich tylko pola, które:
    - są unikalne
    - nie mogą przyjmować wartości null
    - nie zmieniają się w czasie życia rekordu (instancji encji)

    Przykładem takiego pola może być pole username reprezentujące nazwę użytkownika
    która nie może się zmieniać w czasie
 */
public class ChildEntity extends ParentEntity {

    @Column(unique = true, nullable = false)
    private String name;
    private Integer value;
    private LocalDateTime posted;

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
}
