package com.akerke.authservice.security;

import com.akerke.authservice.constants.EmailLinkMode;

public interface EmailLinkHelper {

    String link(EmailLinkMode mode, String email);

}
