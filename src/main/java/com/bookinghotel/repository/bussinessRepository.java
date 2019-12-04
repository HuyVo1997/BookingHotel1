package com.bookinghotel.repository;

import com.bookinghotel.model.Bussiness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface bussinessRepository extends JpaRepository<Bussiness, Integer> {
    Bussiness findBussinessByUsername(String username);
}
