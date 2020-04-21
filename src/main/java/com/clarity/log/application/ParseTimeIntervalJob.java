package com.clarity.log.application;

import com.clarity.log.domain.Connection;
import com.clarity.log.domain.ConnectionRepository;
import com.clarity.log.domain.Host;
import com.clarity.log.domain.Timestamp;

import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ParseTimeIntervalJob {
    private final ConnectionRepository connectionRepository;
    private final OutputFormatter outputFormatter;
    private final Host host;
    private final Timestamp start;
    private final Timestamp end;
    private final Timestamp cutoff;

    ParseTimeIntervalJob(ConnectionRepository connectionRepository, OutputFormatter outputFormatter, Host host, Timestamp start, Timestamp end) {
        this.connectionRepository = connectionRepository;
        this.outputFormatter = outputFormatter;
        this.host = host;
        this.start = start;
        this.end = end;
        this.cutoff = calculateCutoff(end);
        checkInvariants();
    }

    private void checkInvariants() {
        if (connectionRepository == null) throw new IllegalArgumentException("Connection repository cannot be null");
        if (outputFormatter == null) throw new IllegalArgumentException("Writer cannot be null");
        if (host == null) throw new IllegalArgumentException("Host cannot be null");
        if (start == null) throw new IllegalArgumentException("Start cannot be null");
        if (end == null) throw new IllegalArgumentException("End cannot be null");
        if (cutoff == null) throw new IllegalArgumentException("Cutoff cannot be null");
    }

    private Timestamp calculateCutoff(Timestamp end) {
        if (end == null) return null;
        return end.add(5, ChronoUnit.MINUTES);
    }

    public void run() {
        Set<Host> connections = new HashSet<>();

        Connection connection;
        while((connection = connectionRepository.next()) != null) {
            // if the current connection timestamp is greater than the cutoff point then no connections beyond this
            // point can belong to the specified interval
            if (connection.timestamp().after(cutoff)) break;

            if (isInTimePeriod(connection)) {
                if (connection.origin().equals(host)) connections.add(connection.destination());
                if (connection.destination().equals(host)) connections.add(connection.origin());
            }
        }

        print(connections);
    }

    private boolean isInTimePeriod(Connection connection) {
        if (connection.timestamp().before(start)) return false;
        if (connection.timestamp().after(end)) return false;
        return true;
    }

    private void print(Set<Host> connections) {
        outputFormatter.writeTitle("CONNECTIONS");
        outputFormatter.write(connections.stream().map(Host::hostname).collect(Collectors.toList()));
    }
}
