package com.sastartup.demo.controllers;


import com.sastartup.demo.models.Job;

import com.sastartup.demo.models.Startup;

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

//    constructor


    public StartupController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao) {
        this.jobDao = jobDao;
        this.resumeDao = resumeDao;
        this.startupDao = startupDao;
        this.userDao = userDao;
    }


    private final JobRepo jobDao;
    private final ResumeRepo resumeDao;
    private final StartupRepo startupDao;
    private final UserRepo userDao;

//    constructor


    public StartupController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao) {
        this.jobDao = jobDao;
        this.resumeDao = resumeDao;
        this.startupDao = startupDao;
        this.userDao = userDao;
    }


    @GetMapping("/create-job-posting")
    public String jobPostingForm(Model model){
        model.addAttribute("job", new Job());
        return "startups/create-job-posting";
    }



    //    shows all the startup  in the table
    @GetMapping("/showpage")
    public String showpage(Model vmodel){
        vmodel.addAttribute("allstartups",startupDao.findAll());
        return "startups/showpage";
    }


    //    show one startup and details
    @GetMapping("/showpage/{id}")
    public String showOne(@PathVariable Long id,Model vmodel){
        Startup startup = startupDao.findOne(id);
        vmodel.addAttribute("oneStartup",startup);
        return "startups/showone";
        
    @PostMapping("/create-job-posting")
    public String submitJobPosting(@ModelAttribute Job job){
        job.setStartup(startupDao.findOne(1l));
        jobDao.save(job);
        return "startups/create-job-posting";
    }




}
