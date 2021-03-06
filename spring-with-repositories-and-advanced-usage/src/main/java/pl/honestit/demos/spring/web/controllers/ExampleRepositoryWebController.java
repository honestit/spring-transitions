package pl.honestit.demos.spring.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;
import pl.honestit.demos.spring.model.entities.projections.user.Username;
import pl.honestit.demos.spring.model.entities.projections.user.UsernameWithEmail;
import pl.honestit.demos.spring.model.entities.projections.user.UsernameWithEmailWithFirstNameWithLastName;
import pl.honestit.demos.spring.model.entities.projections.user.UsernameWithRoleNames;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        userRepository.findDistinctAllByRoles_RoleNameIn(
                new HashSet<>(
                        Arrays.asList("ROLE_USER")))
                .forEach(System.out::println);

        System.out.println("--- Wszyscy użytkownicy w roli MANAGER lub ADMIN ---");
        userRepository.findDistinctAllByRoles_RoleNameIn(
                new HashSet<>(
                        Arrays.asList("ROLE_MANAGER","ROLE_ADMIN")))
                .forEach(System.out::println);

        return "Zakończone";
    }

    @GetMapping("/advanced")
    @ResponseBody
    public String testAdvancedQueries() {
        System.out.println("--- Lista najnowszych 100 użytkowników ---");
        userRepository.findFirst100ByOrderByCreatedOnDesc().forEach(System.out::println);

        System.out.println("--- Lista najnowszych 100 użytkowników (zapytanie natywne) ---");
        userRepository.findLast100Users().forEach(System.out::println);

        return "Zakończone";
    }

    @GetMapping("/built-in-pagination")
    @ResponseBody
    public String testBuildInPagination() {
        long usersCount = userRepository.count();
        System.out.println("--- Wszystkich użytkowników: " + usersCount);

        int pageSize = 20;
        int pageCount = (int) (usersCount / 20);

        IntStream.rangeClosed(0, pageCount)
                .mapToObj(page -> PageRequest.of(page, pageSize))
                .map(userRepository::findAll)
                .map(Page::getContent)
                .forEach(System.out::println);

        return "Zakończone";
    }

    @GetMapping("/custom-pagination")
    @ResponseBody
    public String testCustomPagination() {
        long usersCount = userRepository.count();
        System.out.println("--- Wszystkich użytkowników: " + usersCount);

        int pageSize = 20;
        int pageCount = (int) (usersCount / 20);

        Set<String> roles = Set.of("ROLE_USER");

        IntStream.rangeClosed(0, pageCount)
                .mapToObj(page -> PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "username")))
                .map(pageRequest -> userRepository.findAllByRoles_RoleNameIn(roles, pageRequest))
                .forEach(System.out::println);

        IntStream.rangeClosed(0, pageCount)
                .mapToObj(page -> PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "enabled", "email")))
                .map(pageRequest -> userRepository.findAllWithDetailsByRoles_RoleNameIn(roles, pageRequest))
                .forEach(System.out::println);

        return "Zakończono";
    }

    @GetMapping("/projections")
    @ResponseBody
    public String testProjections() {
        System.out.println("--- Nazwy użytkowników ---");
        userRepository.findAllBy(Username.class)
                .stream()
                .map(Username::getUsername)
                .forEach(System.out::println);

        System.out.println("--- Nazwy użytkowników z emailami ---");
        userRepository.findAllBy(UsernameWithEmail.class)
                .stream()
                .map(proj -> String.format("Usernanem: %s, email: %s",
                        proj.getUsername(), proj.getEmail()))
                .forEach(System.out::println);

        System.out.println("--- Nazwy użytkowników z danymi szczegółówymi ---");
        userRepository.findAllBy(UsernameWithEmailWithFirstNameWithLastName.class)
                .stream()
                .map(proj -> {
                        if (proj.getDetails() != null) {
                            return String.format("Username: %s, email: %s, firstName: %s, lastName: %s",
                                    proj.getUsername(), proj.getEmail(),
                                    proj.getDetails().getFirstName(),
                                    proj.getDetails().getLastName());
                        }
                        else {
                            return String.format("Username: %s, email: %s",
                                    proj.getUsername(), proj.getEmail());
                        }
                })
                .forEach(System.out::println);

        System.out.println("--- Nazwy użytkowników z rolami ---");
        userRepository.findAllBy(UsernameWithRoleNames.class)
                .stream()
                .map(proj -> String.format("Username: %s, roles: %s",
                        proj.getUsername(), proj.getRoles().stream().map(UsernameWithRoleNames.RoleName::getRoleName).collect(Collectors.toSet())))
                .forEach(System.out::println);

        return "Zakończono";
    }
}
