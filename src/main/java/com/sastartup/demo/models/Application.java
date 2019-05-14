package com.sastartup.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "applications")
public class Application {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne @JoinColumn(name = "resume_id")
    private Resume resume;


}
