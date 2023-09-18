package com.akerke.storageservice.dto;

import com.akerke.storageservice.constants.AttachmentSource;

public record FileOperationDTO(
        Long target,
        AttachmentSource source,
        String name // // during upload multipart.getOriginalName
) {
}
