package com.bookinghotel.model;

import javax.persistence.*;

@Entity
@Table(name = "city")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matp")
    private String matp;

    @Column(name = "name")
    private String name;

    @Column(name="type")
    private String type;

    public Location() {
    }

    public Location(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getMatp() {
        return matp;
    }

    public void setMatp(String matp) {
        this.matp = matp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
