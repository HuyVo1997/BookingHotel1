package com.bookinghotel.repository;

import com.bookinghotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepository extends JpaRepository<Role,Integer> {
    Role findRoleByName(String name);
}
