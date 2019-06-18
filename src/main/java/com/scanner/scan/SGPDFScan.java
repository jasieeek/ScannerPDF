package com.scanner.scan;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SGPDFScan extends Scan {
    public SGPDFScan() {
        super();
    }


    public String scan(){
        try {
            PDFParser parser;
            PDFTextStripper pdfStripper;
            PDDocument pdDoc;
            COSDocument cosDoc;

            parser = new PDFParser(new RandomAccessFile(file, "r"));

            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            pdDoc.getNumberOfPages();
            pdfStripper.setStartPage(pdfStripper.getStartPage());
            pdfStripper.setEndPage(pdfStripper.getEndPage());

            String text;
            String text2 = "";
            text = pdfStripper.getText(pdDoc);
            textGeneralCsv += text;


            String patternStringAll;
            String patternStringAll1;
            String patternStringAll2;
            String patternStringShort;
            //                           |                       CONTRACT DESCRIPTION                                 |      EX     |                 PRICE                  |    CC     |                DEBIT/CREDIT              | 23?    SPREAD   TRANSACTION    23?   |     SHORT/LONG      |                       BEFORE CLOSE                              |   CLOSE   |     PRICE     |
            //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            //OPIS                 CALL      DEC        19     COFFEE    ROBUSTA      'C'             1300            06         122    .  2    5         1  /  2           US         2   ,   6     5     .  2    5   DR      23     SPREAD   TRANSACTION    23         556    *                .  5      - DQ      . 180  EX-      12  /  14  /  18        CLOSE     1122   . 81
            patternStringShort = "[A-Z]+\\s*[A-Z]*\\s[0-9]{2}\\s[A-Z]+\\s[A-Z]*(\\s*'[A-Z]')?\\s*([0-9]{2,4})?\\s*[0-9][0-9]\\s*[0-9]*\\.[0-9][0-9](\\s*[0-9]/[0-9])?\\s*[A-Z]{2}\\s*[0-9]*,?[0-9]*[0-9]?\\.[0-9]{2}(\\s*DR)?\\s*23(\\s*SPREAD\\sTRANSACTION\\s*23)?\\s{47,49}[0-9]*\\*\\s*([0-9]*\\.[0-9]\\s?-?DQ\\s*\\.[0-9]*EX-\\s*[0-9]*/[0-9]*/[0-9]*\\s*)?CLOSE\\s*[0-9]*.[0-9]{2}";

            patternStringAll = "[A-Z]+\\s*[A-Z]*\\s[0-9]{2}\\s[A-Z]+\\s[A-Z]*(\\s*'[A-Z]')?\\s*([0-9]{2,4})?\\s*[0-9][0-9]\\s*[0-9]*\\.[0-9][0-9](\\s*[0-9]/[0-9])?\\s*[A-Z]{2}\\s*[0-9]*,?[0-9]*[0-9]?\\.[0-9]{2}(\\s*DR)?\\s*23(\\s*SPREAD\\sTRANSACTION\\s*23)?\\s*[0-9]*\\*\\s*([0-9]*\\.[0-9]\\s?-?DQ\\s*\\.[0-9]*EX-\\s*[0-9]*/[0-9]*/[0-9]*\\s*)?CLOSE\\s*[0-9]*.[0-9]{2}";


            patternStringAll1 = "[A-Z]+\\s*[A-Z]*\\s[0-9]{2}\\s[A-Z]+\\s[A-Z]*(\\s*'[A-Z]')?\\s*([0-9]{2,4})?\\s*[0-9][0-9]\\s*[0-9]*\\.[0-9][0-9](\\s*[0-9]/[0-9])?\\s*[A-Z]{2}\\s*[0-9]*,?[0-9]*[0-9]?\\.[0-9]{2}(\\s*DR)?\\s*23(\\s*SPREAD\\sTRANSACTION\\s*23)?\\s*[0-9]*\\*";
            patternStringAll2 = "\\s*([0-9]*\\.[0-9]\\s?-?DQ\\s*\\.[0-9]*EX-\\s*[0-9]*/[0-9]*/[0-9]*\\s*)?CLOSE\\s*[0-9]*.[0-9]{2}";

            Pattern pattern = Pattern.compile(patternStringAll);
            Matcher matcher = pattern.matcher(text);

            Pattern patternShort = Pattern.compile(patternStringShort);
            Pattern patternAll1 = Pattern.compile(patternStringAll1);
            Pattern patternAll2 = Pattern.compile(patternStringAll2);
            Matcher tmpMatcherShort;
            Matcher tmpMatcherAll1;
            Matcher tmpMatcherAll2;

            String tmpText;
            int count = 0;
            while(matcher.find()) {
                count++;
                tmpText = matcher.group();
                tmpMatcherShort = patternShort.matcher(tmpText);
                if(tmpMatcherShort.find()){
                    String tmpString = matcher.group();
                    tmpMatcherAll1 = patternAll1.matcher(tmpString);
                    tmpMatcherAll2 = patternAll2.matcher(tmpString);
                    if(tmpMatcherAll1.find() && tmpMatcherAll2.find()){
                        text2 += "Found " + count + ": \n" + tmpMatcherAll1.group() + " SHORT" + tmpMatcherAll2.group() + "\n";
                    }
                }
                else{
                    String tmpString = matcher.group();
                    tmpMatcherAll1 = patternAll1.matcher(tmpString);
                    tmpMatcherAll2 = patternAll2.matcher(tmpString);

                    if(tmpMatcherAll1.find() && tmpMatcherAll2.find()){
                        text2 += "Found " + count + ": \n" + tmpMatcherAll1.group() + " LONG" + tmpMatcherAll2.group() + "\n";
                    }
                }

            }

            String patternString1 = "[A-Z]+\\s*[A-Z]*\\s[0-9]{2}\\s[A-Z]+\\s[A-Z]*(\\s*'[A-Z]')?(\\s*[0-9]{3,4})?";

            String patternString2 = "\\d*\\*\\s(LONG|SHORT)";

            String patternString3 = "CLOSE\\s*[0-9]*\\.[0-9]{2}";

            Pattern pattern1 = Pattern.compile(patternString1);
            Pattern pattern2 = Pattern.compile(patternString2);
            Pattern pattern3 = Pattern.compile(patternString3);
            Matcher matcher1 = pattern1.matcher(text2);
            Matcher matcher2 = pattern2.matcher(text2);
            Matcher matcher3 = pattern3.matcher(text2);

            //DATA FOR EXCEL

            //String textCsv = "";
            List<String> list = new LinkedList<>();
            while (matcher1.find() && matcher2.find() && matcher3.find()) {
                textCsv += matcher1.group() + "  |  " + matcher2.group() + "    " + matcher3.group() + "\n";
                list.add(matcher1.group() + "   " + matcher2.group() + "    " + matcher3.group());
            }


            String textToSortStrike = textCsv;

            SortStrike sortText = new SortStrike();
            sortText.sort(list, textToSortStrike);

            matcher1.reset();
            matcher2.reset();
            matcher3.reset();

            int count2 = 0;
            while(matcher1.find() && matcher2.find() && matcher3.find()) {
                count2++;
                text3 += "FOUND " + count2 + ":\n" + matcher1.group() + " " + matcher2.group() + "  " + matcher3.group() + "\n";
            }

            if(!text3.isEmpty()) {
                text3 = "------------------------------------------------------------------------------\n" +
                        "------------------------------------START--------------------------------\n" +
                        "------------------------------------------------------------------------------\n\n" + text3;
                text3 += "\n\n------------------------------------------------------------------------------\n" +
                        "-------------------------------------END---------------------------------\n" +
                        "------------------------------------------------------------------------------\n\n";
            }

            pdDoc.close();
            cosDoc.close();

            return text3;
        }
        catch(IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public String textForCsv(){
        return textCsv;
    }

    public String textForGeneralCsv(){
        return textGeneralCsv;
    }

}
