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

    @Value("${classpath:static/images/0.5/}")
    private Resource image;

    @GetMapping
    public String prepareHomePage(Model model) {
        model.addAttribute("categories", Arrays.asList("aaa", "bbb", "ccc"));
        model.addAttribute("subcategories", Arrays.asList("aaa-1", "aaa-2", "aaa-3"));
        return Pages.HOME;
    }

    @PostMapping(params = {"save"})
    public String saveForm(String category, String subcategory) {
        log.info("category={}, subcategory={}", category, subcategory);
        return "redirect:/";
    }

    @PostMapping
    public String changeSelect(String category, Model model) {
        model.addAttribute("categories", Arrays.asList("aaa", "bbb", "ccc"));
        if ("aaa".equals(category)) {
            model.addAttribute("subcategories", Arrays.asList("aaa-1", "aaa-2", "aaa-3"));
        }
        else if ("bbb".equals(category)) {
            model.addAttribute("subcategories", Arrays.asList("bbb-1", "bbb-2", "bbb-3"));
        }
        else {
            model.addAttribute("subcategories", Collections.emptyList());
        }
        return Pages.HOME;
    }
}
