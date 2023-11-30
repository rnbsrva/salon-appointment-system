package com.akerke.salonservice.domain.repository;

import com.akerke.salonservice.domain.entity.Master;
import com.akerke.salonservice.domain.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
}
