package com.scanner.scan;

public class Element implements Comparable<Element>{
    private String prefix;
    private String name;
    private int strike;
    private String amount;
    private String value;

    public Element(String prefix , String name , int strike, String amount , String value) {
        this.prefix = prefix.trim();
        this.name = name.trim();
        this.strike = strike;
        this.amount = amount.trim();
        this.value = value.trim();
    }

    public String getPrefix() {
        return prefix;
    }

    public String getName() {
        return name;
    }

    public int getStrike() {
        return strike;
    }

    public String getAmount() {
        return amount;
    }

    public String getValue() {
        return value;
    }

    public String toString(){
        return prefix + " " + name + " " + strike + " "  + amount + " " + value + "\n";
    }

    @Override
    public int compareTo(Element o) {
        if(this.getName().compareTo(o.getName()) == 0){
           return Integer.compare(this.getStrike(),o.getStrike());
        }
        else
            return this.getName().compareTo(o.getName());
    }
}
