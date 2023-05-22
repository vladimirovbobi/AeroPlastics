package GUI;

public class Date {
    int day;
    int month;
    int year;
     public Date (int days, int months, int years){
         day = days;
         month = months;
         year = years;
    }
    public String toString(){
         return month+"/"+day+"/"+year;
    }
}
