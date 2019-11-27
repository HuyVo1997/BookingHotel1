package com.bookinghotel.repository;

import com.bookinghotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User,Integer> {
    User findUserByEmail(String email);
}
