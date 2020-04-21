package com.clarity.log.application;

import com.clarity.log.domain.ConnectionRepository;
import com.clarity.log.domain.Host;
import com.clarity.log.domain.Timestamp;

public class JobFactory {
    private final ConnectionRepository connectionRepository;
    private final OutputFormatter outputFormatter;
    private final Host host;
    private final Timestamp start;
    private final Timestamp end;

    public JobFactory(ConnectionRepository connectionRepository, OutputFormatter outputFormatter, Host host, Timestamp start, Timestamp end) {
        this.connectionRepository = connectionRepository;
        this.outputFormatter = outputFormatter;
        this.host = host;
        this.start = start;
        this.end = end;
    }

    public ParseTimeIntervalJob parseTimeInterval() {
        return new ParseTimeIntervalJob(connectionRepository, outputFormatter, host, start, end);
    }

    public ParseUnlimitedInputJob parseUnlimitedInput() {
        return new ParseUnlimitedInputJob(connectionRepository, outputFormatter, host);
    }
}
