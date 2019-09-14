package pl.honestit.demos.spring.startup;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.model.entities.user.UserRole;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SetupDataCreator implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SetupDataCreator(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createUserIfNotExists("user", "pass", "user@honestit.pl", "USER");
        createUserIfNotExists("manager", "pass", "manager@honestit.pl", "MANAGER", "MANAGER");
        createUserIfNotExists("admin", "pass", "admin@honestit.pl", "ADMIN");
    }

    private void createUserIfNotExists(String username, String password, String email, String... roles) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(System.out::println,
                        () -> {
                            UserEntity user = new UserEntity();
                            user.setUsername(username);
                            user.setPassword(passwordEncoder.encode(password));
                            user.setEmail(email);
                            user.setEnabled(true);
                            user.getRoles().addAll(Stream.of(roles).map(UserRole::new).collect(Collectors.toSet()));
                            userRepository.save(user);
                        });
    }
}
