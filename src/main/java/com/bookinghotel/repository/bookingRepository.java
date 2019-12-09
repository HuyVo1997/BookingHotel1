package com.bookinghotel.repository;

import com.bookinghotel.model.Booking;
import com.bookinghotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface bookingRepository extends JpaRepository<Booking,String> {
    List<Booking> findBookingsByUser(User user);
    Booking findBookingByCodetransaction(String code);

}
