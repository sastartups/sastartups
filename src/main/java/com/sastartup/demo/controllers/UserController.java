package com.sastartup.demo.controllers;

import com.sastartup.demo.models.*;
import com.sastartup.demo.repositories.JobRepo;
import com.sastartup.demo.repositories.ResumeRepo;
import com.sastartup.demo.repositories.StartupRepo;
import com.sastartup.demo.repositories.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;


//    constructor


    public UserController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao,EmailService emailService, PasswordEncoder passwordEncoder) {
        this.jobDao = jobDao;
        this.resumeDao = resumeDao;
        this.startupDao = startupDao;
        this.userDao = userDao;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;

    }



    @GetMapping("/sign-up")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "users/signup-form";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/create/startup")
    public String createStartupForm(Model model) {
        model.addAttribute("startup", new Startup());
        return "users/create-startup";
    }

    @PostMapping("/create/startup")
    public String submitStartupForm(@ModelAttribute Startup startup) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        startup.setUser(dbUser);

//        System.out.println("here!!!!"+startup.getProfile_img());
//          System.out.println(startup.getName());
        startupDao.save(startup);

        return "users/applyalert";

    }


//    edit startup
    @GetMapping("/startup/{id}/edit")
    public String editstartup(@PathVariable Long id,Model vmodel){
        Startup startup = startupDao.findOne(id);
        vmodel.addAttribute("startup",startup);
        return "startups/editstartup";
    }

    @PostMapping("/startup/{id}/edit")
    public String editedstartup(@ModelAttribute Startup edit,@PathVariable Long id){

        User sessionuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbuser = userDao.findOne(sessionuser.getId());

        edit.setUser(userDao.findOne(dbuser.getId()));
        startupDao.save(edit);
        return "redirect:/userProfile";
    }



    //    delete startup
    @GetMapping("/startup/{id}/delete")
    public String deleteform(@PathVariable Long id, Model vmodel){

        Startup startup = startupDao.findOne(id);
        vmodel.addAttribute("startup",startup);
        return ("startups/deletestartup");
    }

    @PostMapping("/startup/{id}/delete")
    public String delete(@PathVariable Long id){
        startupDao.delete(id);
        return "redirect:/userProfile";
    }

    @GetMapping("/submit-resume")
    public String resumeForm(Model model) {
        model.addAttribute("resume", new Resume());
        return "users/resume";
    }

    @PostMapping("/submit-resume")
    public String submitResume(@ModelAttribute Resume resume) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        resume.setOwner(dbUser);
        resumeDao.save(resume);
        return "users/applyalert";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        model.addAttribute("user", dbUser);
        model.addAttribute("resumes", resumeDao.findByOwner(dbUser));
//        model.addAttribute("applicants", )
        return "users/userProfile";
    }

    @PostMapping("/apply/{id}")
    public String easyApply(@PathVariable long id) {
        Job job = jobDao.findOne(id);
        List<Resume> jobResumes = job.getResumes();
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        Resume userResume = resumeDao.findByOwnerId(dbUser.getId());
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
