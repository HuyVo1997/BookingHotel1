package com.bookinghotel.repository;

import com.bookinghotel.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface serviceRepository extends JpaRepository<Service,Integer> {
    List<Service> findServicesByServiceidBetween(Integer id1, Integer id2);
}
