package com.clarity.log.domain;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.StringJoiner;

public class Timestamp {
    private final String timestamp;
    private final Instant instant;

    private Timestamp(String timestamp) {
        this.timestamp = timestamp;
        this.instant = Instant.ofEpochMilli(Long.parseLong(timestamp));
        checkInvariants();
    }

    private Timestamp(Instant instant) {
        this.timestamp = Long.toString(instant.toEpochMilli());
        this.instant = instant;
    }

    private void checkInvariants() {
        if (timestamp == null) throw new IllegalArgumentException("timestamp must be provided");
    }

    public String timestamp() {
        return timestamp;
    }


    public boolean before(Timestamp timestamp) {
        if (timestamp == null) throw new IllegalArgumentException("timestamp cannot be null");
        return this.instant.isBefore(timestamp.instant);
    }

    public boolean beforeOrEquals(Timestamp timestamp) {
        return before(timestamp) || equals(timestamp);
    }

    public boolean after(Timestamp timestamp) {
        if (timestamp == null) throw new IllegalArgumentException("timestamp cannot be null");
        return this.instant.isAfter(timestamp.instant);
    }

    public boolean afterOrEquals(Timestamp timestamp) {
        return after(timestamp) || equals(timestamp);
    }

    public Timestamp add(long time, TemporalUnit temporalUnit) {
        return new Timestamp(instant.plus(time, temporalUnit));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timestamp timestamp1 = (Timestamp) o;
        return Objects.equals(timestamp, timestamp1.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Timestamp.class.getSimpleName() + "[", "]")
                .add("timestamp='" + timestamp + "'")
                .toString();
    }

    public static TimestampBuilder aTimestamp() {
        return new TimestampBuilder();
    }

    public static class TimestampBuilder {
        private String timestamp;

        private TimestampBuilder() {}

        public TimestampBuilder withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Timestamp build() {
            return new Timestamp(timestamp);
        }
    }
}
