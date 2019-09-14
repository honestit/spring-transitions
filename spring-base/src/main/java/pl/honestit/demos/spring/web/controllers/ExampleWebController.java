package pl.honestit.demos.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/examples/web")
public class ExampleWebController {

    @GetMapping
    @ResponseBody
    public String getExampleData() {
        return String.format("Some data");
    }

    @PostMapping
    @ResponseBody
    public String processExampleData(String data) {
        return String.format("Processed data: %s", data);
    }
}
