package model;

import java.util.Date;

public class TestResult {
    private int patientID;
    private int doctorID;
    private Date date;
    private String time;
    private String name;
    private int labID;
    private String result;
    private int prescriptionID;

    // Getters and setters
    public int getPatientID() { return patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

    public int getDoctorID() { return doctorID; }
    public void setDoctorID(int doctorID) { this.doctorID = doctorID; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLabID() { return labID; }
    public void setLabID(int labID) { this.labID = labID; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public int getPrescriptionID() { return prescriptionID; }
    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }
}
