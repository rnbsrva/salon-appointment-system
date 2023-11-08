package com.akerke.storageservice.domain.dto;

import com.akerke.storageservice.common.constants.AttachmentSource;

public record FileOperationDTO(
        Long target,
        AttachmentSource source,
        String name
) {

}
