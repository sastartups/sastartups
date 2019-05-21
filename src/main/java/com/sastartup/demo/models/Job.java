package com.sastartup.demo.models;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "jobs")
public class Job {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @ManyToOne @JoinColumn(name = "startup_id")
    private Startup startup;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable (
            name = "applications",
            joinColumns={@JoinColumn(name = "job_id")},
            inverseJoinColumns = {@JoinColumn(name = "resume_id")}
    )
    private List<Resume> resumes;

    public Job() {
    }

    public Job(String title, String description, Startup startup, List<Resume> resumes) {
        this.title = title;
        this.description = description;
        this.startup = startup;
        this.resumes = resumes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }
}
