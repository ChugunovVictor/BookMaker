package org.ssau.model;

import org.springframework.hateoas.Identifiable;

import javax.persistence.*;

@Entity
@Table(name = "C_USER")
public class User implements Identifiable<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    String login;

    @Column(nullable = false)
    String password;

    @Column(columnDefinition = "boolean default false")
    boolean isAdmin;

    public User(){}

    public User(String login, String pass, boolean isAdmin) {
        this.login = login;
        this.password = pass;
        this.isAdmin = isAdmin;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
