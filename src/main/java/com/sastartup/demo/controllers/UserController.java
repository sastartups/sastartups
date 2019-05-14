package com.sastartup.demo.controllers;

import com.sastartup.demo.models.Resume;
import com.sastartup.demo.models.Startup;
import com.sastartup.demo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

//    @GetMapping("/")
//    public String hello(){
//        return "index";
//    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "users/signup-form";
    }

//    @PostMapping("/register")
//    public String

//    @GetMapping("/create/startup")
//    public String createStartupForm(Model model){
//        model.addAttribute("startup", new Startup());
//        return "users/create-startup";
//    }
//
//    @GetMapping("/submitresume")
//    public String resumeForm(Model model){
//        model.addAttribute("resume", new Resume());
//        return "users/resume";
//    }


}
