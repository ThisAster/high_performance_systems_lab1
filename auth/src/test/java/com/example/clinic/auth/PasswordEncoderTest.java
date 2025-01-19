package com.example.clinic.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordEncoderTest {
    @Test
    void bCriptEncoder() {
        var cripto = new BCryptPasswordEncoder(12);
        var random = new Random();
        String testpass = Stream
                .generate(() -> "abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(0, 26)))
                .limit(60)
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
        System.out.println(testpass);
        var encoded = cripto.encode("jkhprtrnwxwtyftvxbjplbljcsjlycgayjekqpuhclykhgrntropngzwkwzr");
        System.out.println(encoded);
        assertEquals(60, encoded.length());
    }
}
