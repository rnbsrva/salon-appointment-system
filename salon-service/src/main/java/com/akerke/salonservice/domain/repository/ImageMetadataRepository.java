package com.akerke.salonservice.domain.repository;

import com.akerke.salonservice.domain.entity.ImageMetadata;
import com.akerke.salonservice.domain.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, Long> {

    Optional<ImageMetadata> findImageMetadataByImageId (String imageId);

}
