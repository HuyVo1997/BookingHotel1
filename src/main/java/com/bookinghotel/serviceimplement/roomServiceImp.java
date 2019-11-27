package com.bookinghotel.serviceimplement;

import com.bookinghotel.model.Room;
import com.bookinghotel.repository.roomRepository;
import com.bookinghotel.service.roomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class roomServiceImp implements roomService {
    @Autowired
    roomRepository roomRepository;

    @Override
    public List<Room> findAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public Room findRoomById(int id) {
        return roomRepository.findById(id).get();
    }

    @Override
    public void updateRoom(Room room) {
        roomRepository.save(room);
    }
}
