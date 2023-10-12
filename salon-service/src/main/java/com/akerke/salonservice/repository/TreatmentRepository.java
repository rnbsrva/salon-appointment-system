package com.akerke.salonservice.repository;

import com.akerke.salonservice.entity.Salon;
import com.akerke.salonservice.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findTreatmentsByIdIn (List<Long> ids);

}
