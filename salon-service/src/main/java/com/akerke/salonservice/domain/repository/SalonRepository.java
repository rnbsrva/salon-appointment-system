package com.akerke.salonservice.domain.repository;

import com.akerke.salonservice.domain.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> {
}
