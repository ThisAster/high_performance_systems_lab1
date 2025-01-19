package com.example.clinic.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SecurityHelperClass implements SecurityHelper {
    private final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiJv/wbqHCyUR5g4nRqTu" +
            "R9RsEWd4glxKE6Vi9on7+GzqwIO/UR/NXrp0VV5dP8kLWe4zSudBFYiFxzshThP4" +
            "mY65tVaHj8g0G1BzU3Mb4/Lj7LF3Q8iVKV/gdYPmGavQfmK1R5LZvY7ra/u4mYs3" +
            "PAew0NPlT2oDDHkpsYycG4RqLLzat+x0RzIPH+DtduhT1r/5hfgTn0y883xPxki7" +
            "sBqqLJk+wgaPetOvKkPcmr5+ccsK5o3tsFtmyM4AZ9b0f37zuixRiMQX/3QmJ9hK" +
            "FyIxXIMrPA8X7d4v+H2iwRtxVVjrR5wetecap5sNLnE58TEKhgPyqJZM8BcuJcAt" +
            "LQIDAQAB";

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