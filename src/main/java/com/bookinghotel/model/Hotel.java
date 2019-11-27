package com.bookinghotel.model;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.springframework.security.core.Transient;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Set;

@Entity
@Table(name="hotel")
@Transactional
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotelid")
    private int hotelid;
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "price")
    private Double price;
    @Column(name="image")
    private String image;
    @Column(name="reviews")
    private Integer reviews;
    @Column(name="rate")
    private Double rate;
    @Column(name="star")
    private Integer star;

    @OneToMany(mappedBy = "hotel",fetch=FetchType.LAZY)
    private Set<Room> rooms;

    @ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "hotel_service",
            joinColumns = @JoinColumn(name = "hotelid"),
            inverseJoinColumns = @JoinColumn(name = "serviceid")
    )
    private Set<Service> hotelservices;

    public Hotel(String name, String location, Double price, String image,
                 Integer reviews, Double rate, Integer star, Set<Room> rooms, Set<Service> hotelservices) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.image = image;
        this.reviews = reviews;
        this.rate = rate;
        this.star = star;
        this.rooms = rooms;
        this.hotelservices = hotelservices;
    }

    public Hotel(){}

    public int getHotelid() {
        return hotelid;
    }

    public void setHotelid(int hotelid) {
        this.hotelid = hotelid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Service> getHotelservices() {
        return hotelservices;
    }

    public void setHotelservices(Set<Service> hotelservices) {
        this.hotelservices = hotelservices;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    //    @Column(name="generate")
//    protected String mediaFileName;
//    public String generateBase64Image()
//    {
//        return Base64.encodeBase64String(this.getImage());
//    }

//    @Lob
//    @Column(name = "image")
//    private byte[] image;
//    public Hotel(){
//    }

}
