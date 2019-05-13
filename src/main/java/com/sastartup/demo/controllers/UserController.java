//package com.sastartup.demo.controllers;
//
//import com.sastartup.demo.models.User;
//import com.sastartup.demo.repositories.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class UserController {
//
//    @Autowired
//    private UserRepo users;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public UserController(UserRepo user,PasswordEncoder passwordEncoder){
//        this.users = user;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @GetMapping("/sign-up")
//    public String showSignupForm(Model model){
//        model.addAttribute("user", new User());
//        return "users/sign-up";
//    }
//
//    @PostMapping("/sign-up")
//    public String saveUser(@ModelAttribute User user){
//        String hash = passwordEncoder.encoder.encode(user.getPassword());
//        user.setPassword(hash);
//        users.save(user);
//        return "redirect:/login";
//    }
//
//}
