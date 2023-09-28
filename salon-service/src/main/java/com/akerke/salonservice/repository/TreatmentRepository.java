package com.akerke.salonservice.repository;

import com.akerke.salonservice.entity.Salon;
import com.akerke.salonservice.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}
