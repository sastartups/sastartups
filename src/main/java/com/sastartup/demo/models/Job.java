package com.sastartup.demo.models;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "jobs")
public class Job {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false,unique = true)
    private String job_title;

    @Column(nullable = false,unique = true,length = 2000)
    private String job_description;

    @ManyToOne @JoinColumn(name = "startup_id")
    private Startup startup;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    private List<Application> applications;

}
