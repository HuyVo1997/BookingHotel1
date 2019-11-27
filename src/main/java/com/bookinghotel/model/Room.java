package com.bookinghotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="room")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomid")
    private int roomid;

    @Column(name="image")
    private String image;

    @Column(name="description")
    private String description;

    @Column(name="title")
    private String title;

    @Column(name="numofadults",nullable = true)
    private Integer numofadults;

    @Column(name="numofchild")
    private Integer numofchild;

    @Column(name="numofbed")
    private Integer numofbed;

    @Column(name="roomfootage")
    private Integer roomfootage;

    @Column(name="price")
    private Double price;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="hotelid")
    @JsonIgnore
    private Hotel hotel;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "room_service",
            joinColumns = @JoinColumn(name = "roomid"),
            inverseJoinColumns = @JoinColumn(name = "serviceid")
    )

    private Set<Service> roomservices;

    @ManyToOne
    @JoinColumn(name= "typeid")
    @JsonIgnore
    private TypeRoom typeroom;

    @Column(name = "numroom")
    private Integer numroom;

    public Integer getNumroom() {
        return numroom;
    }

    public void setNumroom(Integer numroom) {
        this.numroom = numroom;
    }

    public Room() {
    }

    public Set<Service> getRoomservices() {
        return roomservices;
    }

    public void setRoomservices(Set<Service> roomservices) {
        this.roomservices = roomservices;
    }

    public Room(String image, String description, String title, Integer numofadults, Integer numofchild,
                Integer numofbed, Integer roomfootage,
                Double price, Hotel hotel, Set<Service> roomservices, TypeRoom typeroom, Integer numroom) {
        this.image = image;
        this.description = description;
        this.title = title;
        this.numofadults = numofadults;
        this.numofchild = numofchild;
        this.numofbed = numofbed;
        this.roomfootage = roomfootage;
        this.price = price;
        this.hotel = hotel;
        this.roomservices = roomservices;
        this.typeroom = typeroom;
        this.numroom = numroom;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumofadults() {
        return numofadults;
    }

    public void setNumofadults(Integer numofadults) {
        this.numofadults = numofadults;
    }

    public Integer getNumofchild() {
        return numofchild;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNumofchild(Integer numofchild) {
        this.numofchild = numofchild;
    }

    public TypeRoom getTyperoom() {
        return typeroom;
    }

    public void setTyperoom(TypeRoom typeroom) {
        this.typeroom = typeroom;
    }

    public Integer getNumofbed() {
        return numofbed;
    }

    public void setNumofbed( Integer numofbed) {
        this.numofbed = numofbed;
    }

    public Integer getRoomfootage() {
        return roomfootage;
    }

    public void setRoomfootage(Integer roomfootage) {
        this.roomfootage = roomfootage;
    }


}
