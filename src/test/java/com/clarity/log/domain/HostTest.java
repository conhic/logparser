package com.clarity.log.domain;

import org.junit.jupiter.api.Test;

import static com.clarity.log.domain.Host.aHost;
import static org.junit.jupiter.api.Assertions.*;

class HostTest {
    private static String VALID_HOSTNAME = "HOSTNAME";

    @Test
    void nullHostname() {
        assertThrows(IllegalArgumentException.class, () -> aHost().withHostname(null).build());
    }

    @Test
    void emptyHostname() {
        assertThrows(IllegalArgumentException.class, () -> aHost().withHostname("").build());
    }

    @Test
    void validHostname() {
        assertDoesNotThrow(() -> aHost().withHostname(VALID_HOSTNAME).build());
    }

    @Test
    void hostname() {
        Host actual = aHost().withHostname(VALID_HOSTNAME).build();
        assertEquals(VALID_HOSTNAME, actual.hostname());
    }
}