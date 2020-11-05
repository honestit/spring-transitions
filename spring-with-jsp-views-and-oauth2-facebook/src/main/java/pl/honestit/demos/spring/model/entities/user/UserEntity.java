package pl.honestit.demos.spring.model.entities.user;

import lombok.*;
import pl.honestit.demos.spring.model.entities.base.ParentEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
/*
    Dodany indeks do szybkiego wyszukiwania po nazwie użytkownika
    Dodany indeks do szybkiego wyszukiwania po email'u użytkownika
 */
@Table(name = "example_users", indexes = {
        @Index(columnList = "username"),
        @Index(columnList = "email")
})
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class UserEntity extends ParentEntity {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "auth_provider")
    private String authProvider;
    private Boolean enabled = Boolean.FALSE;
    private String email;

    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserDetailsEntity details;

    /*
        Role nie tworzymy jako encje, a jako obiekty typu Embeddable.
        Dla zbioru ról powstanie dedykowana tabela. Zostanie w niej utworzona
        kolumna wynikająca z klasy UserRole ale również kolumna klucza głównego.

        Relacja między tabelami oparta jest nie na polu id, a na polu username.
        Dzięki temu tabela example_users_roles będzie składała się z pary kolumn
        username i role_name i przykładowych wartości:

        akowalski, ROLE_USER
        akowalski, ROLE_MANAGER
        admin, ROLE_ADMIN
     */
    @ElementCollection
    @CollectionTable(name = "example_users_roles",
        joinColumns = @JoinColumn(name = "username", referencedColumnName = "username")
    )
    private Set<UserRole> roles = new HashSet<>();

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
