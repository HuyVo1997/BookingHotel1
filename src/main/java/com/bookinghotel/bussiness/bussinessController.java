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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


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

    public String statusLogin(HttpSession session){
        if(session.getAttribute("statusLogin") == null){
            return "redirect:/bussiness/login";
        }
        return null;
    }

    public String checkAdmin(HttpSession session){
        Bussiness bussiness = (Bussiness)session.getAttribute("statusLogin");
        Bussiness admin = bussinessRepository.findBussinessByUsername(bussiness.getUsername());
        return (admin.getRoles().iterator().next().getName());
    }

    @RequestMapping("/bussiness/login")
    public String login(HttpSession session){
        return "login";
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
                           @RequestParam(value = "hservice") Integer[] hservice,
                           @RequestParam("file") MultipartFile file){
        String UPLOADED_FOLDER = "/Users/huyvo/Documents/Web/BookingHotel/src/main/resources/static/img/hotel/";
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
        hotel.setLocation(address);
        hotel.setStatus(0);
        hotel.setStar(1);
        hotel.setRate(1.0);
        hotel.setBussiness(bussiness);
        if(hservice.length > 0){
            Set<Service> services = new HashSet<>();
            for(int i = 0 ; i < hservice.length ; i++){
                Service service = serviceRepository.findById(hservice[i]).get();
                services.add(service);
            }
            hotel.setHotelservices(services);
        }
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            hotel.setImage(file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        hotelRepository.save(hotel);
        return "redirect:/bussiness/add-room";
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
                          @RequestParam("rservice") Integer[] rservice,
                          @RequestParam("file") MultipartFile file){
        String UPLOADED_FOLDER = "/Users/huyvo/Documents/Web/BookingHotel/src/main/resources/static/img/room/";
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
        if(rservice.length > 0){
            Set<Service> services = new HashSet<>();
            for(int i = 0 ; i < rservice.length ; i++){
                Service service = serviceRepository.findById(rservice[i]).get();
                services.add(service);
            }
            room.setRoomservices(services);
        }
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            room.setImage(file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @RequestMapping(value="/update-hotel/{id}",method = RequestMethod.GET)
    public String pageUpdateHotel(@PathVariable Integer id,Model model){
        Hotel hotel = hotelService.findHotelById(id);
        model.addAttribute("hotel",hotel);
        Set<Service> serviceSet = hotel.getHotelservices();
        List <Integer> services = new ArrayList<>();
        for (Iterator<Service> it = serviceSet.iterator(); it.hasNext(); ) {
            Service s = it.next();
            services.add(s.getServiceid());
        }
        model.addAttribute("listService",serviceRepository.findServicesByServiceidBetweenAndServiceidIsNotIn(1,10,services));
        return "update_hotel";
    }

    @RequestMapping(value="/update-hotel",method = RequestMethod.POST)
    public String updateHotel(@RequestParam("hotelid") Integer id,
                              @RequestParam("nameHotel") String name,
                              @RequestParam("price") Double price,
                              @RequestParam("email") String email,
                              @RequestParam("phone") String phone,
                              @RequestParam("address") String address,
                              @RequestParam(name = "hservice",required = false) Integer[] hservice,
                              @RequestParam(name = "removeSerive",required = false) Integer[] removeService){
        Hotel hotel = hotelService.findHotelById(id);
        hotel.setName(name);
        hotel.setPrice(price);
        hotel.setEmail(email);
        hotel.setPhone(phone);
        hotel.setLocation(address);
        if(hservice != null){
            Set<Service> services = new HashSet<>();
            for(int i = 0 ; i < hservice.length ; i++){
                Service service = serviceRepository.findById(hservice[i]).get();
                services.add(service);
            }
            for (Iterator<Service> it = hotel.getHotelservices().iterator(); it.hasNext(); ) {
                Service s = it.next();
                services.add(s);
            }
            hotel.setHotelservices(services);
        }

        if(removeService != null){
            Set<Service> services = hotel.getHotelservices();
            for(int i = 0; i < removeService.length ; i++){
                services.remove(serviceRepository.findById(removeService[i]).get());
            }
            hotel.setHotelservices(services);
        }
        hotelService.saveOrUpdate(hotel);
        return "redirect:/bussiness/view-hotel";
    }

    @RequestMapping("/bussiness/view-room")
    public String pageViewRoom(){
        return "all_rooms";
    }
}
