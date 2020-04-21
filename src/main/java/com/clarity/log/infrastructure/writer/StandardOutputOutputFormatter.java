package com.clarity.log.infrastructure.writer;

import com.clarity.log.application.OutputFormatter;

import java.io.PrintStream;
import java.util.Collection;

public class StandardOutputOutputFormatter implements OutputFormatter {
    public static final String SEPARATOR = "***************";
    private final PrintStream printStream;

    public StandardOutputOutputFormatter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void writeTitle(String title) {
        printStream.println(SEPARATOR);
        printStream.println(title);
        printStream.println(SEPARATOR);
    }

    public void write(Collection<String> collection) {
        for (String s : collection) {
            printStream.println(s);
        }

        printStream.println();
    }

    public void write(String string) {
        printStream.println(string);
        printStream.println();
    }
}
