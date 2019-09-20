package pl.honestit.demos.spring.web.controllers.jsp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.demos.spring.model.dal.repositories.UserDetailsRepository;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.user.UserDetailsEntity;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.web.utils.Pages;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    public AccountController(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping
    public String prepareUserAccountPage(Model model, Principal principal) {
        UserEntity loggedUser = userRepository.getWithDetailsByUsername(principal.getName());
        log.debug("Zalogowany użytkownik: {}", loggedUser);

        model.addAttribute("user", loggedUser);
        model.addAttribute("details", loggedUser.getDetails());

        return Pages.User.ACCOUNT;
    }

    @PostMapping(params = {"edit"})
    public String beginEditUserData(Model model, Principal principal) {
        UserEntity loggedUser = userRepository.getWithDetailsByUsername(principal.getName());
        log.debug("Rozpoczęcie edycji danych użytkownika: {}", loggedUser);

        model.addAttribute("user", loggedUser);
        model.addAttribute("details", loggedUser.getDetails());
        model.addAttribute("edit", true);

        return Pages.User.ACCOUNT;
    }

    @PostMapping(params = {"save"})
    public String saveEditUserData(String firstName, String lastName, String pesel, String dateOfBirth, Principal principal) {
        UserEntity loggedUser = userRepository.getWithDetailsByUsername(principal.getName());
        log.debug("Zmiana danych użytkownika: {}", loggedUser);
        log.debug("firstName = {}, lastName = {}, pesel = {}, dateOfBirt = {}",
                firstName, lastName, pesel, dateOfBirth);

        UserDetailsEntity details = loggedUser.getDetails();
        if (details == null) {
            details = new UserDetailsEntity();
            details.setOwner(loggedUser);
        }
        details.setFirstName(firstName);
        details.setLastName(lastName);
        details.setPesel(pesel);
        details.setDateOfBirth(LocalDate.parse(dateOfBirth));

        userDetailsRepository.save(details);
        return "redirect:/user/account";
    }

    @PostMapping(params = {"cancel"})
    public String cancelEditUserData() {
        return "redirect:/user/account";
    }
}
