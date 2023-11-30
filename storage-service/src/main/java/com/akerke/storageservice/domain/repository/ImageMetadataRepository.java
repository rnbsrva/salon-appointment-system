package com.akerke.storageservice.domain.repository;

import com.akerke.storageservice.domain.entity.ImageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageMetadataRepository extends MongoRepository<ImageMetadata, String> {
}
