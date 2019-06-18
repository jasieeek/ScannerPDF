package com.scanner.report;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GeneralReportRCG extends GeneralReport {

    public GeneralReportRCG() {
        super();
    }

    public String returnReport(){
        toNameTxt = "RCG";

        String patternStringException = "\\|\\s*Please report any differences immediately to Rosenthal Collins Group, L\\.L\\.C\\. Customer Service Department at \\(312\\) 795-7919. If not resolved, please contact Rosenthal Collins Group, L\\.L\\.C\\.\\|\n" +
                "Compliance Department at \\(312\\) 795-7930\\. The failure to immediately exercise your right to have discrepancies corrected before the beginning of the next market session will be deemed your\\|\n" +
                "agreement that this statement is correct and ratified\\.\\|" +
                "\\s*\\d*\\s[A-Za-z]+\\s[A-Za-z]+\\s[A-Za-z]+,\\s[A-Za-z]+,\\s[A-Za-z]+\\s\\d*\\s*\\|" +
                "\\s*\\(\\d+\\)\\s\\d{3}-\\d{4}\\s+•\\s+FAX:\\s\\(\\d+\\)\\s\\d{3}-\\d{4}\\|" +
                "\\s+RETAIN FOR TAX RECORDS\\s+SUBJECT TO TERMS AND CONDITIONS ON REVERSE SIDE\\|" +
                "\\s+ACCOUNT NUMBER:\\s+[A-Z]\\s+\\d{5}\\s\\d{3}\\s\\d{5}\\s*\\|" +
                "\\s+STATEMENT DATE:\\s+[A-Z]{3}\\s\\d{1,2},\\s\\d{4}\\s*\\|" +
                "(\\s+\\|)+" +
                "\\s+([A-Z]+\\s)+\\s+\\|" +
                "\\s+([A-Z]+\\s)+([A-Z]+-[A-Z]+)?\\s+\\|" +
                "\\s+([A-Z]+\\s)+\\s+\\|" +
                "\\s+\\d*(\\s[A-Z]*)+\\d{2}TH\\s[A-Z]*\\s*\\|" +
                "\\s+([A-Z]+\\s)+\\d*\\s+\\|" +
                "(\\s+\\|)+" +
                "\\s+PAGE\\s+\\d{1,4}\\s*\\|" +
                "(\\s+\\|)+" +
                "\\s+(\\*\\* USD SEGREGATED \\*\\*)?\\s+(\\*\\* CAD SEGREGATED \\*\\*)?\\s+(\\*\\* CONVERTED TOTAL \\*)?";

        String patternString = "\\|\\s*BEGINNING BALANCE\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?\\s*" +
                "(\\s*\\|\\s*COMMISSION\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*EXCHANGE FEES\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*TRANSACTION FEES\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*TOTAL FEES\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*GROSS PROFIT OR LOSS\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*OPTION PREMIUM\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*TOTAL\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*NET MEMO OPTION PREMIUM\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
//                        obsluga dwoch stron
                "(\\s*\\|\\s*ENDING BALANCE\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                //DES        ENDING BALANCE          1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR
                //DES               OPEN TRADE EQUITY          1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR
                "(\\s*\\|\\s*\\|\\s*OPEN TRADE EQUITY\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(" + patternStringException + ")?" +
                "(\\s*\\|\\s*(\\|\\s*)?TOTAL EQUITY\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(" + patternStringException + ")?" +
                "(\\s*\\|\\s*NET MARKET VALUE OF OPTIONS\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*LONG OPTION COST\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*SHORT OPTION PROCEEDS\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*\\|\\s*(\\|\\s*)?ACCOUNT VALUE AT MARKET\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?(\\s*\\|)?";

        String text2 = "";
        text2 += text.lines()
                .collect(Collectors.joining("|\n"));

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text2);

        LinkedList<String> listFirst = new LinkedList<>();

        while (matcher.find()) {
            listFirst.add(matcher.group());
        }

        LinkedList<String> listSecond = new LinkedList<>();

        for(int i=0; i<listFirst.size();i++){
            String tmp = listFirst.get(i);
            if(tmp.length() > 2500){
                String[] tmpParts = tmp.split(patternStringException);
                String part1 = tmpParts[0];
                String part2 = tmpParts[1];
                listSecond.add(i, part1+part2);
            }
            else{
                listSecond.add(i, tmp);
            }
        }

        String patternString1;
        String patternString2;
        patternString1 = "\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s+\\|";
        patternString2 = "\\|\\s+[A-Z]+\\s*[A-Z]*\\s*[A-Z]*\\s*[A-Z]*\\s*[A-Z]*\\s*";

        Pattern pattern1 = Pattern.compile(patternString1);
        Pattern pattern2 = Pattern.compile(patternString2);

        LinkedList<String> endList = new LinkedList<>();

        for(String s: listSecond){
            LinkedList<String> listThird = new LinkedList<>();
            Matcher matcher1 = pattern1.matcher(s);
            Matcher matcher2 = pattern2.matcher(s);

            int count1 = 0;
            while (matcher1.find() && matcher2.find()) {
                listThird.add(matcher2.group());

                listThird.set(count1, matcher2.group());
                listThird.set(count1, listThird.get(count1) + matcher1.group());
                count1++;
            }
            endList.add(listThird.stream()
                    .map(line -> line.replace("|", " "))
                    .map(String::strip)
                    .collect(Collectors.joining("\n")));

        }

        String patternStringAccount;
        patternStringAccount = "\\s+ACCOUNT NUMBER:\\s+[A-Z]\\s+\\d{5}\\s\\d{3}\\s\\d{5}\\s*\\|" +
                "\\s+STATEMENT DATE:\\s+[A-Z]{3}\\s\\d{1,2},\\s\\d{4}\\s*\\|" +
                "(\\s+\\|)+" +
                "\\s+([A-Z]+\\s)+\\s+\\|" +
                "(\\s+([A-Z]+\\s)+([A-Z]+-[A-Z]+)?\\s+\\|)?(\\s+\\*\\*\\s([A-Z]+\\s)+([A-Z]+-[A-Z]+\\s)?[A-Z]+\\*\\*\\s+\\|)?" +
                "\\s+([A-Z]+\\s)+([A-Z]+-[A-Z]+)?\\s+\\|" +
                "\\s+\\d*(\\s[A-Z]*)+\\d{2}TH\\s[A-Z]*\\s*\\|" +
                "\\s+([A-Z]+\\s)+\\d*\\s+\\|";

        Pattern pattern3 = Pattern.compile(patternStringAccount);
        Matcher matcher3 = pattern3.matcher(text2);

        LinkedHashSet<String> setForAccountData = new LinkedHashSet<>();
        while (matcher3.find()) {
            setForAccountData.add(matcher3.group());
        }


        String textForAccountData = setForAccountData.toString();

        String patternStringAccountNumber, patternStringAccountDate, patternStringAccountName;

        patternStringAccountNumber = "ACCOUNT NUMBER:\\s+[A-Z]\\s\\d+\\s\\d+\\s\\d+";
        patternStringAccountDate =  "STATEMENT DATE:\\s+[A-Z]{3}\\s\\d{2},\\s\\d{4}";
        patternStringAccountName =  "\\s+([A-Z]+\\s)+\\s+\\|" +
                "(\\s+([A-Z]+\\s)+([A-Z]+-[A-Z]+)?\\s+\\|)?(\\s+\\*\\*\\s([A-Z]+\\s)+([A-Z]+-[A-Z]+\\s)?[A-Z]+\\*\\*\\s+\\|)?" +
                "\\s+([A-Z]+\\s)+([A-Z]+-[A-Z]+)?\\s+\\|" +
                "\\s+\\d*(\\s[A-Z]*)+\\d{2}TH\\s[A-Z]*\\s*\\|" +
                "\\s+([A-Z]+\\s)+\\d*\\s+\\|";
        Pattern pattern4 = Pattern.compile(patternStringAccountNumber);
        Pattern pattern5 = Pattern.compile(patternStringAccountDate);
        Pattern pattern6 = Pattern.compile(patternStringAccountName);

        Matcher matcher4 = pattern4.matcher(textForAccountData);
        Matcher matcher5 = pattern5.matcher(textForAccountData);
        Matcher matcher6 = pattern6.matcher(textForAccountData);

        LinkedList<String> listForAccountData = new LinkedList<>();

        int count = 0;
        while (matcher4.find() && matcher5.find() && matcher6.find()) {
            String tmpString = matcher6.group().lines()
                    .map(line -> line.replace("|" , " "))
                    .map(line -> line.strip())
                    .collect(Collectors.joining("\n"));
            listForAccountData.add("\n" + "Found " + count + ": \n\n" + matcher4.group() + "\n" + matcher5.group() + "\nName: " + tmpString + "\n" + "Report: \n" + endList.get(count) + "\n\n");
            count++;
        }

        String endText = listForAccountData.stream().collect(Collectors.joining());
        return endText;
    }
}
