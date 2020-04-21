package com.clarity.log.infrastructure.repository;

import com.clarity.log.domain.Connection;
import com.clarity.log.domain.Timestamp;
import org.junit.jupiter.api.Test;

import static com.clarity.log.domain.Connection.aConnection;
import static com.clarity.log.domain.Host.aHost;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectionParserTest {

    @Test
    void parseLine() {
        String line = "1565647204351 Aadvik Matina";
        ConnectionParser connectionParser = new ConnectionParser();
        Connection actual = connectionParser.parse(line);
        Connection expected = aConnection()
                .fromOrigin(aHost().withHostname("Aadvik").build())
                .toDestination(aHost().withHostname("Matina").build())
                .withTimestamp(Timestamp.aTimestamp().withTimestamp("1565647204351").build())
                .build();

        assertEquals(expected, actual);
    }
}