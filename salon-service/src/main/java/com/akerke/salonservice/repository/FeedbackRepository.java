package com.akerke.salonservice.repository;

import com.akerke.salonservice.entity.Feedback;
import com.akerke.salonservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
