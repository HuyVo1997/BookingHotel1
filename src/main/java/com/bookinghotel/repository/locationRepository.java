package com.bookinghotel.repository;

import com.bookinghotel.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface locationRepository extends JpaRepository<Location,String> {
    List<Location> findLocationsByNameContaining(String name);
}
