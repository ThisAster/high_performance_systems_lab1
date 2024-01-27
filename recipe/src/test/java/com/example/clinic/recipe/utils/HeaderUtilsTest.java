package com.example.clinic.recipe.utils;

import com.example.clinic.recipe.util.HeaderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeaderUtilsTest {

    @Test
    public void testCreatePaginationHeaders() {
        // Arrange
        Page<?> mockPage = mock(Page.class);
        when(mockPage.getTotalElements()).thenReturn(100L);
        when(mockPage.getNumber()).thenReturn(1);
        when(mockPage.getSize()).thenReturn(10);
        when(mockPage.getTotalPages()).thenReturn(10);
        when(mockPage.isLast()).thenReturn(false);

        // Act
        HttpHeaders headers = HeaderUtils.createPaginationHeaders(mockPage);

        // Assert
        assertEquals("100", headers.getFirst("total_count"));
        assertEquals("1", headers.getFirst("page_number"));
        assertEquals("10", headers.getFirst("page_size"));
        assertEquals("10", headers.getFirst("total_pages"));
        assertEquals("false", headers.getFirst("is_last"));
    }

}
