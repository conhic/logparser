package com.clarity.log.application;

import java.util.Collection;

public interface OutputFormatter {
    void writeTitle(String title);
    void write(Collection<String> collection);
    void write(String string);
}
