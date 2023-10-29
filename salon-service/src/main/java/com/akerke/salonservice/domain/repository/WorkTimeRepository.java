package com.akerke.salonservice.domain.repository;

import com.akerke.salonservice.domain.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
}
