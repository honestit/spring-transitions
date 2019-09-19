package pl.honestit.demos.spring.web.controllers.jsp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.user.UserEntity;
import pl.honestit.demos.spring.web.utils.Pages;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/user/account")
public class UserAccountController {

    private final UserRepository userRepository;

    public UserAccountController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String prepareUserAccountPage(Model model, Principal principal) {
        log.info("Zalogowany użytkownik: {}", principal.getName());

        UserEntity loggedUser = userRepository.getWithDetailsByUsername(principal.getName());
        log.debug("Zalogowany użytkownik z db: {}", loggedUser);

        model.addAttribute("user", loggedUser);
        model.addAttribute("details", loggedUser.getDetails());

        return Pages.User.ACCOUNT;
    }
}
