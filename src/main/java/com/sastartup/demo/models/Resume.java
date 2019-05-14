package com.sastartup.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "resumes")
public class Resume {

    @Id @GeneratedValue
    private Long id;

    @Column
    private String resume;

    @OneToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resume")
    private List<Resume> resumes;
}
