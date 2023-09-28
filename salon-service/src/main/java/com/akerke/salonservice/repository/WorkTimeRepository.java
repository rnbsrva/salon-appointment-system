package com.akerke.salonservice.repository;

import com.akerke.salonservice.entity.WorkDay;
import com.akerke.salonservice.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
}
