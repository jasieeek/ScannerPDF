package com.scanner.scan;

import java.io.File;

public abstract class Scan {
    public String toNameCsv;
    protected String text3 = new String();
    protected String textCsv = " ";
    protected String textGeneralCsv = " ";
    protected File file;

    public abstract String scan();

    public String textForCsv(){
        return textCsv;
    }

    public String textForGeneralCsv(){
        return textGeneralCsv;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
