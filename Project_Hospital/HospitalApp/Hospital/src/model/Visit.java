package model;

import java.util.Date;

public class Visit {
    private int patientID;
    private int doctorID;
    private Date date;
    private String time;
    private String status;
    private Integer reportID;

    // Getters and setters
    public int getPatientID() { return patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

    public int getDoctorID() { return doctorID; }
    public void setDoctorID(int doctorID) { this.doctorID = doctorID; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getReportID() { return reportID; }
    public void setReportID(Integer reportID) { this.reportID = reportID; }
}
