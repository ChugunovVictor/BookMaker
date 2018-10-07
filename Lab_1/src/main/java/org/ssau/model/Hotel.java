package org.ssau.model;

import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "C_HOTEL")
public class Hotel implements Identifiable<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    String name;

    @Column
    int stars;

    public Hotel(){}

    public Hotel(String name, int stars) {
        this.name = name;
        this.stars = stars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
