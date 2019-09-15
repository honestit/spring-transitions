package pl.honestit.demos.spring.web.controllers.jsp;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.model.entities.user.UserRole;
import pl.honestit.demos.spring.web.utils.Pages;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String prepareRegistrationPage() {
        return Pages.Registration.FORM;
    }

    @PostMapping
    public String processRegistrationPage(String username,
                                          String email,
                                          String password,
                                          String rePassword,
                                          Model model) {
        List<String> errors = validateRegistrationData(username, email, password, rePassword);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return Pages.Registration.FORM;
        } else {
            try {
                registerUser(username, email, password);
                model.addAttribute("successMsg", "Rejestracja przebiegła pomyślnie!");
            } catch (Exception ex) {
                model.addAttribute("errorMsg", "Coś poszło nie tak");
            }
            return Pages.Registration.SUCCESS;
        }
    }

    private void registerUser(String username, String email, String password) {
        UserEntity userToRegister = new UserEntity();
        userToRegister.setUsername(username);
        userToRegister.setEmail(email);
        userToRegister.setPassword(passwordEncoder.encode(password));
        userToRegister.setEnabled(true);
        userToRegister.getRoles().add(new UserRole("ROLE_USER"));

        userRepository.save(userToRegister);
    }

    private List<String> validateRegistrationData(String username, String email, String password, String rePassword) {
        List<String> errors = new ArrayList<>();
        if (username.trim().isEmpty()) {
            errors.add("Nazwa użytkownika nie może być pusta");
        }
        if (email.trim().isEmpty()) {
            errors.add("Email nie może być pusty");
        }
        if (password.trim().isEmpty()) {
            errors.add("Hasło nie może być puste");
        }
        if (!password.equals(rePassword)) {
            errors.add("Niezgodne hasła");
        }
        if (userRepository.countByUsername(username) > 0) {
            errors.add("Nazwa użytkownika jest już zajęta");
        }
        return errors;
    }
}
