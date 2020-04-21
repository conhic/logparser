package com.clarity.log.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Stopwatch {
    private Instant time;

    private Stopwatch() {
        this.time = Instant.now();
    }

    public void reset() {
        this.time = Instant.now();
    }

    public long elapsedTime() {
        return Duration.between(time, Instant.now()).get(ChronoUnit.SECONDS);
    }

    public static Stopwatch createStarted() {
        return new Stopwatch();
    }
}
