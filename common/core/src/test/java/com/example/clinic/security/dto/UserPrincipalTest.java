package com.example.clinic.security.dto;

import com.example.clinic.dto.UserDetailsDto;
import com.example.clinic.dto.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserPrincipalTest {

    private UserDetailsDto userDetailsDto;
    private UserPrincipal userPrincipal;

    @BeforeEach
    public void setUp() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        userDetailsDto = new UserDetailsDto(
                1L,
                authorities,
                true,
                true,
                true,
                true
        );

        userPrincipal = new UserPrincipal(userDetailsDto);
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
        assertNotNull(authorities);
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    public void testGetPassword() {
        assertNull(userPrincipal.getPassword());
    }

    @Test
    public void testGetUsername() {
        assertEquals("1", userPrincipal.getUsername());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(userPrincipal.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(userPrincipal.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(userPrincipal.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(userPrincipal.isEnabled());
    }

    @Test
    public void testToken() {
        assertNull(userPrincipal.getToken()); // Token should be null initially

        String testToken = "testToken";
        userPrincipal.setToken(testToken);
        assertEquals(testToken, userPrincipal.getToken()); // Verify the token is set correctly
    }

}
