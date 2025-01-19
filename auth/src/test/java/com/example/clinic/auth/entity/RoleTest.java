package com.example.clinic.auth.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testToString() {
        assertThat(Role.USER).isEqualTo(Role.valueOf("USER"));
        assertThat(Role.ADMIN).isEqualTo(Role.valueOf("ADMIN"));
    }
}