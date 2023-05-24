package GUI;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Timer;

/**
 * The type Date.
 */
public class Date {

    /**
     * The Day.
     */
    int day;
    /**
     * The Month.
     */
    int month;
    /**
     * The Year.
     */
    int year;
    private static Time localTime;

    /**
     * Instantiates a new Date.
     *
     * @param days   the days
     * @param months the months
     * @param years  the years
     */
    public Date (int days, int months, int years){
         day = days;
         month = months;
         year = years;
    }
    public String toString(){
         return month+"/"+day+"/"+year;
    }

    /**
     * Today's date.
     */
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

    /**
     * Get local time.
     *
     * @return the time
     */
    public static Time getLocalTime(){
         return localTime;
    }

}
