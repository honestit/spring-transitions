package pl.honestit.demos.spring.web.controllers;

import antlr.collections.impl.IntRange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

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

        IntStream.rangeClosed(0, pageCount).mapToObj(page -> PageRequest.of(page, pageSize)).map(userRepository::findAll).map(Page::getContent).forEach(System.out::println);

        return "Zakończone";
    }
}
