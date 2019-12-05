package com.bookinghotel.bussiness;

import com.bookinghotel.model.*;
import com.bookinghotel.repository.bussinessRepository;
import com.bookinghotel.repository.hotelRepository;
import com.bookinghotel.repository.serviceRepository;
import com.bookinghotel.service.bussinessService;
import com.bookinghotel.service.hotelService;
import com.bookinghotel.service.roomService;
import com.bookinghotel.service.typeroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class bussinessController {

    @Autowired
    bussinessService bussinessService;

    @Autowired
    bussinessRepository bussinessRepository;

    @Autowired
    hotelRepository hotelRepository;

    @Autowired
    typeroomService typeroomService;

    @Autowired
    hotelService hotelService;

    @Autowired
    roomService roomService;

    @Autowired
    serviceRepository serviceRepository;

    public String statusLogin(HttpSession session,String page){
        if(session.getAttribute("statusLogin") != null){
            return page;
        }
        else {
            return "login";
        }
    }

    public String checkAdmin(HttpSession session){
        Bussiness bussiness = (Bussiness)session.getAttribute("statusLogin");
        Bussiness admin = bussinessRepository.findBussinessByUsername(bussiness.getUsername());
        return (admin.getRoles().iterator().next().getName());
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
    public String bussinesspage(HttpSession session,Model model){
        if(session.getAttribute("statusLogin") == null){
            return "login";
        }
        else {
            model.addAttribute("isAdmin",checkAdmin(session));
            return "index-bussiness";
        }
    }

    @RequestMapping("/bussiness/add-hotel")
    public String pageAddHotel(HttpSession session, Model model){
        model.addAttribute("isAdmin",checkAdmin(session));
        model.addAttribute("listService",serviceRepository.findServicesByServiceidBetween(1,10));
        return "new_booking";
    }

    @RequestMapping("/bussiness/view-hotel")
    public String viewHotel(HttpSession session, Model model){
        model.addAttribute("isAdmin",checkAdmin(session));
        Bussiness bussiness = (Bussiness)session.getAttribute("statusLogin");
        model.addAttribute("listHotel",hotelRepository.findHotelsByBussiness(bussiness));
        return "view_booking";
    }

    @RequestMapping(value="/add-hotel",method = RequestMethod.POST)
    public String addHotel(HttpSession session,
                           @RequestParam("nameHotel") String name,
                           @RequestParam("price") Double price,
                           @RequestParam("email") String email,
                           @RequestParam("phone") String phone,
                           @RequestParam("city") String city,
                           @RequestParam("province") String province,
                           @RequestParam("address") String address,
                           @RequestParam(value = "hservice") Integer[] hservice){
        Hotel hotel = new Hotel();
        Bussiness bussiness = (Bussiness)session.getAttribute("statusLogin");
        hotel.setName(name);
        hotel.setPrice(price);
        hotel.setEmail(email);
        hotel.setPhone(phone);
        if(!city.equals("")){
            address = address + ", " + city;
        }
        if(!province.equals("")){
            address = address + ", " + province;
        }
        hotel.setLocation(address + ", " + city + ", " + province);
        hotel.setStatus(0);
        hotel.setStatus(1);
        hotel.setRate(1.0);
        hotel.setBussiness(bussiness);
        Set<Service> services = new HashSet<>();
        for(int i = 0 ; i < hservice.length ; i++){
            Service service = serviceRepository.findById(hservice[i]).get();
            services.add(service);
        }
        hotel.setHotelservices(services);
        hotelRepository.save(hotel);
        return "redirect:/bussiness/view-hotel";
    }


    @RequestMapping("/bussiness/add-room")
    public String pageAddRoom(HttpSession session,Model model){
        Bussiness bussiness = (Bussiness)session.getAttribute("statusLogin");
        model.addAttribute("listHotel",hotelRepository.findHotelsByBussiness(bussiness));
        model.addAttribute("isAdmin",checkAdmin(session));
        model.addAttribute("listService",serviceRepository.findServicesByServiceidBetween(11,19));
        model.addAttribute("listTypeRoom",typeroomService.getAllTypeRooms());
        return "add_room";
}

    @RequestMapping(value="/add-room",method = RequestMethod.POST)
    public String addRoom(HttpSession session,
                          @RequestParam("hotelid") Integer hotelid,
                          @RequestParam("description") String description,
                          @RequestParam("typeroom") Integer typeroom,
                          @RequestParam("refund") Integer cancle,
                          @RequestParam("numadult") Integer numadult,
                          @RequestParam("numchild") Integer numchild,
                          @RequestParam("numbed") Integer numbed,
                          @RequestParam("numroom") Integer numroom,
                          @RequestParam("price") Double price,
                          @RequestParam("roomfootage") Integer roomfootage,
                          @RequestParam("rservice") Integer[] rservice){

        Hotel hotel = hotelService.findHotelById(hotelid);
        TypeRoom typeRoom = typeroomService.findTypeRoomById(typeroom);
        Room room = new Room();
        room.setDescription(description);
        room.setRefund(cancle);
        room.setNumofadults(numadult);
        room.setNumofchild(numchild);
        room.setNumofbed(numbed);
        room.setNumroom(numroom);
        room.setPrice(price);
        room.setRoomfootage(roomfootage);
        room.setHotel(hotel);
        room.setTyperoom(typeRoom);
        Set<Service> services = new HashSet<>();
        for(int i = 0 ; i < rservice.length ; i++){
            Service service = serviceRepository.findById(rservice[i]).get();
            services.add(service);
        }
        room.setRoomservices(services);
        roomService.updateRoom(room);
        return "redirect:/bussiness/add-room";
    }

    @RequestMapping("/bussiness/pending")
    public String pendingHotel(HttpSession session,Model model){
        model.addAttribute("isAdmin",checkAdmin(session));
        model.addAttribute("listHotelPending",hotelService.findAllhotel());
        return "view_pending";
    }

    @RequestMapping(value="/activePending/{id}",method = RequestMethod.GET)
    public String activePending(@PathVariable Integer id){
        Hotel hotel = hotelService.findHotelById(id);
        hotel.setStatus(1);
        hotelService.saveOrUpdate(hotel);
        return "redirect:/bussiness/pending";
    }

}
