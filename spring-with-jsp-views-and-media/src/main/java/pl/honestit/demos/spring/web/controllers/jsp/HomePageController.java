package pl.honestit.demos.spring.web.controllers.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.demos.spring.web.utils.Pages;

@Controller
@RequestMapping("/")
public class HomePageController {

    @GetMapping
    public String prepareHomePage() {
        return Pages.HOME;
    }
}
