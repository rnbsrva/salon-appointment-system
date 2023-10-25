package com.akerke.salonservice.repository;

import com.akerke.salonservice.entity.Salon;
import com.akerke.salonservice.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}
