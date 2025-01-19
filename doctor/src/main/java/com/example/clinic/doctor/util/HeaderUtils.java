package com.example.clinic.doctor.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public class HeaderUtils {

    public static HttpHeaders createPaginationHeaders(Page<?> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("total_count", String.valueOf(page.getTotalElements()));
        headers.add("page_number", String.valueOf(page.getNumber()));
        headers.add("page_size", String.valueOf(page.getSize()));
        headers.add("total_pages", String.valueOf(page.getTotalPages()));
        headers.add("is_last", String.valueOf(page.isLast()));
        return headers;
    }
}
