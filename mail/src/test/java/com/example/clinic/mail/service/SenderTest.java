package com.example.clinic.mail.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
class SenderTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private Sender sender;

    @BeforeEach
    public void setUp() {
        webClient = webClient.mutate()
                .responseTimeout(Duration.ofMillis(3000))
                .build();
    }

// TODO: check jwt token
//    @Test
//    @SneakyThrows
//    void sendEmailWithDefaultConfig() {
//        var testEmail = new EmailDto("example@mail.ru", "Title", "Some text");
//
//        this.webClient.post()
//                .uri("/api/mail/send")
//                .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sImlkIjoxLCJlbmFibGVkIjp0cnVlLCJ1c2VybmFtZSI6ImFkbWluIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3MzQ5MzM3OTgsImV4cCI6MTczNTM2NTc5OH0.n_eSEQsOX_4d2qmQpOrWWmwFg9N_OtQQXzbmpiHhU1EpVo0pUrbha_aeuzgdyPX8M4h0RKqEPKLSLB_9D1fE-EnSWw6ti-SZS6VRR2RS9ZvR7_j63tVSBFpt9xTlPIE7o2earqisH6C2qTYRx5wG0Pjl_LZz2emEj-ViRuDdp7APguv49ssPYE_A8LdfLqO0hMWUCUz4Vq6TuBqd2F1020JedAP9oarOGX3FLsuxLT5aWDXHCA4v1J8OAF05pcaFFmIbFZcpmgtJd8pm_uFYTULhSF4NuDFIqDCq1O2Qj-4FM3d2yNYfIlo86x2KLl2-Ot-Fg-Ha8QDLxsoLBCT4Uw")
//                .bodyValue(testEmail)
//                .exchange()
//                .expectStatus().isOk();
//
//        verify(sender, times(1)).sendEmail(testEmail);
//    }

}
