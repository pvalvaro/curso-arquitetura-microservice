package com.ead.authuser.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class CommonsRequestLoggingFilterConfig {
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        var filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        filter.setHeaderPredicate(header -> !header.equalsIgnoreCase("authorization"));
        return filter;
    }
}
