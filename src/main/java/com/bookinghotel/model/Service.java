package com.bookinghotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.Transient;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="serviceid")
    private int serviceid;

    @Column(name="name")
    private String name;

    @Column(name="image")
    private String image;

    @ManyToMany(mappedBy = "hotelservices",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Hotel> hotels;

    @ManyToMany(mappedBy = "roomservices",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Room> rooms;

    public Service(){

    }

    public Service(String name){
        this.name = name;
    }

    public Service(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public int getServiceid() {
        return serviceid;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(Set<Hotel> hotels) {
        this.hotels = hotels;
    }
}
