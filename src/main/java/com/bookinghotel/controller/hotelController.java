package com.bookinghotel.controller;
import com.bookinghotel.model.Hotel;
import com.bookinghotel.model.Service;
import com.bookinghotel.repository.hotelRepository;
import com.bookinghotel.repository.roomRepository;
import com.bookinghotel.repository.userRepository;
import com.bookinghotel.security.CustomAuthenticationSuccessHandler;
import com.bookinghotel.service.hotelService;
import com.bookinghotel.service.roomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class hotelController {
    @Autowired
    hotelService hotelService;
    @Autowired
    hotelRepository hotelRepository;
    @Autowired
    userRepository userRepository;
    @Autowired
    roomService roomService;

    List<Integer> star = new ArrayList<>();
    List<Integer> starDistinct;
    List<String> hserviceList = new ArrayList<>();
    List<String> hserviceDistict;
    public static String locationText = "";
    public static String  dateStart = "";
    public static String dateEnd = "";

    public static int roomId;
    public static int hotelId;

    public void authentication(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("Email",email);
        model.addAttribute("User",userRepository.findUserByEmail(email));
    }

    @RequestMapping(value="/search",method = RequestMethod.GET)
    public String search(Model search,
                         @RequestParam("location") String location,
                         @RequestParam("start") String start,
                         @RequestParam("end") String end){
        authentication(search);
        dateStart = start;
        dateEnd = end;
        search.addAttribute("start",start);
        search.addAttribute("end",end);

        locationText = location;
        return "hotel-search-results";
    }

    @RequestMapping(value="/hotelResult",method= RequestMethod.POST)
    public String hotelResult(Model hotelResult,
                              @RequestParam(value = "price", required = false) Double price,
                              @RequestParam(value = "star",required = false) Integer starOption,
                              @RequestParam(value="hservice", required = false) String hservice,
                              @RequestParam(value = "remove") Integer remove,
                              @RequestParam(value = "option") Integer option,
                              @RequestParam(value="reload") Integer reload){
        authentication(hotelResult);
        if(remove == 0){
            if(option == 0){
                star.add(starOption);
                starDistinct = star.stream().distinct().collect(Collectors.toList());
                hserviceList.add(hservice);
                hserviceDistict = hserviceList.stream().distinct().collect(Collectors.toList());
            }
            if(option == 1){
                star.add(starOption);
                starDistinct = star.stream().distinct().collect(Collectors.toList());
            }
            if(option == 2){
                hserviceList.add(hservice);
                hserviceDistict = hserviceList.stream().distinct().collect(Collectors.toList());
            }
        }

        if(remove == 1){
            if(option == 3){
                starDistinct.remove(starOption);
                star = starDistinct;
            }
            if(option == 4) {
                hserviceDistict.remove(hservice);
                hserviceList = hserviceDistict;
            }
        }

        if(reload == 1) {
            if (starDistinct.size() >= 2) {
                star.clear();
                star.add(null);
                starDistinct = star.stream().distinct().collect(Collectors.toList());
            }

            if (hserviceDistict.size() >= 2) {
                hserviceList.clear();
                hserviceList.add(null);
                hserviceDistict = hserviceList.stream().distinct().collect(Collectors.toList());
            }
        }
        List<Hotel> result = hotelRepository.filterHotel(locationText,price,starDistinct,hserviceDistict);
        hotelResult.addAttribute("listHotelResult",result);
        return "star";
    }

    @RequestMapping(value="/hotelSearch",method=RequestMethod.GET)
    public String hotelSearch(Model model){
        authentication(model);
        return "hotel-search";
    }

    @RequestMapping(value="/hotelDetail/{id}",method=RequestMethod.GET)
    public String hotelDetails(Model model,@PathVariable int id){
        authentication(model);
        model.addAttribute("dateStart",dateStart);
        model.addAttribute("dateEnd",dateEnd);
        model.addAttribute("hotelById",hotelService.findHotelById(id));
        hotelId = id;
        return "hotel-details";
    }

    @RequestMapping(value="/Payment/{id}")
    public String loginPayment(Model model, @PathVariable int id){
        authentication(model);
        model.addAttribute("dateStart",dateStart);
        model.addAttribute("dateEnd",dateEnd);
        model.addAttribute("roomByID", roomService.findRoomById(id));
        model.addAttribute("numDays",CaculatorDate());
        roomId = id;
        return "hotel-payment-registered-card";
    }

    public int CaculatorDate(){
        int days;
        Date start = new Date(dateStart.toString());
        Date end = new Date(dateEnd.toString());
        long diff  = end.getTime() - start.getTime();
        long diffDays  = diff/1000/60/60/24;
        days = (int) diffDays;
        return days;
    }
}
