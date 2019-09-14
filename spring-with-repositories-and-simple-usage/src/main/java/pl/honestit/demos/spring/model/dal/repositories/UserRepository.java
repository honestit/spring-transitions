package pl.honestit.demos.spring.model.dal.repositories;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.model.entities.user.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Pobieranie użytkownika po nazwie.
     *
     * Wymagane aby użytkownik istniał. Jeżeli użytkownik może nie istnieć
     * to metoda zgłosi wyjątek. Lepiej wtedy użyć: {@link #findByUsername(String)}
     *
     * @param username nazwa użytkownika
     * @return encja użytkownika
     */
    User getByUsername(String username);

    /**
     * Wyszukiwanie użytkownika po nazwie.
     *
     * Nie wymagane aby użytkownik istniał.
     *
     * @param username nazwa użytkownika
     * @return encja użytkownika
     */
    Optional<User> findByUsername(String username);

    /**
     * Pobieranie unikalnych użytkowników na podstawie zbioru ról
     *
     * @param roles zbiór ról
     * @return lista użytkowników należących do przynajmniej jednej z ról
     */
    List<UserEntity> findDistinctAllByRoles(Set<UserRole> roles);

    /**
     * Pobieranie użytkowników aktywnych
     *
     * @return lista aktywnych użytkowników
     */
    List<UserEntity> findAllByEnabledIsTrue();

    /**
     * Pobieranie użytkowników nieaktywnych
     *
     * @return lista nieaktywnych użytkowników
     */
    List<UserEntity> findAllByEnabledIsFalse();

    /**
     * Pobieranie listy 100 ostatnio utworzonych użytkowników
     *
     * @return lista użytkowników
     */
    List<User> findFirst100ByOOrderByCreatedOnDesc();

    /**
     * Pobieranie listy 100 ostatnio utworzonych użytkowników (zapytanie natywne)
     *
     * @return lista użytkowników
     */
    @Query(nativeQuery = true,
        value = "SELECT u.* FROM example_users u ORDER BY u.created_on DESC LIMIT 100"
    )
    List<User> findLast100Users();

}
