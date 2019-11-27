package com.bookinghotel.serviceimplement;

import com.bookinghotel.model.Booking;
import com.bookinghotel.repository.bookingRepository;
import com.bookinghotel.service.bookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class bookingServiceImp implements bookingService {
    @Autowired
    bookingRepository bookingRepository;

    @Override
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }
}
