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
    private final NotificationRepo notificationRepo;

//    constructor


    public HomeController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao, NotificationRepo notificationRepo) {
        this.jobDao = jobDao;
        this.resumeDao = resumeDao;
        this.startupDao = startupDao;
        this.userDao = userDao;
        this.notificationRepo = notificationRepo;
    }


//    ----------------------------------------------------------------------------------

    @GetMapping("/")
    public String hello(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User dbUser = userDao.findOne(sessionUser.getId());
            model.addAttribute("user", dbUser);
            model.addAttribute("count", notificationRepo.countByUser(dbUser));
            model.addAttribute("navNotifications", notificationRepo.findTop4ByUserOrderByIdDesc(dbUser));
        } else {
            model.addAttribute("user", null);
        }
        return "startups/index";
    }

}
