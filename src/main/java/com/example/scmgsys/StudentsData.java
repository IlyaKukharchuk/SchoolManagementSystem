package com.example.scmgsys;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentsData {
    private final StringProperty ID;
    private final StringProperty FIRSTNAME;
    private final StringProperty LASTNAME;
    private final StringProperty EMAIL;
    private final StringProperty DOB;
    private final StringProperty SECTION;
    private final IntegerProperty HOURS;
    private final IntegerProperty MONTHHOURS;
    private final StringProperty IMAGE;

    public StudentsData(String id, String firstName, String lastName, String email, String dob, String section, Integer hours, Integer monthHours, String image) {
        this.ID = new SimpleStringProperty(id);
        this.FIRSTNAME = new SimpleStringProperty(firstName);
        this.LASTNAME = new SimpleStringProperty(lastName);
        this.EMAIL = new SimpleStringProperty(email);
        this.DOB = new SimpleStringProperty(dob);
        this.SECTION = new SimpleStringProperty(section);
        this.HOURS = new SimpleIntegerProperty(hours);
        this.MONTHHOURS = new SimpleIntegerProperty(monthHours);
        this.IMAGE = new SimpleStringProperty(image);
    }


    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
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

    public String getEMAIL() {
        return EMAIL.get();
    }

    public StringProperty EMAILProperty() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL.set(EMAIL);
    }

    public String getDOB() {
        return DOB.get();
    }

    public StringProperty DOBProperty() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB.set(DOB);
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

    public String getIMAGE() {
        return IMAGE.get();
    }

    public StringProperty IMAGEProperty() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE.set(IMAGE);
    }
}