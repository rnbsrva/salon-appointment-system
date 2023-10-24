package com.akerke.authservice.security;

import com.akerke.authservice.common.constants.EmailLinkMode;

public interface EmailLinkHelper {

    String link(EmailLinkMode mode, String email);

}
