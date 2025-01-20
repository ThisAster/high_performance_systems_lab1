package com.example.clinic.mail.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableConfigurationProperties(ConfigEmail.class)
@TestPropertySource(properties = {
        "email.sender=test@example.com",
        "email.host=smtp.example.com",
        "email.port=587",
        "email.password=secret",
        "email.titles.testTitle=Test Title"
})
public class ConfigEmailTest {

    @Autowired
    private ConfigEmail configEmail;

    @Test
    public void testEmailConfiguration() {
        assertThat(configEmail.getSender()).isEqualTo("test@example.com");
        assertThat(configEmail.getHost()).isEqualTo("smtp.example.com");
        assertThat(configEmail.getPort()).isEqualTo(587);
        assertThat(configEmail.getPassword()).isEqualTo("secret");
        assertThat(configEmail.getTitles()).containsEntry("testTitle", "Test Title");
    }

}
