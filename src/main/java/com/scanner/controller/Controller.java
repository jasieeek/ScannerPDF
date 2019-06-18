package com.scanner.controller;

import com.scanner.addtives.ExecutionTimer;
import com.scanner.addtives.FileSelect;
import com.scanner.report.GeneralReport;
import com.scanner.report.GeneralReportRCG;
import com.scanner.report.GeneralReportSG;
import com.scanner.report.MainReport;
import com.scanner.scan.RCGPDFScan;
import com.scanner.scan.SGPDFScan;
import com.scanner.scan.Scan;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;


public class Controller {
    private Scan scanner;
    private int helperReport = 0;

    @FXML
    private TextArea area;

    @FXML
    private void choose1() {
        scanner = new SGPDFScan();
        scanner.toNameCsv = "SG";
        choose();
        helperReport = 1;
    }

    @FXML
    private void choose2() {
        scanner = new RCGPDFScan();
        scanner.toNameCsv = "RCG";
        choose();
        helperReport = 2;
    }

    private void choose() {
        area.clear();
        FileSelect fileSelect = new FileSelect();
        File fileTmp = fileSelect.openFile();
        if (fileTmp == null) {
            area.appendText("You don't choose a file. Try again.");
            helperReport = 0;
        } else {
            area.setPromptText("I process data...");
            ExecutionTimer timer = new ExecutionTimer();
            timer.TimeOn();
            scanner.setFile(fileTmp);

            String text;
            text = scanner.scan();

            timer.TimeOff();

            if (!text.isEmpty())
                area.appendText("-----------------------------IMPORTANT DATA----------------------------\n\n" + text + "\nExecution time: " + timer.TotalTime()
                        + "s" + "\n\n\n\n(if you want to export data to an Excel file, press the button REPORT\nif you want to make a general report and export his to Word file,\npress the button GENERAL REPORT)");
            else {
                area.appendText("\n\n-----------------------------------WARNING-------------------------------------\n" +
                        "\nI didn't find anything in this file :(" +
                        "\n\n--------------------------------------------------------------------------------");
                helperReport = 0;
            }
        }
    }

    @FXML
    private void reportCsv() throws IOException {
        if(helperReport == 1 || helperReport == 2){
            if (scanner.textForCsv().equals(" ")) {
                area.appendText("\n\n-----------------------------------WARNING-------------------------------------\n" +
                        "\nI have nothing to save : (" +
                        "\n\n--------------------------------------------------------------------------------");
            } else {
                MainReport report = new MainReport();
                report.saveToCsv(scanner.textForCsv(), scanner.toNameCsv);
                area.appendText("\n\n-----------------------------------SUCCESS-------------------------------------\n" +
                        "\nA CSV file was generated successfully!!! : ) \n\nLocation: " + report.pathToCsv() +
                        "\n\n------------------------------------------------------------------------------");
            }
        }
        else{
            area.clear();
            area.appendText("\n\n-----------------------------------WARNING-------------------------------------\n" +
                    "\nI have nothing to save from report \nor the report was not created : (" +
                    "\n\n--------------------------------------------------------------------------------");
        }
    }

    @FXML
    private void generalReportTxt() throws IOException {
        if(helperReport == 1) {
            GeneralReport generalReport = new GeneralReportSG();
            generalReport.setData(scanner.textForGeneralCsv());
            String tmpData = generalReport.returnReport();
            generalReport.saveToTxt(tmpData, "SG");
            area.appendText("\n\n-------------------------------GENERAL REPORT----------------------------------\n" +
                    "\n" + tmpData +
                    "\n\n------------------------------------------------------------------------------" +
                    "\n\n-----------------------------------SUCCESS-------------------------------------\n" +
                    "\nA CSV file was generated successfully!!! : ) \n\nLocation: " + generalReport.pathToTxt() +
                    "\n\n------------------------------------------------------------------------------");
        }
        else if(helperReport == 2) {
            GeneralReport generalReport = new GeneralReportRCG();
            generalReport.setData(scanner.textForGeneralCsv());
            String tmpData = generalReport.returnReport();
            generalReport.saveToTxt(tmpData, "RCG");
            area.appendText("\n\n-------------------------------GENERAL REPORT----------------------------------\n" +
                    "\n" + tmpData +
                    "\n\n------------------------------------------------------------------------------" +
                    "\n\n-----------------------------------SUCCESS-------------------------------------\n" +
                    "\nA CSV file was generated successfully!!! : ) \n\nLocation: " + generalReport.pathToTxt() +
                    "\n\n------------------------------------------------------------------------------");
        }
        else {
            area.clear();
            area.appendText("\n\n-----------------------------------WARNING-------------------------------------\n" +
                    "\nI have nothing to save from general report \nor the general report was not created : (" +
                    "\n\n--------------------------------------------------------------------------------");
        }
    }

    @FXML
    private void info() {
        area.clear();
        String manual = "-----------------------------------MANUAL-----------------------------------\n\n" +
                "The program is used to extract data from PDF files. To use\n" +
                "program, select the file using the SG buttons (if it is PDF\n" +
                "origin of SOCIETY GENERALE) or RCG (if it is a PDF of origin\n" +
                "ROSENTHAL COLLINS GROUP). After a moment the program will throw us into the frame\n" +
                "Data required on the right. The data can be exported to a file\n" +
                "csv (Excel) with the REPORT button. If you want ";
        area.appendText(manual);
    }

    @FXML
    private void exit() {
        System.exit(0);
    }
}
