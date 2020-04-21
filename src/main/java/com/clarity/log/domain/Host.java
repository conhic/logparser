package com.clarity.log.domain;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.StringJoiner;

public class Host {
    private final String hostname;

    private Host(String hostname) {
        this.hostname = hostname;
        checkInvariants();
    }

    private void checkInvariants() {
        if (StringUtils.isEmpty(hostname)) throw new IllegalArgumentException("hostname must be provided");
    }

    public String hostname() {
        return hostname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return Objects.equals(hostname, host.hostname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Host.class.getSimpleName() + "[", "]")
                .add("hostname='" + hostname + "'")
                .toString();
    }

    public static HostBuilder aHost() {
        return new HostBuilder();
    }

    public static class HostBuilder {
        private String hostname;

        private HostBuilder() {}

        public HostBuilder withHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Host build() {
            return new Host(hostname);
        }
    }
}
