package com.project.jujucabomboniermanegementsystem.managementsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "redirect:/index.html";  // redireciona para o arquivo estático
    }
}
