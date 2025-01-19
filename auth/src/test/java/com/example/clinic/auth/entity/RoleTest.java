package com.example.clinic.auth.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {

    @Test
    void testToString() {
        assertThat(Role.USER).isEqualTo(Role.valueOf("USER"));
        assertThat(Role.ADMIN).isEqualTo(Role.valueOf("ADMIN"));
    }
}