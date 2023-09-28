package com.akerke.salonservice.repository;

import com.akerke.salonservice.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalonRepository extends JpaRepository<Salon, Long> {
}
