package com.akerke.storageservice.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttachmentSource {
    SALON_IMAGE("salon-image"),

    MASTER_IMAGE("master-image");

    private final String name;
}
