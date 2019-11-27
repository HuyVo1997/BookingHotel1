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

    @Column(name="userid")
    private Integer userid;

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

    public Booking() {
    }

    public Booking(String type, Integer userid, String title, String location,
                   String orderdate, String executiondate, Double price, Integer current) {
        this.type = type;
        this.userid = userid;
        this.title = title;
        this.location = location;
        this.orderdate = orderdate;
        this.executiondate = executiondate;
        this.price = price;
        this.current = current;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

}
