package com.scanner.addtives;


public class ExecutionTimer {
    public long start;
    public long stop;

    public void TimeOn() {
        this.start = System.currentTimeMillis();
    }
    public void TimeOff(){
        this.stop = System.currentTimeMillis();
    }
    public float TotalTime(){
        float tmp = stop-start;
        float total = tmp/1000;   //seconds
        total *= 100;
        total = Math.round(total);
        total /= 100;
        return total;
    }
}
