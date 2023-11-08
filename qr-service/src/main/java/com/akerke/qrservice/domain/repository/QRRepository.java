package com.akerke.qrservice.domain.repository;

import com.akerke.qrservice.domain.entity.QR;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QRRepository extends MongoRepository<QR, String> {

    Optional<QR> findByLink(String link);

}
