package com.scanner.report;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GeneralReportSG extends GeneralReport {

    public GeneralReportSG() {
        super();
    }

    public String returnReport(){
        toNameTxt = "SG";
        String patternString;
        //DES                  BEGINNING ACCOUNT BALANCE          1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR
        patternString = "23\\s*BEGINNING ACCOUNT BALANCE\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?\\s*" +
                "(\\s*23\\s*COMMISSION\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*23\\s*CLEARING FEES\\s*\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*23\\s*EXCHANGE FEES\\s*\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*23\\s*NFA/SEC FEES\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*23\\s*TOTAL COMMISSIONS, FEES, TAXES\\s*\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*23\\s*TOTAL REALIZED PROFIT OR LOSS\\s*\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*23\\s*TOTAL OPTION PREMIUM\\s*\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "\\s*23\\s*ENDING ACCOUNT BALANCE\\s*\\d{0,3},?\\d{0,3},?\\d{1,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?" +
                //DES            ENDING ACCOUNT BALANCE                     1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR
                //DES                             OPEN TRADE EQUITY          1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR
                "(\\s*23\\s*23\\s*OPEN TRADE EQUITY\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "\\s*23\\s*(23\\s*)?TOTAL EQUITY\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?" +
                //DES                       TOTAL EQUITY          1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR
                //DES                             NET MARKET VALUE OF OPTIONS          1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR
                "(\\s*23\\s*23\\s*NET MARKET VALUE OF OPTIONS\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?" +
                "(\\s*23\\s*(23\\s*)?NET LIQUIDATING VALUE\\s*\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?(\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*)?)?(\\s*23)?";
        //DES                                NET LIQUIDATING VALUE           1 ,    018  ,    553    .  27   DR             1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR               1 ,    018  ,    553    .  27   DR


        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        LinkedList<String> listFirst = new LinkedList<>();

        while (matcher.find()) {
            listFirst.add(matcher.group());
        }

        String patternString1;
        String patternString2;
        patternString1 = "\\d{0,3},?\\d{0,3},?\\d{0,3}\\.\\d{2}(DR)?\\s*23";
        patternString2 = "23\\s+[A-Z]+(\\s|\\D)*[A-Z]*,?\\s[A-Z]*,?\\s[A-Z]*,?\\s[A-Z]*\\s";

        Pattern pattern1 = Pattern.compile(patternString1);
        Pattern pattern2 = Pattern.compile(patternString2);

        LinkedList<String> endList = new LinkedList<>();

        for(int i=0; i<listFirst.size();i++){
            LinkedList<String> secList = new LinkedList<>();
            Matcher matcher1 = pattern1.matcher(listFirst.get(i));
            Matcher matcher2 = pattern2.matcher(listFirst.get(i));

            int count1 = 0;
            while (matcher1.find() && matcher2.find()) {
                secList.add(matcher2.group());

                secList.set(count1, matcher2.group());
                secList.set(count1, secList.get(count1) + matcher1.group());
                count1++;
            }
            endList.add(secList.stream()
                    .map(line -> line.replace("23"," "))
                    .map(line -> line.replace(" , ", " "))
                    .map(String::strip)
                    .collect(Collectors.joining("\n")));
        }

        String[] arrayTmp = new String[endList.size()];
        int i = 0;
        for (String s :endList) {
            arrayTmp[i++] = s;
        }

        String patternString3;

        patternString3 = "[A-Z]{3}\\s[0-9]{2},\\s[0-9]{4}\\s*23\\s*[A-Z]*\\s[A-Z]*\\s[A-Z]*\\s*23\\s*[0-9]*\\s[A-Z]*\\s[A-Z]*\\s-\\s[A-Z]*\\s[0-9]*\\s*Q PI[0-9]*\\s[0-9]*\\s*23\\s*[A-Z]*\\s[A-Z]*\\s[0-9]*";

        Pattern pattern3 = Pattern.compile(patternString3);
        Matcher matcher3 = pattern3.matcher(text);

        LinkedHashSet<String> zbior2 = new LinkedHashSet<>();

        while (matcher3.find()) {
            zbior2.add(matcher3.group());
        }

        String[] arrayEnd = new String[zbior2.size()];
        int j = 0;
        for (String s : zbior2)
            arrayEnd[j++] = s;

        for(int k = 0; k < arrayEnd.length; k++)
        {
            String[] tabTmp = arrayEnd[k].split(" Q ");

            String[] tabTmp1 = tabTmp[0].split("23");

            String[] tabTmp2 = tabTmp[1].split("23");

            String data = tabTmp1[0];
            String name = tabTmp1[1] + tabTmp1[2] + tabTmp2[1];
            String number = tabTmp2[0];

            arrayEnd[k] = "\n\nDate: " + data + "\nName: " + name + "\nNumber: " + number;
        }

        String textEnd = "";
        for(int l = 0; l < arrayEnd.length; l++){
            String tmp1 = "\n" + arrayTmp[l] + "\n";
            arrayEnd[l] = arrayEnd[l] + "\n" + tmp1;
            textEnd += "Found " + (l+1) + ": \n" + arrayEnd[l] + "\n\n";
        }

        return textEnd;
    }
}
