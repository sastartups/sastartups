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


    public User() {
    }

    public User(String first_name, String last_name, String user_name, String password, String email, List<Startup> startups) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.startups = startups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Startup> getStartups() {
        return startups;
    }

    public void setStartups(List<Startup> startups) {
        this.startups = startups;
    }
}

