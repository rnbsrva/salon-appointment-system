package com.akerke.salonservice.domain.repository;

import com.akerke.salonservice.domain.entity.Appointment;
import com.akerke.salonservice.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByAppointment (Appointment appointment);
}
