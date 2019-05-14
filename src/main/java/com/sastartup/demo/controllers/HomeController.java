package com.sastartup.demo.controllers;

import com.sastartup.demo.repositories.*;
import org.springframework.stereotype.Controller;
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
    public String hello(){
        return "startups/index";
    }

}
