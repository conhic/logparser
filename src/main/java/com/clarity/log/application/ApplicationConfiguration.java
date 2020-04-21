package com.clarity.log.application;

import com.clarity.log.domain.ConnectionRepository;
import com.clarity.log.domain.Host;
import com.clarity.log.domain.Timestamp;
import com.clarity.log.infrastructure.repository.LogFileReader;
import com.clarity.log.infrastructure.writer.StandardOutputOutputFormatter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

import static com.clarity.log.domain.Host.aHost;
import static com.clarity.log.domain.Timestamp.aTimestamp;

@Configuration
public class ApplicationConfiguration {
    private static final String FILE_ARG = "file";
    private static final String HOST_ARG = "hostname";
    private static final String START_ARG = "start";
    private static final String END_ARG = "end";

    private final ApplicationArguments applicationArguments;

    public ApplicationConfiguration(ApplicationArguments applicationArguments) {
        this.applicationArguments = applicationArguments;
    }

    @Bean
    public JobFactory jobFactory() {
        return new JobFactory(connectionRepository(), writer(), host(), start(), end());
    }

    private ConnectionRepository connectionRepository() {
        File file = file();
        if (file == null) return null;

        return new LogFileReader(file);
    }

    private File file() {
        if (!applicationArguments.containsOption(FILE_ARG)) return null;
        String filepath = applicationArguments.getOptionValues(FILE_ARG).get(0);
        return new File(filepath);
    }

    private OutputFormatter writer() {
        return new StandardOutputOutputFormatter(System.out);
    }

    private Host host() {
        if (!applicationArguments.containsOption(HOST_ARG)) return null;
        String hostname = applicationArguments.getOptionValues(HOST_ARG).get(0);
        return aHost().withHostname(hostname).build();
    }

    private Timestamp start() {
        if (!applicationArguments.containsOption(START_ARG)) return null;
        String startTimestamp = applicationArguments.getOptionValues(START_ARG).get(0);
        return aTimestamp().withTimestamp(startTimestamp).build();
    }

    private Timestamp end() {
        if (!applicationArguments.containsOption(END_ARG)) return null;
        String startTimestamp = applicationArguments.getOptionValues(END_ARG).get(0);
        return aTimestamp().withTimestamp(startTimestamp).build();
    }
}
