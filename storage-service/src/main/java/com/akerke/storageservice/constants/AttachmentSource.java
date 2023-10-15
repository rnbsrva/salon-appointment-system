package com.akerke.storageservice.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttachmentSource {
    SALON_IMAGE("salon-image"),

    MASTER_IMAGE("master-image"),
    QR_IMAGE("qr-image");

    private final String name;
}
