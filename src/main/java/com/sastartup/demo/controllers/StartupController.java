package com.sastartup.demo.controllers;


import com.sastartup.demo.models.*;

import com.sastartup.demo.repositories.JobRepo;
import com.sastartup.demo.repositories.ResumeRepo;
import com.sastartup.demo.repositories.StartupRepo;
import com.sastartup.demo.repositories.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class StartupController {

    private final JobRepo jobDao;
    private final ResumeRepo resumeDao;
    private final StartupRepo startupDao;
    private final UserRepo userDao;
    private EmailService emailService;

//    constructor


    public StartupController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao,EmailService emailService) {
        this.jobDao = jobDao;
        this.resumeDao = resumeDao;
        this.startupDao = startupDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }


    //    shows all the startup  in the table
    @GetMapping("/showpage")
    public String showpage(Model vmodel){
        vmodel.addAttribute("allstartups",startupDao.findAll());
        return "startups/showpage";
    }


    //    show one startup and details
    @GetMapping("/showpage/{id}")
    public String showOne(@PathVariable Long id,Model vmodel) {
        Startup startup = startupDao.findOne(id);


//sends email to the company owner
        System.out.println( "this is the :"+startup.getUser().getEmail());
        emailService.prepareAndSend(startup, "resume uploaded","some one is intrested");





        vmodel.addAttribute("oneStartup", startup);
        return "startups/showone";
    }

    @GetMapping("/create-job-posting")
    public String jobPostingForm(Model model){
        model.addAttribute("job", new Job());
        return "startups/create-job-posting";
    }

    @PostMapping("/create-job-posting")
    public String submitJobPosting(@ModelAttribute Job job){
        job.setStartup(startupDao.findOne(1l));
        jobDao.save(job);
        return "startups/create-job-posting";
    }



    



}
