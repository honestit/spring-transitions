package pl.honestit.demos.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.demos.spring.model.dal.repositories.UserRepository;

@Controller
@RequestMapping("/examples/repositories")
public class ExampleRepositoryWebController {

    private final UserRepository userRepository;

    public ExampleRepositoryWebController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
