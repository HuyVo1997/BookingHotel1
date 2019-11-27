package com.bookinghotel.controller;

import com.bookinghotel.model.Booking;
import com.bookinghotel.model.Hotel;
import com.bookinghotel.model.Room;
import com.bookinghotel.model.User;
import com.bookinghotel.repository.bookingRepository;
import com.bookinghotel.repository.roomRepository;
import com.bookinghotel.repository.userRepository;
import com.bookinghotel.service.PaypalService;
import com.bookinghotel.service.bookingService;
import com.bookinghotel.service.hotelService;
import com.bookinghotel.service.roomService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaypalController {
    @Autowired
    PaypalService paypalService;

    @Autowired
    roomService roomService;

    @Autowired
    hotelService hotelService;

    @Autowired
    userRepository userRepository;

    @Autowired
    bookingService bookingService;

    public static final String SUCCESS_URL = "paypal/success";
    public static final String CANCLE_URL = "paypal/cancle";
    public Double priceTotal = 0.0;
    public Integer numRoom = 0;

    public void authentication(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("Email",email);
        model.addAttribute("User",userRepository.findUserByEmail(email));
    }

    @PostMapping("/paypal")
    public String payment(@RequestParam("priceTotal") Double total,
                          @RequestParam("numroom") Integer quantity){
        try {
            Payment payment = paypalService.createPayment(total,"USD",
                    "paypal","sale","Booking Hotel",
                    "http://localhost:8080/" + CANCLE_URL,
                    "http://localhost:8080/"  + SUCCESS_URL);
            priceTotal = total;
            numRoom = quantity;
            for (Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")){
                    return "redirect:" + link.getHref();
                }
            }
        }catch(PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping(value = CANCLE_URL)
    public String canclePay(){
        return "cancle";
    }

    @RequestMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             Model model){
        Room room = roomService.findRoomById(hotelController.roomId);
        Hotel hotel = hotelService.findHotelById(hotelController.hotelId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findUserByEmail(email);
        try {
            authentication(model);
            Payment payment = paypalService.excutePayment(paymentId,payerId);
            Booking booking = new Booking("hotel",user.getUserid(),room.getTyperoom().getType(),hotelController.locationText,
                    hotelController.dateStart,hotelController.dateEnd,priceTotal,1);
            bookingService.saveBooking(booking);
            room.setNumroom(room.getNumroom() - numRoom);
            roomService.updateRoom(room);
            System.out.println(payment.toJSON());
            if(payment.getState().equals("approved")){
                return "success-payment";
            }
        }
        catch (PayPalRESTException e){
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }
}
