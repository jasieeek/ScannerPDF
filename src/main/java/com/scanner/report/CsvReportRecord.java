package com.scanner.report;

class CsvReportRecord{
    private static final String SEPARATOR = ";";
    String name;
    String amount;
    String price;

    public CsvReportRecord(String name, String amount, String price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    static CsvReportRecord lineToReportRecord(String line){

        String[] lineTab1 = line.split("(CLOSE|S\\.P\\.)");
        String[] lineTab2 = lineTab1[0].split("\\|");
        return new CsvReportRecord(lineTab2[0].trim(),lineTab2[1].trim(),lineTab1[1].trim().replace(".",","));
    }

    String toCscRow(){
        return name + SEPARATOR + amount + SEPARATOR + price;
    }
}