package GUI;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;
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
    public static String todaysDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now));
    }
    public static String changeTodaysDateByDays(int days){
        String todaysDate = todaysDate();
        StringBuilder stringBuilder = new StringBuilder(todaysDate);
        char day1 = todaysDate.charAt(3);
        char day2 = todaysDate.charAt(4);

        char year = todaysDate.charAt(9);
        int daytoInt = Character.getNumericValue(day1) * 10 + Character.getNumericValue(day2);
        daytoInt += days;
        int yearToInt = Character.getNumericValue(year);
        char month1 = todaysDate.charAt(0);
        char month2 = todaysDate.charAt(1);
        String years = stringBuilder.substring(5);
        int monthToInt=Character.getNumericValue(month1) * 10 + Character.getNumericValue(month2);
        if(daytoInt > 31){

            if(month1 == '1' && month2 == '2'){
                stringBuilder.replace(5,9,"202"+Character.toString(yearToInt+1));
                years = stringBuilder.substring(5);
            }else if(month2 == '9'){
                month1 = '1';
                month2 = '0';
            }else{
                monthToInt = Character.getNumericValue(month2);
                monthToInt +=1;
                month2 = Character.forDigit(monthToInt,10);
            }
            daytoInt -= 31;
            if(daytoInt > 10){
                if(daytoInt > 20){
                    daytoInt -= 20;
                    day2 = Character.forDigit(daytoInt,10);
                    day1 = '2';
                }else if (daytoInt > 29 && daytoInt < 31){
                    daytoInt -= 30;
                    day2 = Character.forDigit(daytoInt,10);
                    day1 = '3';
                } else if (daytoInt > 31) {
                    todaysDate =  changeTodaysDateByDays(daytoInt - 31);
                }else{
                    daytoInt -= 10;
                    day2 = Character.forDigit(daytoInt,10);
                    day1 = '1';

                }



            }else{
                day2 = Character.forDigit(daytoInt,10);
                day1 = '0';
            }

        }
        StringBuilder build  = new StringBuilder();

        build.append(month1);
        build.append(month2);
        build.append('/');
        build.append(day1);
        build.append(day2);
        build.append(years);

        String str  = build.toString();

        stringBuilder.replace(0,10,str);
        return stringBuilder.toString();
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
