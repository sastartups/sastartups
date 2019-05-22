package com.sastartup.demo.models;


import javax.persistence.*;

@Entity
@Table(name="notifications")
public class Notification {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Notification(){}

    public Notification(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
