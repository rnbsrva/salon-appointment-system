package com.akerke.salonservice.domain.repository;

import com.akerke.salonservice.domain.entity.ImageMetadata;
import com.akerke.salonservice.domain.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, Long> {
}
