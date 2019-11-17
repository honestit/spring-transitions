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
import pl.honestit.demos.spring.web.requests.RegistrationRequest;
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
    public String processRegistrationPage(RegistrationRequest registrationRequest,
                                          Model model) {
        List<String> errors = validateRegistrationData(registrationRequest);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return Pages.Registration.FORM;
        } else {
            try {
                registerUser(registrationRequest);
                model.addAttribute("successMsg", "Rejestracja przebiegła pomyślnie!");
            } catch (Exception ex) {
                model.addAttribute("errorMsg", "Coś poszło nie tak");
            }
            return Pages.Registration.SUCCESS;
        }
    }

    private void registerUser(RegistrationRequest registrationRequest) {
        UserEntity userToRegister = new UserEntity();
        userToRegister.setUsername(registrationRequest.getUsername());
        userToRegister.setEmail(registrationRequest.getEmail());
        userToRegister.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToRegister.setEnabled(true);
        userToRegister.getRoles().add(new UserRole("ROLE_USER"));

        userRepository.save(userToRegister);
    }

    private List<String> validateRegistrationData(RegistrationRequest registrationRequest) {
        List<String> errors = new ArrayList<>();
        if (registrationRequest.getUsername().trim().isEmpty()) {
            errors.add("Nazwa użytkownika nie może być pusta");
        }
        if (registrationRequest.getEmail().trim().isEmpty()) {
            errors.add("Email nie może być pusty");
        }
        if (registrationRequest.getEmail().trim().isEmpty()) {
            errors.add("Hasło nie może być puste");
        }
        if (!registrationRequest.getPassword().equals(registrationRequest.getRePassword())) {
            errors.add("Niezgodne hasła");
        }
        try {
            if (userRepository.countByUsername(registrationRequest.getUsername()) > 0) {
                errors.add("Nazwa użytkownika jest już zajęta");
            }
        } catch (Exception ex) {
            errors.add("Nie udało się sprawdzić nazwy użytkownika");
        }
        return errors;
    }
}
