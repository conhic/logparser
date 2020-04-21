package com.clarity.log.infrastructure.writer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardOutputOutputFormatterTest {
    private static final String SEPARATOR = "***************";

    private ByteArrayOutputStream outputSpy;
    private StandardOutputOutputFormatter standardOutputWriter;

    @BeforeEach
    void setUp() {
        this.outputSpy = new ByteArrayOutputStream();
        this.standardOutputWriter = new StandardOutputOutputFormatter(new PrintStream(outputSpy));
    }

    @Test
    void writeTitle() {
        String expected =
                SEPARATOR + "\n" +
                "TEST"    + "\n" +
                SEPARATOR + "\n";

        standardOutputWriter.writeTitle("TEST");
        assertEquals(expected, outputSpy.toString());
    }

    @Test
    void writeCollection() {
        String expected =
                "TEST" + "\n" +
                "TEST" + "\n" +
                "TEST" + "\n" +
                "\n";

        standardOutputWriter.write(Arrays.asList("TEST", "TEST", "TEST"));
        assertEquals(expected, outputSpy.toString());
    }

    @Test
    void write() {
        String expected =
                "TEST" + "\n" +
                "\n";

        standardOutputWriter.write("TEST");
        assertEquals(expected, outputSpy.toString());
    }
}