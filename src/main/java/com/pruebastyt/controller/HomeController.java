package com.pruebastyt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // Retorna la vista "index.html" ubicada en src/main/resources/templates/
        return "index";
    }
}