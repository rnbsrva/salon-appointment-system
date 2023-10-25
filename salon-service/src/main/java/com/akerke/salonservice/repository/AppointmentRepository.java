package com.akerke.salonservice.repository;

import com.akerke.salonservice.entity.Appointment;
import com.akerke.salonservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
