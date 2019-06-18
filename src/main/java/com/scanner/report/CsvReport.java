package com.scanner.report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class CsvReport {
    List<CsvReportRecord> reportRecords = new LinkedList<>();

    public CsvReport(String data) {
        this.reportRecords.addAll(data.lines()
                .map(CsvReportRecord::lineToReportRecord)
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return reportRecords.stream()
                .map(CsvReportRecord::toCscRow)
                .collect(Collectors.joining("\n"));
    }

    void generateCsv(Path path) throws IOException {
        Files.writeString(path, toString());
    }
}