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
import java.nio.file.Path;
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


    public UserController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao, EmailService emailService, PasswordEncoder passwordEncoder) {
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
    public String saveUser(@ModelAttribute User user) {
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
        if(startup.getProfile_img().equals("")) {
            startup.setProfile_img("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        }
        if(startup.getCover_img().equals("")){
            startup.setCover_img("https://coverfiles.alphacoders.com/372/37214.jpg");
        }

//        System.out.println("here!!!!"+startup.getProfile_img());
//          System.out.println(startup.getName());
        startupDao.save(startup);

        return "redirect:/userProfile";

    }


    //    edit startup
    @GetMapping("/startup/{id}/edit")
    public String editstartup(@PathVariable Long id, Model vmodel) {
        Startup startup = startupDao.findOne(id);
        vmodel.addAttribute("startup", startup);
        return "startups/editstartup";
    }

    @PostMapping("/startup/{id}/edit")
    public String editedstartup(@ModelAttribute Startup edit, @PathVariable Long id) {

        User sessionuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbuser = userDao.findOne(sessionuser.getId());

        edit.setUser(userDao.findOne(dbuser.getId()));
        startupDao.save(edit);
        return "redirect:/userProfile";
    }


    //    delete startup
    @GetMapping("/startup/{id}/delete")
    public String deleteform(@PathVariable Long id, Model vmodel) {

        Startup startup = startupDao.findOne(id);
        vmodel.addAttribute("startup", startup);
        return ("startups/deletestartup");
    }

    @PostMapping("/startup/{id}/delete")
    public String delete(@PathVariable Long id) {
        startupDao.delete(id);
        return "redirect:/userProfile";
    }

//    @GetMapping("/submit-resume")
//    public String resumeForm(Model model) {
//        model.addAttribute("resume", new Resume());
//        return "users/resume";
//    }

    @PostMapping("/submit-resume")
    public String submitResume(@ModelAttribute Resume resume) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        resume.setOwner(dbUser);
        dbUser.setResume(resume);

        if (resume.getPath() == "" || resume.toString().length() == 0) {
            return "redirect:/submit-resume";
        } else {
            resumeDao.save(resume);
            userDao.save(dbUser);
        }

        return "redirect:/userProfile";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        model.addAttribute("resume", new Resume());
        model.addAttribute("user", dbUser);
//        model.addAttribute("resumes", resumeDao.findByOwner(dbUser));
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
        System.out.println(job.getTitle());
        emailService.prepareAndSend(
                job.getStartup(),
                "GrowTechSA.com",

                "startup name: " + job.getStartup().getName() + "\n"
                        + "job title: " + job.getTitle() + "\n"
                        + "first name: " + userResume.getOwner().getFirst_name() + "\n"
                        + "last name: " + userResume.getOwner().getLast_name() + "\n"
                        + "resume link : " + userResume.getPath() + "\n"


        );

        return "redirect:/userProfile";
    }

    @GetMapping("/edit")
    public String showUserEditPage(Model model) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        model.addAttribute("user", dbUser);
        return "users/edit-user-profile";
    }

    @PostMapping("/edit")
    public String submitUserEditPage(@ModelAttribute User updatedUser) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        updatedUser.setId(dbUser.getId());
        if (updatedUser.getPassword() == null) {
            updatedUser.setPassword(dbUser.getPassword());
        } else {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        userDao.save(updatedUser);
        return "redirect:/userProfile";
    }


    //ARASH try to see if you can incorporate the email service here to email the resume owner that the startup owner is not interested
    @PostMapping("/resume/{jobId}/pass/{resumeId}")
    public String notInterestedApplication(@PathVariable long jobId, @PathVariable long resumeId){
        Job job = jobDao.findOne(jobId);
        List<Resume> resumes = job.getResumes();
        for(Iterator<Resume> resume  = resumes.iterator(); resume.hasNext();){
            Resume r = resume.next();
            if(r.getId() == resumeId){
                resume.remove();
            }
        }
        job.setResumes(resumes);
        jobDao.save(job);
        return "redirect:/userProfile";
    }




}
