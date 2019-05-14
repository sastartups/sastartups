package com.sastartup.demo.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false,unique = true)
    private String user_name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Startup> startups;


}
