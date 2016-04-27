package com.example.diplomat.dijoov2;

/**
 * Created by Diplomat on 2/27/2016.
 */
public class DijooUser {

    private int birthYear;
    private String fullName;

    public DijooUser(){}

    public DijooUser(String fullName, int birthYear){
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
