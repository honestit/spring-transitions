package pl.honestit.demos.spring.model.dal.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.honestit.demos.spring.model.entities.user.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {

    @EntityGraph(attributePaths = {"profileFile.data"})
    UserDetailsEntity getWithProfileFileByOwnerUsername(String username);
}
