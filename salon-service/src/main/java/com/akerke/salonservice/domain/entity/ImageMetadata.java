package com.akerke.salonservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ImageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageId;

    private Boolean isMainImage;

    public ImageMetadata(String imageId, Boolean isMainImage) {
        this.imageId = imageId;
        this.isMainImage = isMainImage;
    }
}
