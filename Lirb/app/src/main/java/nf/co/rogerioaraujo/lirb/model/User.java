package nf.co.rogerioaraujo.lirb.model;

import java.sql.Date;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String name;
    private Date dateRegister;
    private Status status;

    public User() {
    }

    public User(int id, String username, String email, String password,
                String name, Date dateRegister, Status status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateRegister = dateRegister;
        this.status = status;
    }

    public User(String username, String email, String password,
                String name, Date dateRegister, Status status) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dateRegister = dateRegister;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
