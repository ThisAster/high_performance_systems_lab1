package com.example.clinic.util;

import com.example.clinic.dto.PagedResponse;
import org.springframework.data.domain.Page;

public class ResponseUtil {

    public static <T> PagedResponse<T> createPagedResponse(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
