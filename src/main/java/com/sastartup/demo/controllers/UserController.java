package com.sastartup.demo.controllers;

import com.sastartup.demo.models.*;
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

import javax.persistence.JoinColumn;
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
    private EmailService emailService;

//    constructor


    public UserController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao,EmailService emailService) {
        this.jobDao = jobDao;
        this.resumeDao = resumeDao;
        this.startupDao = startupDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }



    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "users/signup-form";
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute User user) {
        userDao.save(user);
        return "users/applyalert";

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
        return "users/applyalert";

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
        return "users/applyalert";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model) {
        model.addAttribute("user", userDao.findOne(1L));
        model.addAttribute("resumes", resumeDao.findByOwner(userDao.findOne(1l)));
//        model.addAttribute("applicants", )
        return "users/userProfile";
    }

    @PostMapping("/apply/{id}")
    public String easyApply(@PathVariable long id) {
        Job job = jobDao.findOne(id);
        List<Resume> jobResumes = job.getResumes();
        Resume userResume = resumeDao.findByOwnerId(userDao.findOne(1l).getId());
        jobResumes.add(userResume);
        jobDao.save(job);

        //sends email to the company owner on apply
        //System.out.println(userResume.getPath());
        emailService.prepareAndSend(
                job.getStartup(),
                "resume uploaded",
                "resume link : " + userResume.getPath() +"\n"
                + "first name: " + userResume.getOwner().getFirst_name() +"\n"
                + "last name: " + userResume.getOwner().getLast_name() +"\n"
        );

        return "users/applyalert";
    }


}
