package pl.honestit.demos.spring.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.model.entities.user.UserRole;

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Slf4j
@Component
public class SetupDataCreator implements ApplicationRunner {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SetupDataCreator(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("--- Tworzenie użytkowników głównych ---");
        createMainUsers();
        if (activeProfile.equals("dev")) {
            log.info("--- Tworzenie użytkowników testowych ---");
            createTestUsers();
        }
    }

    private void createTestUsers() {
        LongStream.range(1, 100).forEach(this::createTestUser);
    }

    private void createTestUser(Long id) {
        createUserIfNotExists("user" + id, "pass" + id, "email" + id + "@honestit.pl", "USER");
    }

    private void createMainUsers() {
        createUserIfNotExists("user", "pass", "user@honestit.pl", "USER");
        createUserIfNotExists("manager", "pass", "manager@honestit.pl", "MANAGER", "MANAGER");
        createUserIfNotExists("admin", "pass", "admin@honestit.pl", "ADMIN");
    }

    private void createUserIfNotExists(String username, String password, String email, String... roles) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(
                        user -> {
                            log.debug("Użytkownik już istnieje: " + user.getUsername());
                        },
                        () -> {
                            UserEntity user = new UserEntity();
                            user.setUsername(username);
                            user.setPassword(passwordEncoder.encode(password));
                            user.setEmail(email);
                            user.setEnabled(true);
                            user.getRoles().addAll(Stream.of(roles).map(role -> "ROLE_".concat(role)).map(UserRole::new).collect(Collectors.toSet()));
                            userRepository.save(user);
                            log.debug("Utworzono użytkownika: " + user);
                        }
                );
    }
}
