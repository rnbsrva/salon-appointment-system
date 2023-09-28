package com.akerke.salonservice.repository;

import com.akerke.salonservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
