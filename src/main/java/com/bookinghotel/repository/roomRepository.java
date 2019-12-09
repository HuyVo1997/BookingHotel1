package com.bookinghotel.repository;

import com.bookinghotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface roomRepository extends JpaRepository<Room,Integer> {
    List<Room> findRoomsByHotel_Hotelid(Integer id);
}
