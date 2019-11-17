package pl.honestit.demos.spring.web.controllers.jsp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.model.entities.user.UserRole;
import pl.honestit.demos.spring.web.requests.RegistrationRequest;
import pl.honestit.demos.spring.web.utils.Pages;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/register")
@Slf4j
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
    public String processRegistrationPage(@ModelAttribute("data") @Valid RegistrationRequest registrationRequest, Errors errors, Model model) {
        log.debug("Żądanie rejestracji użytkownika: {}", registrationRequest);

        // Uzupełnienie potencjalnych błędów o błędy "biznesowe"
        updateErrors(registrationRequest, errors);
        if (errors.hasErrors()) {
            // Pobranie zbioru pól, dla których są błędy i wstawienie do modelu
            model.addAttribute("invalidFields", errors.getFieldErrors().stream()
                    .map(FieldError::getField).distinct().collect(Collectors.toSet()));
            return Pages.Registration.FORM;
        }
        else {
            registerUser(registrationRequest);
            model.addAttribute("successMsg", "Rejestracja przebiegła pomyślnie");
            return Pages.Registration.SUMMARY;
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

    private void updateErrors(RegistrationRequest registrationRequest, Errors bindingResult) {
        if (!registrationRequest.getPassword().equals(registrationRequest.getRePassword())) {
            bindingResult.reject("rePassword", null, "Niezgodne hasła");
        }
        try {
            if (userRepository.countByUsername(registrationRequest.getUsername()) > 0) {
                bindingResult.reject("username", null, "Nazwa użytkownika jest już zajęta");
            }
        } catch (Exception ex) {
            bindingResult.reject("username", null, "Nie udało się sprawdzić nazwy użytkownika");
        }
    }
}
