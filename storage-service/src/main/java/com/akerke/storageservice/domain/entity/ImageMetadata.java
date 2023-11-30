package com.akerke.storageservice.domain.entity;

import com.akerke.storageservice.common.constants.AttachmentSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="image_metadata")
public class ImageMetadata {


    @Id
    private String id;

    private Boolean isMainImage;

    private String name;

    private Long target;

    private AttachmentSource attachmentSource;

    public ImageMetadata(Boolean isMainImage, String name, Long target, AttachmentSource attachmentSource) {
        this.isMainImage = isMainImage;
        this.name = name;
        this.target = target;
        this.attachmentSource = attachmentSource;
    }
}
