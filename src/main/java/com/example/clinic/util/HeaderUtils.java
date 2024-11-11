package com.example.clinic.util;

import org.springframework.http.HttpHeaders;
import org.springframework.data.domain.Page;

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
