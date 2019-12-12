package com.bookinghotel.controller;
import com.bookinghotel.filter.AjaxReponseBody;
import com.bookinghotel.model.User;
import com.bookinghotel.repository.hotelRepository;
import com.bookinghotel.repository.locationRepository;
import com.bookinghotel.repository.roomRepository;
import com.bookinghotel.repository.userRepository;
import com.bookinghotel.security.CustomAuthenticationSuccessHandler;
import com.bookinghotel.service.PaypalService;
import com.bookinghotel.service.hotelService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Refund;
import com.paypal.api.payments.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class homeController {
    @Autowired
    hotelRepository hotelRepository;

    @Autowired
    hotelService hotelService;

    @Autowired
    userRepository userRepository;

    @Autowired
    roomRepository roomRepository;

    @Autowired
    PaypalService paypalService;

    @Autowired
    locationRepository locationRepository;


    public void authentication(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("Email",email);
        model.addAttribute("User",userRepository.findUserByEmail(email));
    }

    @RequestMapping(value="/")
    public String index(Model model){
        authentication(model);
        return "index";
    }

    @RequestMapping(value="/loadLocation",method = RequestMethod.POST)
    public ResponseEntity<?> loadLocation(@RequestParam(value="location") String location){
        AjaxReponseBody result = new AjaxReponseBody();
        result.setLocationResult(locationRepository.findLocationsByNameContaining(location));
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="/login")
    public String login(Principal principal, HttpServletRequest request) throws Exception{
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME,referer);
        return principal == null ?  "login-register" : "redirect:/";
    }

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public String register(@RequestParam("firstname") String firstname,
                           @RequestParam("lastname") String lastname,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return "redirect:/";
    }

    @RequestMapping(value="/admin/add")
    public String addHotel(){
        return "admin";
    }

    @RequestMapping(value="/403")
    public String accessDenied(){
        return "403";
    }
}
