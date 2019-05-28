package com.sastartup.demo.controllers;


import com.sastartup.demo.models.*;

import com.sastartup.demo.repositories.*;

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
    private final NotificationRepo notificationRepo;

//    constructor


    public StartupController(JobRepo jobDao, ResumeRepo resumeDao, StartupRepo startupDao, UserRepo userDao, NotificationRepo notificationRepo) {
        this.jobDao = jobDao;
        this.resumeDao = resumeDao;
        this.startupDao = startupDao;
        this.userDao = userDao;
        this.notificationRepo = notificationRepo;

    }


    //    shows all the startup  in the table
    @GetMapping("/showpage")
    public String showpage(Model vmodel) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User dbUser = userDao.findOne(sessionUser.getId());
            vmodel.addAttribute("user", dbUser);
            vmodel.addAttribute("count", notificationRepo.countByUser(dbUser));
            vmodel.addAttribute("navNotifications", notificationRepo.findTop4ByUserOrderByIdDesc(dbUser));


        } else {
            vmodel.addAttribute("user", null);
        }
        vmodel.addAttribute("allstartups", startupDao.findAll());
        return "startups/showpage";
    }


    //    show one startup and details
    @GetMapping("/showpage/{id}")
    public String showOne(@PathVariable Long id, Model vmodel) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User dbUser = userDao.findOne(sessionUser.getId());
            vmodel.addAttribute("user", dbUser);
            vmodel.addAttribute("navNotifications", notificationRepo.findTop4ByUserOrderByIdDesc(dbUser));
            vmodel.addAttribute("count", notificationRepo.countByUser(dbUser));


        } else {
            vmodel.addAttribute("user", null);
        }
        Startup startup = startupDao.findOne(id);
        vmodel.addAttribute("oneStartup", startup);
        return "startups/showone";
    }

    //    create job
    @GetMapping("/create/{id}/job")
    public String jobPostingForm(Model model, @PathVariable Long id) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        model.addAttribute("user", dbUser);
        model.addAttribute("count", notificationRepo.countByUser(dbUser));
        model.addAttribute("startupId", id);
        return "startups/create-job-posting";
    }


    @PostMapping("/create/{id}/job")
    public String submitJobPosting(@RequestParam String title, @RequestParam String description, @PathVariable Long id, Model model) {
        if (title.equals("")
                || description.equals("")) {
            return "startups/create-job-posting";

        }
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        model.addAttribute("user", dbUser);
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
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        vmodel.addAttribute("user", dbUser);
        vmodel.addAttribute("count", notificationRepo.countByUser(dbUser));
        Job job = jobDao.findOne(id);
        vmodel.addAttribute("job", job);
        return ("startups/deletejob");
    }

    @PostMapping("/job/{id}/delete")
    public String delete(@PathVariable Long id) {
        System.out.println(jobDao.findOne(id).getStartup().getId());
        Long startupId = jobDao.findOne(id).getStartup().getId();
        Job job = jobDao.findOne(id);
        for (Resume resume : job.getResumes()) {
            User user = resume.getOwner();
            Notification notification = new Notification("" + job.getStartup().getName() + " has closed their job search for position " + job.getTitle() +
                    ". Thank you for your interest.", user);
            notificationRepo.save(notification);
        }

        jobDao.delete(id);


        return "redirect:/showpage/" + startupId;
    }


    //    edit job
    @GetMapping("/job/{id}/edit")
    public String editform(@PathVariable Long id, Model vmodel) {
        User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userDao.findOne(sessionUser.getId());
        vmodel.addAttribute("user", dbUser);
        vmodel.addAttribute("navNotifications", notificationRepo.findTop4ByUserOrderByIdDesc(dbUser));
        vmodel.addAttribute("count", notificationRepo.countByUser(dbUser));


        Job job = jobDao.findOne(id);
        vmodel.addAttribute("job", job);
        return ("startups/editjob");
    }

    @PostMapping("/job/{id}/edit")
    public String edit(@ModelAttribute Job jobtoedit, @PathVariable Long id, Model model) {

        User sessionuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User db_user = userDao.findOne(sessionuser.getId());
        model.addAttribute("user", db_user);
        jobtoedit.setStartup(startupDao.findOne(db_user.getId()));
        Job oldJob = jobDao.findOne(id);
        jobtoedit.setId(oldJob.getId());
        jobtoedit.setStartup(oldJob.getStartup());
        jobDao.save(jobtoedit);


        return "redirect:/showpage/" + jobtoedit.getStartup().getId();
    }

    @GetMapping("/all/jobs")
    public String viewAllJobs(@RequestParam(defaultValue = "") String searchFor, Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User sessionUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User dbUser = userDao.findOne(sessionUser.getId());
            model.addAttribute("user", dbUser);
            model.addAttribute("navNotifications", notificationRepo.findTop4ByUserOrderByIdDesc(dbUser));
            model.addAttribute("count", notificationRepo.countByUser(dbUser));


        } else {
            model.addAttribute("user", null);
        }

        if (searchFor.toString().equals("")) {
            model.addAttribute("jobs", jobDao.findAll());
        } else if (!searchFor.toString().equals("")) {
            if (jobDao.findByTitleIgnoreCaseContaining(searchFor).size() == 0) {
                model.addAttribute("jobs", jobDao.findAll());
            } else {
                model.addAttribute("jobs", jobDao.findByTitleIgnoreCaseContaining(searchFor));
            }

        }
        System.out.println(jobDao.findAll());
        return "startups/alljobs";
    }

}
