package com.example.clinic.util;

import org.springframework.http.HttpHeaders;
import org.springframework.data.domain.Page;

public class HeaderUtils {

    public static HttpHeaders createPaginationHeaders(Page<?> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Total-Count", String.valueOf(page.getTotalElements()));
        headers.add("Page-Number", String.valueOf(page.getNumber()));
        headers.add("Page-Size", String.valueOf(page.getSize()));
        headers.add("Total-Pages", String.valueOf(page.getTotalPages()));
        headers.add("Is-Last", String.valueOf(page.isLast()));
        return headers;
    }
}
