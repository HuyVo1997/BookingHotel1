package com.bookinghotel.service;

import com.bookinghotel.model.TypeRoom;

import java.util.List;

public interface typeroomService {
    List<TypeRoom> getAllTypeRooms();
    TypeRoom findTypeRoomById(Integer id);
}
