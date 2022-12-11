package com.gate.lck.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JWTKey {

    @Value("${token.key}")
    private String JWTKey;

    public String get() {
        return JWTKey;
    }

    public void setKey(String key) {
        JWTKey = key;
    }
}
