package com.example.clinic.mail.service;

import com.example.clinic.mail.dto.EmailDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SenderTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    @SneakyThrows
    void sendEmailWithDefaultConfig() {
        var testEmail = new EmailDto("example@mail.ru", "Title", "Some text");

        this.webClient.post()
                .uri("/api/mail/send")
                .bodyValue(testEmail)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}