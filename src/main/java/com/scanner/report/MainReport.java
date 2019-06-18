package com.scanner.report;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;


public class MainReport {

    public Path path;


    public void saveToCsv(String textCsv, String toName) throws IOException {
        CsvReport report = new CsvReport(textCsv);

        LocalDate data = LocalDate.now();
        path = Paths.get("d:/DDTrading/dailyReports/report" + toName + data + ".csv");

        report.generateCsv(path);
    }

    public String pathToCsv(){
        return path.toString();
    }
}