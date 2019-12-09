package com.bookinghotel.repository;

import com.bookinghotel.model.Bussiness;
import com.bookinghotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface roleRepository extends JpaRepository<Role,Integer> {

}
