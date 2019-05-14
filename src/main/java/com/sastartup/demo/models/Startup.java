package com.sastartup.demo.models;

import jdk.internal.jline.internal.Urls;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "startups")
public class Startup {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false,unique = true)
    private String company_namel;

    @Column(nullable = false,length = 2000)
    private String description;

    @Column(nullable = false,unique = true)
    private String adress;

    @Column
    private String profile_img;

    @Column
    private String cover_img;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "startup")
    private List<Job> jobs;


}
