package com.bookinghotel.service;

import com.bookinghotel.model.Hotel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface hotelService {
    List<Hotel> findAllhotel();
    Hotel findHotelById(int id);
    void saveOrUpdate(Hotel hotel);
    void delete(int id);
}
