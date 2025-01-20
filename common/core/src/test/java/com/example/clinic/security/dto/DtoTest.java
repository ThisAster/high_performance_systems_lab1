package com.example.clinic.security.dto;

import com.example.clinic.dto.UserDetailsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class DtoTest {

    @Test
    public void test() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        UserDetailsDto userDetailsDto = new UserDetailsDto(
                1L,
                authorities,
                true,
                true,
                true,
                true
        );

        Assertions.assertNotNull(userDetailsDto.getAuthorities());
        Assertions.assertNotNull(userDetailsDto.getId());

        Assertions.assertTrue(userDetailsDto.isAccountNonExpired());
        Assertions.assertTrue(userDetailsDto.isAccountNonLocked());
        Assertions.assertTrue(userDetailsDto.isCredentialsNonExpired());
        Assertions.assertTrue(userDetailsDto.isEnabled());
    }

}
