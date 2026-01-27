package com.iws_manager.iws_manager_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionCookieConfig {

    @Bean
    public CookieSerializer cookieSerializer() {

        DefaultCookieSerializer serializer = new DefaultCookieSerializer();

        serializer.setCookieName("JSESSIONID");
        serializer.setUseHttpOnlyCookie(true);

        // REQUIRED FOR CROSS-SITE COOKIES
        serializer.setSameSite("None");

        /*
         * IMPORTANT:
         * Secure MUST be true in HTTPS environments
         * Must be false for localhost (HTTP)
         */
        serializer.setUseSecureCookie(false); // local default

        return serializer;
    }
}
