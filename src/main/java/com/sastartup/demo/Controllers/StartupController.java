package com.sastartup.demo.Controllers;


import com.sastartup.demo.Models.Job;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartupController {


    @GetMapping("/create-job-posting")
    public String jobPostingForm(Model model){
        model.addAttribute("job", new Job());
        return "startups/create-job-posting";
    }
}
