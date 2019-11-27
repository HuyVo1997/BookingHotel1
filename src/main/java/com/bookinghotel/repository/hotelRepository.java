package com.bookinghotel.repository;

import com.bookinghotel.model.Hotel;
import com.bookinghotel.model.Room;
import com.bookinghotel.model.Service;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Repository
public interface hotelRepository extends JpaRepository<Hotel,Integer>{

        @Query("SELECT DISTINCT hotel " +
                "FROM Hotel hotel " +
                "JOIN FETCH hotel.hotelservices hservice " +
                "JOIN FETCH hotel.rooms room " +
                "JOIN FETCH room.roomservices rservice " +
                "WHERE hotel.location LIKE %:location% " +
                "AND (:price is NULL OR hotel.price = (:price)) " +
                "AND (COALESCE(:star,null) IS NULL OR hotel.star IN (:star)) " +
                "AND (COALESCE(:hservice,null) IS NULL OR hservice.name IN (:hservice))")
        List<Hotel> filterHotel(@Param("location") String location,
                                @Param("price") Double price,
                                @Param("star") List<Integer> star,
                                @Param("hservice") List<String> hservice);
}
