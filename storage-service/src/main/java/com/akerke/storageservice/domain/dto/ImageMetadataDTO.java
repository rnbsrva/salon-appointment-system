package com.akerke.storageservice.domain.dto;

import com.akerke.storageservice.common.constants.AttachmentSource;

public record ImageMetadataDTO (
        Boolean isMainImage,
        String name,
        Long target,
        AttachmentSource attachmentSource
){
}
