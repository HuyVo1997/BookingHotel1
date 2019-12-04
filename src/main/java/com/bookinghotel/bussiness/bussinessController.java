package com.bookinghotel.bussiness;

import com.bookinghotel.model.Bussiness;
import com.bookinghotel.repository.bussinessRepository;
import com.bookinghotel.repository.hotelRepository;
import com.bookinghotel.service.bussinessService;
import com.bookinghotel.service.hotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
public class bussinessController {

    @Autowired
    bussinessService bussinessService;

    @Autowired
    bussinessRepository bussinessRepository;

    @Autowired
    hotelRepository hotelRepository;

    public String statusLogin(HttpSession session,String page){
        if(session.getAttribute("statusLogin") != null){
            return page;
        }
        else {
            return "login";
        }
    }

    @RequestMapping("/bussiness/login")
    public String login(HttpSession session){
        return statusLogin(session,"/bussiness/login");
    }

    @RequestMapping("/bussiness/register")
    public String register(){
        return "sign_up";
    }

    @RequestMapping(value="/register-success",method = RequestMethod.POST)
    public String registerSucess(@RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("pass") String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Bussiness bussiness = new Bussiness();
        bussiness.setEmail(email);
        bussiness.setUsername(username);
        bussiness.setPassword(bCryptPasswordEncoder.encode(password));
        bussinessService.saveOrUpdate(bussiness);
        return "login";
    }

    @RequestMapping("/login-success")
    public String loginSuccess(@RequestParam("username") String username,
                               @RequestParam("pass") String password,
                               HttpSession session){
        Bussiness bussiness = bussinessRepository.findBussinessByUsername(username);
        if(BCrypt.checkpw(password,bussiness.getPassword())){
            session.setAttribute("statusLogin",bussiness);
            return "redirect:/bussiness/home-page";
        }
        else {
            return "/bussiness/login";
        }
    }

    @RequestMapping("/bussiness/home-page")
    public String bussinesspage(HttpSession session){
        if(session.getAttribute("statusLogin") == null){
            return "login";
        }
        else {
            return "index-bussiness";
        }
    }

    @RequestMapping("/bussiness/add-hotel")
    public String addHotel(HttpSession session){
        return statusLogin(session,"new_booking");
    }

    @RequestMapping("/bussiness/view-hotel")
    public String viewHotel(HttpSession session, Model model){
        Bussiness bussiness = (Bussiness)session.getAttribute("statusLogin");
        model.addAttribute("listHotel",hotelRepository.findHotelsByBussiness(bussiness));
        return "view_booking";
    }


}
