package com.user.management.app.util;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenUtil {
    public static String generateSafeToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = Base64.getEncoder().encodeToString(bytes);
        return token;
    }
}
