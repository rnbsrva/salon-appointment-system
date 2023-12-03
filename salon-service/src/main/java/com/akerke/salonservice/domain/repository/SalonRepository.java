package com.akerke.salonservice.domain.repository;

import com.akerke.salonservice.domain.entity.Salon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Long> ,
        JpaSpecificationExecutor<Salon> {
}
