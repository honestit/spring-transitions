package pl.honestit.demos.spring.model.dal.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.honestit.demos.spring.model.entities.user.UserEntity;

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
    UserEntity getByUsername(String username);

    /**
     * Wyszukiwanie użytkownika po nazwie.
     *
     * Nie wymagane aby użytkownik istniał.
     *
     * @param username nazwa użytkownika
     * @return encja użytkownika
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Pobieranie unikalnych użytkowników na podstawie zbioru ról
     *
     * @param roles zbiór ról
     * @return lista użytkowników należących do przynajmniej jednej z ról
     */
    List<UserEntity> findDistinctAllByRoles_RoleNameIn(Set<String> roles);

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
    List<UserEntity> findFirst100ByOrderByCreatedOnDesc();

    /**
     * Pobieranie listy 100 ostatnio utworzonych użytkowników (zapytanie natywne)
     *
     * @return lista użytkowników
     */
    @Query(nativeQuery = true,
        value = "SELECT u.* FROM example_users u ORDER BY u.created_on DESC LIMIT 100"
    )
    List<UserEntity> findLast100Users();

    /**
     * Pobieranie listy użytkowników należących do podanych ról
     *
     * @param roles lista ról
     * @param pageable obiekt wytycznych paginowania i sortowania
     *
     * @return lista użytkowników
     */
    List<UserEntity> findAllByRoles_RoleNameIn(Set<String> roles, Pageable pageable);

    /**
     * Pobieranie listy użytkowników należących do podanych ról.
     * Metoda wykorzystuje @EntityGrapth, aby zoptymalizować zapytanie
     * i od razu pobrać dane użytkowników
     *
     * @param roles lista ról
     * @param pageable obiekt wytycznych paginowania i sortowania
     * @return lista użytkowników
     */
    @EntityGraph(attributePaths = {"details"})
    List<UserEntity> findAllWithDetailsByRoles_RoleNameIn(Set<String> roles, Pageable pageable);



}
