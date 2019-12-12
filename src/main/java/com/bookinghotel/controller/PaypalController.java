package com.bookinghotel.controller;

import com.bookinghotel.filter.Encryptor;
import com.bookinghotel.model.Booking;
import com.bookinghotel.model.Hotel;
import com.bookinghotel.model.Room;
import com.bookinghotel.model.User;
import com.bookinghotel.repository.userRepository;
import com.bookinghotel.service.PaypalService;
import com.bookinghotel.service.bookingService;
import com.bookinghotel.service.hotelService;
import com.bookinghotel.service.roomService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Random;

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

    @Autowired
    JavaMailSender javaMailSender;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findUserByEmail(email);
        try {
            byte[] array = new byte[7];
            new Random().nextBytes(array);
            String secretKey = new String(array, Charset.forName("UTF-8"));
            authentication(model);
            Payment payment = paypalService.excutePayment(paymentId,payerId);
            String saleid = Encryptor.encrypt(payment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId(),secretKey);
            Booking booking;
            if(user != null){
                if(room.getRefund() == 1){
                    booking = new Booking("hotel",user,room,room.getTyperoom().getType(),hotelController.locationText,
                            hotelController.dateStart,hotelController.dateEnd,priceTotal,1,numRoom, saleid, secretKey);
                }
                else {
                    booking = new Booking("hotel",user,room,room.getTyperoom().getType(),hotelController.locationText,
                            hotelController.dateStart,hotelController.dateEnd,priceTotal,1,numRoom, null, null);
                }
                try{
                    sendEmailWithAttachment();
                }catch (MessagingException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            else{
                int iduser = 0;
                if(room.getRefund() == 1){
                    booking = new Booking("hotel",null,room,room.getTyperoom().getType(),hotelController.locationText,
                            hotelController.dateStart,hotelController.dateEnd,priceTotal,1,numRoom, saleid, secretKey);
                }
                else {
                    booking = new Booking("hotel",null,room,room.getTyperoom().getType(),hotelController.locationText,
                            hotelController.dateStart,hotelController.dateEnd,priceTotal,1,numRoom, null, null);
                }
            }
            Room roomCheck = roomService.findRoomById(hotelController.roomId);
            if(roomCheck.getNumroom() <= 0){
                payment.setState("failed");
            }
            else{
                bookingService.saveBooking(booking);
                room.setNumroom(room.getNumroom() - numRoom);
                roomService.updateRoom(room);
            }
            if(payment.getState().equals("approved")){
                return "success-payment";
            }
            if(payment.getState().equals("failed")){
                return "cancle";
            }
        }
        catch (PayPalRESTException e){
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping(value="/refund/{id}",method = RequestMethod.GET)
    public String refundPay(@PathVariable String id) throws PayPalRESTException {
        paypalService.refund(id);
        return "redirect:/user/booking-history";
    }

    void sendEmailWithAttachment() throws MessagingException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email);

        helper.setSubject("Testing from Spring Boot");

        helper.setText("<h1>Booking Success!</h1>", true);

        javaMailSender.send(msg);

    }
}
