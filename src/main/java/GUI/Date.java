package GUI;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Timer;

public class Date {

    int day;
    int month;
    int year;
    private static Time localTime;
     public Date (int days, int months, int years){
         day = days;
         month = months;
         year = years;
    }
    public String toString(){
         return month+"/"+day+"/"+year;
    }
    public static void todaysDate(){
        while (true) {
            localTime = Time.valueOf(LocalTime.now());
            System.out.println(localTime);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static Time getLocalTime(){
         return localTime;
    }

}
