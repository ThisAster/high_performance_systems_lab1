package com.example.clinic.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SecurityHelperClass implements SecurityHelper {
    private final String publicKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArQOSvbCMumu5wbk7nHvd" +
            "NhXR40rvuF5YoVdwVgTSnpExfdO66ZUDifw4DKozEhPKlFdzsSXOWvx86wTBrFiD" +
            "EkBxG0XRIlwwEVoMWmJ1gZmyd9HI4yZ3pqc5tRy98tSaGDpwHcS3oYlWyAQ+QKo3" +
            "6ioL3TjQHb/4U5uS55SRUDkETTWtkH1gRT3L/uLiMsmrHIn2Zf73eB8fjX7CcwQK" +
            "Plel2W8gK9vZjrm0QY/quzqjnXkoyfWh9wklsSngvlUkioWjKBjdxXqIVGbwF5ok" +
            "UkQ6lBB/EzZBfjl80gc9JJS/5crOl+U6E+pw88tkh2e79PFppmIu/ZZ5LgjEcPIe" +
            "TQIDAQAB";

    @Override
    public PublicKey getJwtValidationKey() {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Invalid key");
        }
    }
}