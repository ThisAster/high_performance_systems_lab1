package com.example.clinic.appointment.config.resolver;

import com.example.clinic.appointment.model.PageArgument;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
public class PageArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";
    private static final Integer SIZE_MAX = 100;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PageArgument.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        int page = Optional.ofNullable(webRequest.getParameter(PAGE_PARAM))
                .flatMap(this::parseNum)
                .filter(this::validPageOrThrow)
                .orElse(0);

        int size = Optional.ofNullable(webRequest.getHeader("X-Per-Page"))
                .or(() -> Optional.ofNullable(webRequest.getParameter(SIZE_PARAM)))
                .flatMap(this::parseNum)
                .filter(this::validSizeOrThrow)
                .orElse(SIZE_MAX);

        return new PageArgument(page, size);
    }

    @SneakyThrows
    private boolean validPageOrThrow(Integer page) {
        if (page < 0) {
            throw new BadRequestException("Page Requested is less than 0");
        }
        return true;
    }

    @SneakyThrows
    private boolean validSizeOrThrow(Integer size) {
        if (size < 1) {
            throw new BadRequestException("Size Requested is less than 1");
        }
        if (size > SIZE_MAX) {
            throw new BadRequestException("Size Requested is greater than 100");
        }
        return true;
    }

    private Optional<Integer> parseNum(String num) {
        if (StringUtils.isNumeric(num)) {
            return Optional.of(Integer.parseInt(num));
        }
        return Optional.empty();
    }
}
