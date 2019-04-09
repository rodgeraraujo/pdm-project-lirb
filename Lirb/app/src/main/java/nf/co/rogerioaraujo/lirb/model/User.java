package nf.co.rogerioaraujo.lirb.model;

import java.sql.Date;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String description;
    private String picture;
    private Date dateRegister;
    private Status status;

    public User() {
    }

    public User(int id, String username, String email, String password,
                String name, String description, String picture, Date dateRegister, Status status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.description = description;
        this.picture = picture;
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

    public User(String username, String name, String description, String picture) {
        this.username = username;
        this.name = name;
        this.description = description;
        this.picture = picture;

    }

    public User(String name, String description, String picture) {
        this.name = name;
        this.description = description;
        this.picture = picture;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String
    toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", dateRegister=" + dateRegister +
                ", status=" + status +
                '}';
    }
}
