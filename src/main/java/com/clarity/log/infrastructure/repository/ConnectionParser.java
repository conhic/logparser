package com.clarity.log.infrastructure.repository;

import com.clarity.log.domain.Connection;
import com.clarity.log.domain.Host;
import com.clarity.log.domain.Timestamp;

import static com.clarity.log.domain.Connection.aConnection;
import static com.clarity.log.domain.Host.aHost;
import static com.clarity.log.domain.Timestamp.aTimestamp;

class ConnectionParser {

    Connection parse(String line) {
        try {
            String[] words = line.split(" ");
            Timestamp timestamp = extractTimestamp(words);
            Host origin = extractOrigin(words);
            Host destination = extractDestination(words);
            return aConnection().fromOrigin(origin).toDestination(destination).withTimestamp(timestamp).build();
        } catch(Exception e) {
            return null;
        }
    }

    private Timestamp extractTimestamp(String[] words) {
        String timestamp = words[0];
        return aTimestamp().withTimestamp(timestamp).build();
    }

    private Host extractOrigin(String[] words) {
        String origin = words[1];
        return aHost().withHostname(origin).build();
    }

    private Host extractDestination(String[] words) {
        String destination = words[2];
        return aHost().withHostname(destination).build();
    }
}
