package com.bookinghotel.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "hotel",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Room> rooms;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "hotel_service",
            joinColumns = @JoinColumn(name = "hotelid"),
            inverseJoinColumns = @JoinColumn(name = "serviceid")
    )
    private Set<Service> hotelservices;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bussinessid")
    private Bussiness bussiness;

    @Column(name = "status")
    private Integer status;

    public Hotel(String name, String location, Double price, String image, Integer reviews,
                 Double rate, Integer star, String email, String phone, Set<Room> rooms,
                 Set<Service> hotelservices, Bussiness bussiness, Integer status) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.image = image;
        this.reviews = reviews;
        this.rate = rate;
        this.star = star;
        this.email = email;
        this.phone = phone;
        this.rooms = rooms;
        this.hotelservices = hotelservices;
        this.bussiness = bussiness;
        this.status = status;
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

    public Bussiness getBussiness() {
        return bussiness;
    }
    public void setBussiness(Bussiness bussiness) {
        this.bussiness = bussiness;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
