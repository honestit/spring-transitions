package pl.honestit.demos.spring.model.dal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.honestit.demos.spring.model.entities.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
