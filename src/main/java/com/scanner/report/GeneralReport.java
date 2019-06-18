package com.scanner.report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public abstract class GeneralReport {
    protected String toNameTxt;
    protected String text;
    protected Path path;

    public void setData(String data) {
        this.text = data;
    }

    public abstract String returnReport();

    public void saveToTxt(String textTxt, String toName) throws IOException {

        LocalDate data = LocalDate.now();
        path = Paths.get("d:/DDTrading//dailyReports/generalReport" + toName + data + ".doc");


        Files.writeString(path, textTxt);
    }

    public String pathToTxt(){
        return path.toString();
    }
}
