package com.sastartup.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "resumes")
public class Resume {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String path;

    @OneToOne(mappedBy = "resume")
    private User owner;

    @ManyToMany(mappedBy = "resumes")
    private List<Job> jobs;

    public Resume() {
    }

    public Resume(String path, User owner, List<Job> jobs) {
        this.path = path;
        this.owner = owner;
        this.jobs = jobs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

}
