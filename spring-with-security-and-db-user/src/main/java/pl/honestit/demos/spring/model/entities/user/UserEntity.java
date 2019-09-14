package pl.honestit.demos.spring.model.entities.user;

import lombok.Getter;
import lombok.Setter;
import pl.honestit.demos.spring.model.entities.base.ParentEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Objects;

@Entity
/*
    Dodany indeks do szybkiego wyszukiwania po nazwie użytkownika
 */
@Table(name = "example_users", indexes = {
        @Index(columnList = "username")
})
@Getter @Setter
public class UserEntity extends ParentEntity {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private Boolean enabled = Boolean.FALSE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), username);
    }

    /*
        W przypadku toString pod żadnym pozorem nie dokładamy pola
        password ;)
     */
    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", enabled=" + enabled +
                "} " + super.toString();
    }
}
