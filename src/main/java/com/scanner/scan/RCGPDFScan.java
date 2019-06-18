package com.scanner.scan;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RCGPDFScan extends Scan {
    public RCGPDFScan() {
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
            String text2 = new String();
            text = pdfStripper.getText(pdDoc);
            textGeneralCsv += text;

            String patternString;
            String patternStringShort;
            //                           |                       CONTRACT DESCRIPTION                                 |      EX     |                 PRICE                  |    CC     |                DEBIT/CREDIT              | 23?    SPREAD   TRANSACTION    23?   |     SHORT/LONG      |                       BEFORE CLOSE                              |   CLOSE   |     PRICE     |
            //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            //OPIS            PUT      SEP     19       CME           LEAN       HOGS      WHT          520           06       270    .  00        7 / 8         CD       940 ,   100     .    00  DR       50   *           2    .  4     - DQ      .519   EX      8  / 10 / 19        CLOSE OR S. P.      24   . 42           7 / 8
            patternString = "[A-Z]*\\s*[A-Z]+\\s\\d{2}(\\s[A-Z]+)?(\\s[A-Z]+)?\\s[A-Z]+\\s[A-Z]+(\\s*\\d{3,4})?\\s*\\d{2}\\s*(\\d+)?\\.\\d{2,4}(\\s*\\d/\\d)?\\s*\\w{2}\\s*(\\d+,)?(\\d+)?\\.\\d{2}(DR)?\\s*\\d+\\*\\s*((\\s*\\d*\\.\\d(\\s|-)DQ\\s*\\.\\d*)?EX-\\s*\\d+/\\d+/\\d+)?\\s*(CLOSE|S\\.P\\.)\\s*\\d*\\.\\d{2,4}(\\s*\\d/\\d)?";

            patternStringShort = "[A-Z]*\\s*[A-Z]+\\s\\d{2}(\\s[A-Z]+)?(\\s[A-Z]+)?\\s[A-Z]+\\s[A-Z]+(\\s*\\d{3,4})?\\s*\\d{2}\\s*(\\d+)?\\.\\d{2,4}(\\s*\\d/\\d)?\\s*\\w{2}\\s*(\\d+,)?(\\d+)?\\.\\d{2}(DR)?\\s{47,50}\\d+\\*\\s*((\\s*\\d*\\.\\d(\\s|-)DQ\\s*\\.\\d*)?EX-\\s*\\d+/\\d+/\\d+)?\\s*(CLOSE|S\\.P\\.)\\s*\\d*\\.\\d{2,4}(\\s*\\d/\\d)?";

            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(text);

            Pattern patternShort = Pattern.compile(patternStringShort);
            Matcher tmpMatcherShort;

            String tmpText;
            int count = 0;
            while(matcher.find()) {
                count++;
                tmpText = matcher.group();
                tmpMatcherShort = patternShort.matcher(tmpText);
                if(tmpMatcherShort.find())
                    text2 += "Found " + count + ": \n" + matcher.group() + " SHORT \n";
                else
                    text2 += "Found " + count + ": \n" + matcher.group() + " LONG \n";
            }

            String patternString1;
            patternString1 = "([A-Z]+\\s+)?[A-Z]+\\s\\d{2}(\\s[A-Z]+)?(\\s[A-Z]+)?\\s[A-Z]+\\s[A-Z]+(\\s*\\d{3,4})?";

            String patternString2;
            patternString2 = "\\d*\\*";

            String patternString3;
            patternString3 = "(CLOSE|S\\.P\\.)\\s*\\d*\\.\\d{2,4}(\\s*\\d/\\d)?\\s(LONG|SHORT)";

            Pattern pattern1 = Pattern.compile(patternString1);
            Pattern pattern2 = Pattern.compile(patternString2);
            Pattern pattern3 = Pattern.compile(patternString3);
            Matcher matcher1 = pattern1.matcher(text2);
            Matcher matcher2 = pattern2.matcher(text2);
            Matcher matcher3 = pattern3.matcher(text2);

            //DATA FOR EXCEL

            //String textCsv = "";
            while (matcher1.find() && matcher2.find() && matcher3.find()) {
                textCsv += matcher1.group() + "  |  " + matcher2.group() + "    " + matcher3.group() + "\n";
            }

            matcher1.reset();
            matcher2.reset();
            matcher3.reset();

            int count2 = 0;
            while(matcher1.find() && matcher2.find() && matcher3.find()) {
                count2++;
                text3 += "FOUND " + count2 + ":\n" + matcher1.group() + "    " + matcher2.group() + "    " + matcher3.group() + "\n";
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
