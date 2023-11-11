package com.akerke.storageservice.domain.request;

import com.akerke.storageservice.common.constants.AttachmentSource;

public record FileOperationRequest(
        Long target,
        AttachmentSource source,
        String name
) {

}
