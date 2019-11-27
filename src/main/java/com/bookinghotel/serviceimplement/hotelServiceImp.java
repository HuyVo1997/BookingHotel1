package com.bookinghotel.serviceimplement;

import com.bookinghotel.model.Hotel;
import com.bookinghotel.model.Room;
import com.bookinghotel.repository.hotelRepository;
import com.bookinghotel.repository.roomRepository;
import com.bookinghotel.service.hotelService;
import com.sun.tools.javac.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class hotelServiceImp implements hotelService {

    @Autowired
    hotelRepository hotelRepository;

    @Autowired
    roomRepository roomRepository;

    private List<Hotel> hotels;

    @Override
    public List<Hotel> findAllhotel() {
        return hotelRepository.findAll();
    }

    public List<Hotel> findHotelByRate(int Rate){
        List<Hotel> result = hotels.stream()
                .filter( x -> x.getRate() <= Rate)
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public Hotel findHotelById(int id) {
        return hotelRepository.findById(id).get();
    }

    @Override
    public void saveOrUpdate(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public void delete(int id) {
        hotelRepository.deleteById(id);
    }

}
