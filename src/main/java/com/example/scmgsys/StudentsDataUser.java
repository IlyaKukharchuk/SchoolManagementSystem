package com.example.scmgsys;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentsDataUser {
    private final StringProperty FIRSTNAME;
    private final StringProperty LASTNAME;
    private final StringProperty SECTION;
    private final IntegerProperty MONTHHOURS;
    private final IntegerProperty HOURS;



    public StudentsDataUser(String firstName, String lastName, String section, Integer monthHours, Integer hours) {
        this.FIRSTNAME = new SimpleStringProperty(firstName);
        this.LASTNAME = new SimpleStringProperty(lastName);
        this.SECTION = new SimpleStringProperty(section);
        this.MONTHHOURS = new SimpleIntegerProperty(monthHours);
        this.HOURS = new SimpleIntegerProperty(hours);
    }

    public String getFIRSTNAME() {
        return FIRSTNAME.get();
    }

    public StringProperty FIRSTNAMEProperty() {
        return FIRSTNAME;
    }

    public void setFIRSTNAME(String FIRSTNAME) {
        this.FIRSTNAME.set(FIRSTNAME);
    }

    public String getLASTNAME() {
        return LASTNAME.get();
    }

    public StringProperty LASTNAMEProperty() {
        return LASTNAME;
    }

    public void setLASTNAME(String LASTNAME) {
        this.LASTNAME.set(LASTNAME);
    }


    public String getSECTION() {
        return SECTION.get();
    }

    public StringProperty SECTIONProperty() {
        return SECTION;
    }

    public void setSECTION(String SECTION) {
        this.SECTION.set(SECTION);
    }

    public int getHOURS() {
        return HOURS.get();
    }

    public IntegerProperty HOURSProperty() {
        return HOURS;
    }

    public void setHOURS(int HOURS) {
        this.HOURS.set(HOURS);
    }

    public int getMONTHHOURS() {
        return MONTHHOURS.get();
    }

    public IntegerProperty MONTHHOURSProperty() {
        return MONTHHOURS;
    }

    public void setMONTHHOURS(int MONTHHOURS) {
        this.MONTHHOURS.set(MONTHHOURS);
    }
}