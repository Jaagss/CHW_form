package com.example.chwform;

public class FormData {
    private static final FormData instance = new FormData();

    private FormData() {}

    public static FormData getInstance() {
        return instance;
    }

    public String householdId;
    public String visitDate;
    public String village;
    public String state;
    public String district;
    public String householdSize;


    public String symptoms;
    public String modeOfMedication;
    public String antibiotics;
    public String patientId;
    public String name;
    public String age;
    public String gender;
    public String occupation;
    public byte[] antibioticImage;  // For image handling
    public String imageMimeType;
    public String obtainedFrom;
    public String dateOfAntibioticUsed;
    public String dosage;
    public String unit;
    public String duration;
    public boolean fullCourseTaken;
    public String doctor;
    public String antibioticMisuse;
    public String antibioticResistance;
    public boolean wantInfo;

}
