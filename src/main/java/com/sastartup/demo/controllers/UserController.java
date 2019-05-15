package com.sastartup.demo.controllers;

import com.sastartup.demo.models.Job;
import com.sastartup.demo.models.Resume;
import com.sastartup.demo.models.Startup;
import com.sastartup.demo.models.User;
import com.sastartup.demo.repositories.JobRepo;
import com.sastartup.demo.repositories.ResumeRepo;
import com.sastartup.demo.repositories.StartupRepo;
import com.sastartup.demo.repositories.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


@Controller
public class UserController {

    private final JobRepo jobDao;
    private final ResumeRepo resumeDao;
    private final StartupRepo startupDao;
    private final UserRepo userDao;

//    constructor


    public UserController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao) {
        this.jobDao = jobDao;
        this.resumeDao = resumeDao;
        this.startupDao = startupDao;
        this.userDao = userDao;
    }



    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "users/signup-form";
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute User user) {
        userDao.save(user);
        return "users/signup-form";

    }

    @GetMapping("/create/startup")
    public String createStartupForm(Model model) {
        model.addAttribute("startup", new Startup());
        return "users/create-startup";
    }

    @PostMapping("/create/startup")
    public String submitStartupForm(@ModelAttribute Startup startup) {
        startup.setUser(userDao.findOne(1l));
        startupDao.save(startup);
        return "users/create-startup";

    }

    @GetMapping("/submit-resume")
    public String resumeForm(Model model) {
        model.addAttribute("resume", new Resume());
        return "users/resume";
    }

    @PostMapping("/submit-resume")
    public String submitResume(@ModelAttribute Resume resume) {
        resume.setOwner(userDao.findOne(1l));
        resumeDao.save(resume);
        return "users/resume";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model) {
        model.addAttribute("user", userDao.findOne(1L));
        model.addAttribute("resume", resumeDao.findOne(1l));
        return "users/userProfile";
    }

    @PostMapping("/apply/{id}")
    public String easyApply(@PathVariable long id) {
        Job job = jobDao.findOne(id);
        List<Resume> jobResumes = job.getResumes();
        Resume userResume = resumeDao.findOne(userDao.findOne(1l).getId());
        jobResumes.add(userResume);
        jobDao.save(job);
        return "startups/index";
    }

}
