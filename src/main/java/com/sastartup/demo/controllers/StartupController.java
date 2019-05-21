package com.sastartup.demo.controllers;


import com.sastartup.demo.models.*;

import com.sastartup.demo.repositories.JobRepo;
import com.sastartup.demo.repositories.ResumeRepo;
import com.sastartup.demo.repositories.StartupRepo;
import com.sastartup.demo.repositories.UserRepo;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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


    //    shows all the startup  in the table
    @GetMapping("/showpage")
    public String showpage(Model vmodel) {
        vmodel.addAttribute("allstartups", startupDao.findAll());
        return "startups/showpage";
    }


    //    show one startup and details
    @GetMapping("/showpage/{id}")
    public String showOne(@PathVariable Long id, Model vmodel) {
        Startup startup = startupDao.findOne(id);
        vmodel.addAttribute("oneStartup", startup);
        return "startups/showone";
    }

    //    create job
    @GetMapping("/create/{id}/job")
    public String jobPostingForm(Model model, @PathVariable Long id) {
        model.addAttribute("startupId", id);
        return "startups/create-job-posting";
    }


    @PostMapping("/create/{id}/job")
    public String submitJobPosting(@RequestParam String title, @RequestParam String description, @PathVariable Long id) {

        Job newjob = new Job();
        newjob.setDescription(description);
        newjob.setTitle(title);
        newjob.setStartup(startupDao.findOne(id));
        jobDao.save(newjob);

        return "redirect:/userProfile";
    }


    //    delete job
    @GetMapping("/job/{id}/delete")
    public String deleteform(@PathVariable Long id, Model vmodel) {
        Job job = jobDao.findOne(id);
        vmodel.addAttribute("job", job);
        return ("startups/deletejob");
    }

    @PostMapping("/job/{id}/delete")
    public String delete(@PathVariable Long id) {
        jobDao.delete(id);
//        System.out.println(jobDao.findOne(id).getStartup().getId());

        return "redirect:/showpage/" + jobDao.findOne(id).getStartup().getId();
    }


    //    edit job
    @GetMapping("/job/{id}/edit")
    public String editform(@PathVariable Long id, Model vmodel) {
        Job job = jobDao.findOne(id);
        vmodel.addAttribute("job", job);
        return ("startups/editjob");
    }

    @PostMapping("/job/{id}/edit")
    public String edit(@ModelAttribute Job jobtoedit, @PathVariable Long id) {

        User sessionuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User db_user = userDao.findOne(sessionuser.getId());

        jobtoedit.setStartup(startupDao.findOne(db_user.getId()));
        Job oldJob = jobDao.findOne(id);
        jobtoedit.setId(oldJob.getId());
        jobtoedit.setStartup(oldJob.getStartup());
        jobDao.save(jobtoedit);


        return "redirect:/showpage/" + jobtoedit.getStartup().getId();
    }

    @GetMapping("/all/jobs")
    public String viewAllJobs(@RequestParam(defaultValue = "") String searchFor, Model model) {
        if (searchFor.toString().equals("")) {
            model.addAttribute("jobs", jobDao.findAll());
        } else if (!searchFor.toString().equals("")) {
            if (jobDao.findByTitle(searchFor).size() == 0) {
                model.addAttribute("jobs", jobDao.findAll());
            }else {
                model.addAttribute("jobs", jobDao.findByTitle(searchFor));
            }

        }
        System.out.println(jobDao.findAll());
        return "startups/alljobs";
    }

}
