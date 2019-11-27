package com.bookinghotel.controller;

import com.bookinghotel.model.Booking;
import com.bookinghotel.repository.bookingRepository;
import com.bookinghotel.repository.userRepository;
import com.bookinghotel.service.bookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class userController {

    @Autowired
    userRepository userRepository;

    @Autowired
    bookingService bookingService;

    @Autowired
    bookingRepository bookingRepository;

    public void authentication(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("Email",email);
        model.addAttribute("User",userRepository.findUserByEmail(email));
    }

    @RequestMapping(value="/user/profile",method = RequestMethod.GET)
    public String userProfile(Model model){
        authentication(model);
        return "user-profile";
    }

    @RequestMapping(value="/user/profile-setting",method = RequestMethod.GET)
    public String profileSetting(Model model){
        authentication(model);
        return "user-profile-settings";
    }

    @RequestMapping(value="/user/booking-history",method = RequestMethod.GET)
    public String bookingHistory(Model model){
        authentication(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("listBooking",
                bookingRepository.findBookingsByUserid(userRepository.findUserByEmail(email).getUserid()));
        return "user-profile-booking-history";
    }
}
