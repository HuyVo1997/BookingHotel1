package com.bookinghotel.repository;

import com.bookinghotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface userRepository extends JpaRepository<User,Integer> {
    User findUserByEmail(String email);
    List<User> findUsersByEmail(String email);
}
