package com.sastartup.demo.controllers;

import com.sastartup.demo.models.User;
import com.sastartup.demo.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    private final JobRepo jobDao;
    private final ResumeRepo resumeDao;
    private final StartupRepo startupDao;
    private final UserRepo userDao;

//    constructor


    public HomeController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao) {
        this.jobDao = jobDao;
        this.resumeDao = resumeDao;
        this.startupDao = startupDao;
        this.userDao = userDao;
    }


//    ----------------------------------------------------------------------------------

    @GetMapping("/")
    public String hello(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User dbUser = userDao.findOne(sessionUser.getId());
            model.addAttribute("user", dbUser);
        } else {
            model.addAttribute("user", null);
        }

        return "startups/index";
    }

}
