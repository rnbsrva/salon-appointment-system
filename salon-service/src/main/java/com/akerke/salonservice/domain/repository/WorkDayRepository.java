package com.akerke.salonservice.domain.repository;

import com.akerke.salonservice.domain.entity.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
}
