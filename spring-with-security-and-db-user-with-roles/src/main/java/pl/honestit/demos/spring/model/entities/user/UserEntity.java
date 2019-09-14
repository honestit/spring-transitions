package pl.honestit.demos.spring.model.entities.user;

import lombok.Getter;
import lombok.Setter;
import pl.honestit.demos.spring.model.entities.base.ParentEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
/*
    Dodany indeks do szybkiego wyszukiwania po nazwie użytkownika
    Dodany indeks do szybkiego wyszukiwania po email'u użytkownika
 */
@Table(name = "example_users", indexes = {
        @Index(columnList = "username"),
        @Index(columnList = "email")
})
@Getter @Setter
public class UserEntity extends ParentEntity {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private Boolean enabled = Boolean.FALSE;
    private String email;

    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
    private UserDetailsEntity details;

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
