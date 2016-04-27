package com.example.diplomat.dijoov2;

import java.util.ArrayList;

/**
 * Created by Diplomat on 1/21/2016.
 */
public class Dijoo {

    public String dijooTitle;
    public String dijooCategory;
    public String dijooUnits;
    public String dijooCreationDate;
    public String dijooUser;
    public String dijooToday;
    public int dijooDailyTotal;
    public int dijooOverallTotal;
    public static ArrayList<Dijoo> newDijoo = new ArrayList<>();

    public Dijoo(String dijooUser, String dijooCreationDate, String dijooTitle, String category, String units, String dijooToday, int dailyTotal, int dijooOverallTotal){
        this.dijooUser = dijooUser;
        this.dijooTitle = dijooTitle;
        this.dijooCategory = category;
        this.dijooUnits = units;
        this.dijooCreationDate = dijooCreationDate;
        this.dijooToday = dijooToday;
        this.dijooDailyTotal = dailyTotal;
        this.dijooOverallTotal = dijooOverallTotal;
    }

    public Dijoo(){}

    public String getDijooTitle() {
        return dijooTitle;
    }

    public void setDijooTitle(String dijooTitle) {
        this.dijooTitle = dijooTitle;
    }

    public String getDijooCategory() {
        return dijooCategory;
    }

    public void setDijooCategory(String dijooCategory) {
        this.dijooCategory = dijooCategory;
    }

    public String getDijooCreationDate() {
        return dijooCreationDate;
    }

    public void setDijooCreationDate(String dijooCreationDate) {
        this.dijooCreationDate = dijooCreationDate;
    }

    public String getDijooUnits() {
        return dijooUnits;
    }

    public void setDijooUnits(String dijooUnits) {
        this.dijooUnits = dijooUnits;
    }

    public String getDijooUser() {
        return dijooUser;
    }

    public void setDijooUser(String dijooUser) {
        this.dijooUser = dijooUser;
    }

    public int getDijooDailyTotal() {
        return dijooDailyTotal;
    }

    public void setDijooDailyTotal(int dijooDailyTotal) {
        this.dijooDailyTotal = dijooDailyTotal;
    }

    public String getDijooToday() {
        return dijooToday;
    }

    public void setDijooToday(String dijooToday) {
        this.dijooToday = dijooToday;
    }

    public int getDijooOverallTotal() {
        return dijooOverallTotal;
    }

    public void setDijooOverallTotal(int dijooOverallTotal) {
        this.dijooOverallTotal = dijooOverallTotal;
    }
}
