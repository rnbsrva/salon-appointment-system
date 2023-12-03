package com.akerke.storageservice.domain.repository;

import com.akerke.storageservice.common.constants.AttachmentSource;
import com.akerke.storageservice.domain.entity.ImageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ImageMetadataRepository extends MongoRepository<ImageMetadata, String> {

    Optional<ImageMetadata> findByAttachmentSourceAndTargetAndIsMainImage (AttachmentSource source, Long target, Boolean isMainImage);

}
