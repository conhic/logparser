package com.clarity.log.infrastructure.repository;

import com.clarity.log.domain.Connection;
import com.clarity.log.domain.ConnectionRepository;

import java.io.*;

public class LogFileReader implements ConnectionRepository {
    private final ConnectionParser connectionParser = new ConnectionParser();
    private final BufferedReader reader;

    public LogFileReader(File file) {
        this.reader = createStreamFromFile(file);
    }

    private BufferedReader createStreamFromFile(File file) {
        try {
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Error create fileReader");
        }
    }

    public Connection next() {
        try {
            String line = nextLine();
            return connectionParser.parse(line);
        } catch(Exception e) {
            return null;
        }
    }

    private String nextLine() throws IOException {
        return reader.readLine();
    }
}
