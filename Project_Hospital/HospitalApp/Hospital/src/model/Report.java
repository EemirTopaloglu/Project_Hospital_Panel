package model;

public class Report {
    private int reportID;
    private String diagnosis;
    private double billing;

    // Getters and setters
    public int getReportID() { return reportID; }
    public void setReportID(int reportID) { this.reportID = reportID; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public double getBilling() { return billing; }
    public void setBilling(double billing) { this.billing = billing; }
}
