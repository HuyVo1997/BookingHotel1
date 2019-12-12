package com.bookinghotel.serviceimplement;

import com.bookinghotel.model.Bussiness;
import com.bookinghotel.model.Role;
import com.bookinghotel.repository.bussinessRepository;
import com.bookinghotel.service.bussinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class bussinessServiceImp implements bussinessService{

    @Autowired
    bussinessRepository bussinessRepository;

    @Override
    public void saveOrUpdate(Bussiness bussiness) {
        bussinessRepository.save(bussiness);
    }

}
