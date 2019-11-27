package com.bookinghotel.filter;

import com.bookinghotel.model.Room;
import com.bookinghotel.model.Service;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class SearchCriteria {
    String location;

    Double price;

    Integer star;

    String roomservices;

    String hotelservices;

    public String getHotelservices() {
        return hotelservices;
    }

    public void setHotelservices(String hotelservices) {
        this.hotelservices = hotelservices;
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

    public String getRoomservices() {
        return roomservices;
    }

    public void setRoomservices(String roomservices) {
        this.roomservices = roomservices;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }
}
