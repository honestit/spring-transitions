package pl.honestit.demos.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.user.UserRole;

import java.util.Arrays;
import java.util.HashSet;

@Controller
@RequestMapping("/examples/repositories")
public class ExampleRepositoryWebController {

    private final UserRepository userRepository;

    public ExampleRepositoryWebController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/simple")
    @ResponseBody
    public String testSimpleQueries() {
        System.out.println("--- Wszyscy użytkownicy ---");
        userRepository.findAll().forEach(System.out::println);

        System.out.println("--- Aktywni użytkownicy ---");
        userRepository.findAllByEnabledIsTrue().forEach(System.out::println);

        System.out.println("--- Wszyscy nieaktywni użytkownicy ---");
        userRepository.findAllByEnabledIsFalse().forEach(System.out::println);

        System.out.println("--- Wszyscy użytkownicy w roli USER ---");
        userRepository.findDistinctAllByRoles_RoleName(
                new HashSet<>(
                        Arrays.asList("ROLE_USER")))
                .forEach(System.out::println);

        System.out.println("--- Wszyscy użytkownicy w roli MANAGER lub ADMIN ---");
        userRepository.findDistinctAllByRoles_RoleName(
                new HashSet<>(
                        Arrays.asList("ROLE_MANAGER","ROLE_ADMIN")))
                .forEach(System.out::println);

        return "Zakończone";
    }
}
