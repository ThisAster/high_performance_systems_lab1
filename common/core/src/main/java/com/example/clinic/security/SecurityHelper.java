package com.example.clinic.security;

import java.security.PublicKey;

public interface SecurityHelper {
    PublicKey getJwtValidationKey();
}

