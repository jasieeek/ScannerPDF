package com.scanner.scan;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortStrike {

    public void sort(List<String> list, String text) {

        //System.out.println(list.toString() + "\n\nList size: " + list.size());

        String patternStringPrefix;
        String patternStringName;
        String patternStringStrike;
        String patternStringAmount;
        String patternStringValue;
        patternStringPrefix = "(CALL|PUT)?";
        patternStringName = "\\s*[A-Z]{3}\\s[0-9]{2}(\\s[A-Z]+)+(\\s*'[A-Z]')?\\s";
        patternStringStrike = "\\s[0-9]{3,4}\\s";
        patternStringAmount = "\\s[0-9]+\\*\\s(SHORT|LONG)\\s";
        patternStringValue = "\\sCLOSE\\s*[0-9]*.[0-9]{2}";
        Pattern patternPrefix = Pattern.compile(patternStringPrefix);
        Pattern patternName = Pattern.compile(patternStringName);
        Pattern patternStrike = Pattern.compile(patternStringStrike);
        Pattern patternAmount = Pattern.compile(patternStringAmount);
        Pattern patternValue = Pattern.compile(patternStringValue);
/*
        Matcher matcherPrefix = patternPrefix.matcher(text);
        Matcher matcherName = patternName.matcher(text);
        Matcher matcherStrike = patternStrike.matcher(text);
        Matcher matcherAmount = patternAmount.matcher(text);
        Matcher matcherValue = patternValue.matcher(text);

        List<Element> listStrike = new LinkedList<>();

        while(matcherName.find() && matcherStrike.find() && matcherAmount.find() && matcherValue.find()){
            String tmpString = " ";
            if(matcherPrefix.find()){
                tmpString = matcherPrefix.group();
            }
            listStrike.add(new Element(tmpString, matcherName.group(), Integer.parseInt(matcherStrike.group().trim()), matcherAmount.group(), matcherValue.group()));
        }

        System.out.println(listStrike.toString() + "\n\nList size: " + listStrike.size());
        //System.out.println(text);
*/
        List<Element> listStrike = new LinkedList<>();

        for (int i = 0; i < list.size(); i++) {
            Matcher matcherPrefix = patternPrefix.matcher(list.get(i));
            Matcher matcherName = patternName.matcher(list.get(i));
            Matcher matcherStrike = patternStrike.matcher(list.get(i));
            Matcher matcherAmount = patternAmount.matcher(list.get(i));
            Matcher matcherValue = patternValue.matcher(list.get(i));
            String tmpPrefix;
            int tmpStrike;
            if (matcherPrefix.find() && matcherStrike.find()) {
                while (matcherName.find()  && matcherAmount.find() && matcherValue.find()) {
                    listStrike.add(new Element(matcherPrefix.group() , matcherName.group() , Integer.parseInt(matcherStrike.group().trim()) , matcherAmount.group() , matcherValue.group()));
                }
            } else {
                tmpPrefix = "";
                tmpStrike = 0;
                while (matcherName.find() && matcherAmount.find() && matcherValue.find()) {
                    listStrike.add(new Element(tmpPrefix , matcherName.group() , tmpStrike , matcherAmount.group() , matcherValue.group()));
                }
            }
        }
        System.out.println(listStrike.toString() + "\n\nList size before sort: " + listStrike.size());

        Collections.sort(listStrike);

        System.out.println(listStrike.toString() + "\n\nList size after sort: " + listStrike.size());

//        for(Element s: listStrike){
//            System.out.println(s.getName());
//        }
    }




}
