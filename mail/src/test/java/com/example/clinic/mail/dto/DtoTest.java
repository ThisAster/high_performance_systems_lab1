package com.example.clinic.mail.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DtoTest {

    @Test
    public void testEmailDtoCreation() {
        // Arrange
        String targetEmail = "test@example.com";
        String title = "Test Title";
        String text = "This is a test email.";

        // Act
        EmailDto emailDto = new EmailDto(targetEmail, title, text);

        // Assert
        assertEquals(targetEmail, emailDto.targetEmail());
        assertEquals(title, emailDto.title());
        assertEquals(text, emailDto.text());
    }

    @Test
    public void testEmailDtoEqualsAndHashCode() {
        // Arrange
        EmailDto emailDto1 = new EmailDto("test@example.com", "Test Title", "This is a test email.");
        EmailDto emailDto2 = new EmailDto("test@example.com", "Test Title", "This is a test email.");
        EmailDto emailDto3 = new EmailDto("other@example.com", "Other Title", "This is another email.");

        // Act & Assert
        assertEquals(emailDto1, emailDto2); // Should be equal
        assertNotEquals(emailDto1, emailDto3); // Should not be equal
        assertEquals(emailDto1.hashCode(), emailDto2.hashCode()); // Should have the same hash code
        assertNotEquals(emailDto1.hashCode(), emailDto3.hashCode()); // Should have different hash codes
    }

    @Test
    public void testEmailDtoToString() {
        // Arrange
        EmailDto emailDto = new EmailDto("test@example.com", "Test Title", "This is a test email.");

        // Act
        String toStringResult = emailDto.toString();

        // Assert
        assertTrue(toStringResult.contains("targetEmail=test@example.com"));
        assertTrue(toStringResult.contains("title=Test Title"));
        assertTrue(toStringResult.contains("text=This is a test email."));
    }

}
