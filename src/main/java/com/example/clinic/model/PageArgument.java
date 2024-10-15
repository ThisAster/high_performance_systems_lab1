package com.example.clinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PageArgument {
    Integer page;
    Integer size;

    public PageRequest getPageRequest() {
        return PageRequest.of(page, size);
    }
}
