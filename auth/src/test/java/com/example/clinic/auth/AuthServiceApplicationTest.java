package com.example.clinic.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AuthServiceApplicationTest {

    @Test
    public void testMain() {
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
            String[] args = new String[0];

            AuthServiceApplication.main(args);

            mocked.verify(() -> SpringApplication.run(AuthServiceApplication.class, args), times(1));
        }
    }
}
