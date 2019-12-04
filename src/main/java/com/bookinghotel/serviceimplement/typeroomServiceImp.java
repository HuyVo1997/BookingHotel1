package com.bookinghotel.serviceimplement;

import com.bookinghotel.model.TypeRoom;
import com.bookinghotel.repository.typeroomRepository;
import com.bookinghotel.service.typeroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class typeroomServiceImp implements typeroomService {

    @Autowired
    typeroomRepository typeroomRepository;

    @Override
    public List<TypeRoom> getAllTypeRooms() {
        return typeroomRepository.findAll();
    }

    @Override
    public TypeRoom findTypeRoomById(Integer id) {
        return typeroomRepository.findById(id).get();
    }
}
