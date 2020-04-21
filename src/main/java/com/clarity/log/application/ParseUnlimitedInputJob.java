package com.clarity.log.application;

import com.clarity.log.domain.Connection;
import com.clarity.log.domain.ConnectionRepository;
import com.clarity.log.domain.Host;
import com.clarity.log.domain.Stopwatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ParseUnlimitedInputJob {
    private final ConnectionRepository connectionRepository;
    private final OutputFormatter outputFormatter;
    private final Host host;

    ParseUnlimitedInputJob(ConnectionRepository connectionRepository, OutputFormatter outputFormatter, Host host) {
        this.connectionRepository = connectionRepository;
        this.outputFormatter = outputFormatter;
        this.host = host;
        checkInvariants();
    }

    private void checkInvariants() {
        if (connectionRepository == null) throw new IllegalArgumentException("Connection repository cannot be null");
        if (outputFormatter == null) throw new IllegalArgumentException("Writer cannot be null");
        if (host == null) throw new IllegalArgumentException("Host cannot be null");
    }

    public void run(boolean follow) {
        Set<Host> connected = new HashSet<>();
        Set<Host> received = new HashSet<>();
        Map<Host, Integer> count = new HashMap<>();

        // Process and print log
        processLog(connected, received, count);
        print(connected, received, max(count));

        // Process unlimited log
        Stopwatch stopwatch = Stopwatch.createStarted();
        reset(connected, received, count, stopwatch);
        boolean running = follow;

        while(running) {
            if (anHourHasPassed(stopwatch)) {
                print(connected, received, max(count));
                reset(connected, received, count, stopwatch);
            }

            try {
                processLog(connected, received, count);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    private void processLog(Set<Host> connected, Set<Host> received, Map<Host, Integer> count) {
        Connection connection;
        while((connection = connectionRepository.next()) != null) {
            if (connectsToHost(connection)) connected.add(connection.origin());
            if (receivesConnectionFromHost(connection)) received.add(connection.destination());
            increaseCount(count, connection.origin());
            increaseCount(count, connection.destination());
        }
    }

    private Host max(Map<Host, Integer> count) {
        return count.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private void print(Set<Host> connected, Set<Host> received, Host max) {
        if (connected.isEmpty() && received.isEmpty() && max == null) return;

        outputFormatter.writeTitle("CONNECTED");
        outputFormatter.write(connected.stream().map(Host::hostname).collect(Collectors.toSet()));

        outputFormatter.writeTitle("RECEIVED");
        outputFormatter.write(received.stream().map(Host::hostname).collect(Collectors.toSet()));

        outputFormatter.writeTitle("MAX CONNECTIONS");
        outputFormatter.write(max.hostname());
    }

    private boolean anHourHasPassed(Stopwatch stopwatch) {
        return stopwatch.elapsedTime() >= 3600;
    }

    private void reset(Set<Host> connected, Set<Host> received, Map<Host, Integer> count, Stopwatch stopwatch) {
        connected.clear();
        received.clear();
        count.clear();
        stopwatch.reset();
    }

    private boolean connectsToHost(Connection connection) {
        return connection.destination().equals(host);
    }

    private boolean receivesConnectionFromHost(Connection connection) {
        return connection.origin().equals(host);
    }

    private void increaseCount(Map<Host, Integer> count, Host key) {
        int currentCount = count.getOrDefault(key, 0);
        count.put(key, currentCount + 1);
    }
}
