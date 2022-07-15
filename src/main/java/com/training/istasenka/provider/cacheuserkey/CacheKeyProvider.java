package com.training.istasenka.provider.cacheuserkey;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CacheKeyProvider {

    public String getUsernameKey() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
