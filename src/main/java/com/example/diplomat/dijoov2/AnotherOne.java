package com.example.diplomat.dijoov2;

import java.util.Objects;

/**
 * Created by Diplomat on 3/4/2016.
 */
public class AnotherOne {

    String commitDate;
    int valueString;
    String time;
    String itemKey;

    public AnotherOne(){}

    public AnotherOne(int valueString, String time, String date, String dijooItemKey){
        this.valueString = valueString;
        this.time = time;
        this.commitDate = date;
        this.itemKey = dijooItemKey;

    }

    public String getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(String commitDate) {
        this.commitDate = commitDate;
    }

    public int getValueString() {
        return valueString;
    }

    public void setValueString(int valueString) {
        this.valueString = valueString;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public int getAllDailyInt(String itemKey, AnotherOne anotherOne, String today){{

        int total = 0;

        if(Objects.equals(anotherOne.getItemKey(), itemKey) && Objects.equals(anotherOne.getCommitDate(), today)){

            total = total + getValueString();

        }

        return total;

        }


    }
}
