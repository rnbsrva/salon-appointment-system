package com.akerke.salonservice.repository;

import com.akerke.salonservice.entity.User;
import com.akerke.salonservice.entity.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
}
