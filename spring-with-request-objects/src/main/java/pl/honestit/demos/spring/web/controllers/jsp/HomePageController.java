package pl.honestit.demos.spring.web.controllers.jsp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.demos.spring.web.utils.Pages;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/")
public class HomePageController {

    @GetMapping
    public String prepareHomePage(Model model) {
        return Pages.HOME;
    }
}
