package com.guzanov.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String startPage(Model model) {
        model.addAttribute("name", "Andrey");
        return "start-page";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("title", "MyTitle");
        return "about-page";
    }

}
