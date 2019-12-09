package com.bookinghotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "booking_history")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingid")
    private Integer bookingid;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "roomid")
    private Room room;

    @Column(name = "title")
    private String title;

    @Column(name = "location")
    private String location;

    @Column(name = "orderdate")
    private String orderdate;

    @Column(name = "executiondate")
    private String executiondate;

    @Column(name = "price")
    private Double price;

    @Column(name = "current")
    private Integer current;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="codetransaction")
    private String codetransaction;

    @Column(name = "secretkey")
    private String secretkey;

    public Booking() {
    }

    public Booking(String type, User user, Room room, String title, String location,
                   String orderdate, String executiondate, Double price, Integer current,
                   Integer quantity, String codetransaction, String secretkey) {
        this.type = type;
        this.user = user;
        this.room = room;
        this.title = title;
        this.location = location;
        this.orderdate = orderdate;
        this.executiondate = executiondate;
        this.price = price;
        this.current = current;
        this.quantity = quantity;
        this.codetransaction = codetransaction;
        this.secretkey = secretkey;
    }

    public String getCodetransaction() {
        return codetransaction;
    }

    public void setCodetransaction(String codetransaction) {
        this.codetransaction = codetransaction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getExecutiondate() {
        return executiondate;
    }

    public void setExecutiondate(String executiondate) {
        this.executiondate = executiondate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getBookingid() {
        return bookingid;
    }

    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
