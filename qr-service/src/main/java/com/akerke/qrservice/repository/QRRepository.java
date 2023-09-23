package com.akerke.qrservice.repository;

import com.akerke.qrservice.entity.QR;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QRRepository extends MongoRepository<QR, String> {

    Optional<QR> findByLink(String link);

}
