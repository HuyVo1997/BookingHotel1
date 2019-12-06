package com.bookinghotel.repository;

import com.bookinghotel.model.Hotel;
import com.bookinghotel.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface serviceRepository extends JpaRepository<Service,Integer> {
    List<Service> findServicesByServiceidBetween(Integer id1, Integer id2);
    List<Service> findServicesByServiceidBetweenAndServiceidIsNotIn(Integer id1,Integer id2, List<Integer> serviceList);
}
