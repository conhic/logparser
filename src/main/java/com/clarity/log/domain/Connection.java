package com.clarity.log.domain;

import java.util.Objects;
import java.util.StringJoiner;

public class Connection {
    private final Host origin;
    private final Host destination;
    private final Timestamp timestamp;

    private Connection(Host origin, Host destination, Timestamp timestamp) {
        this.origin = origin;
        this.destination = destination;
        this.timestamp = timestamp;
        checkInvariants();
    }

    private void checkInvariants() {
        if (timestamp == null) throw new IllegalArgumentException("timestamp must be provided");
        if (origin == null) throw new IllegalArgumentException("from host must be provided");
        if (destination == null) throw new IllegalArgumentException("to host must be provided");
    }

    public Host origin() {
        return origin;
    }

    public Host destination() {
        return destination;
    }

    public Timestamp timestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(origin, that.origin) &&
                Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, origin, destination);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Connection.class.getSimpleName() + "[", "]")
                .add("origin=" + origin)
                .add("destination=" + destination)
                .add("timestamp=" + timestamp)
                .toString();
    }

    public static ConnectionBuilder aConnection() {
        return new ConnectionBuilder();
    }

    public static class ConnectionBuilder {
        private Host origin;
        private Host destination;
        private Timestamp timestamp;

        public ConnectionBuilder fromOrigin(Host origin) {
            this.origin = origin;
            return this;
        }

        public ConnectionBuilder toDestination(Host destination) {
            this.destination = destination;
            return this;
        }

        public ConnectionBuilder withTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Connection build() {
            return new Connection(origin, destination, timestamp);
        }
    }
}
