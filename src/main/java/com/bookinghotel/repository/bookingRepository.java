package com.bookinghotel.repository;

import com.bookinghotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface bookingRepository extends JpaRepository<Booking,String> {
    List<Booking> findBookingsByUserid(int id);
}
