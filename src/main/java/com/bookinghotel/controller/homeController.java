package com.bookinghotel.controller;
import com.bookinghotel.repository.hotelRepository;
import com.bookinghotel.repository.roomRepository;
import com.bookinghotel.repository.userRepository;
import com.bookinghotel.security.CustomAuthenticationSuccessHandler;
import com.bookinghotel.service.hotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @RequestMapping(value="/login")
    public String login(Principal principal, HttpServletRequest request) throws Exception{
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME,referer);
        return principal == null ?  "login-register" : "redirect:/";
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
