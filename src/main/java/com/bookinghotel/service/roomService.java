package com.bookinghotel.service;

import com.bookinghotel.model.Room;

import java.util.List;

public interface roomService {
    List<Room> findAllRoom();
    Room findRoomById(int id);
    void updateRoom(Room room);
}
