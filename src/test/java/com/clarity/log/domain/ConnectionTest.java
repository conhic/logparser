package com.clarity.log.domain;

import org.junit.jupiter.api.Test;

import static com.clarity.log.domain.Connection.aConnection;
import static com.clarity.log.domain.Host.aHost;
import static com.clarity.log.domain.Timestamp.aTimestamp;
import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {
    private static final Host VALID_ORIGIN = aHost().withHostname("ORIGIN").build();
    private static final Host VALID_DESTINATION = aHost().withHostname("DESTINATION").build();
    private static final Timestamp VALID_TIMESTAMP = aTimestamp().withTimestamp("123456").build();

    @Test
    void nullOrigin() {
        assertThrows(IllegalArgumentException.class, () -> getValidConnectionBuilder().fromOrigin(null).build());
    }

    @Test
    void nullDestination() {
        assertThrows(IllegalArgumentException.class, () -> getValidConnectionBuilder().toDestination(null).build());
    }

    @Test
    void nullTimestamp() {
        assertThrows(IllegalArgumentException.class, () -> getValidConnectionBuilder().withTimestamp(null).build());
    }

    @Test
    void validConnection() {
        assertDoesNotThrow(() -> getValidConnectionBuilder().build());
    }

    @Test
    void origin() {
        Connection actual = getValidConnectionBuilder().fromOrigin(VALID_ORIGIN).build();
        assertEquals(VALID_ORIGIN, actual.origin());
    }

    @Test
    void destination() {
        Connection actual = getValidConnectionBuilder().toDestination(VALID_DESTINATION).build();
        assertEquals(VALID_DESTINATION, actual.destination());
    }

    @Test
    void timestamp() {
        Connection actual = getValidConnectionBuilder().withTimestamp(VALID_TIMESTAMP).build();
        assertEquals(VALID_TIMESTAMP, actual.timestamp());
    }

    Connection.ConnectionBuilder getValidConnectionBuilder() {
        return aConnection()
                .fromOrigin(VALID_ORIGIN)
                .toDestination(VALID_DESTINATION)
                .withTimestamp(VALID_TIMESTAMP);
    }

}