package model;

public class Department {
    private int departmentID;
    private String name;
    private String specialty;
    private int medManagerID;
    private int adManagerID;

    // Getters and setters
    public int getDepartmentID() { return departmentID; }
    public void setDepartmentID(int departmentID) { this.departmentID = departmentID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public int getMedManagerID() { return medManagerID; }
    public void setMedManagerID(int medManagerID) { this.medManagerID = medManagerID; }

    public int getAdManagerID() { return adManagerID; }
    public void setAdManagerID(int adManagerID) { this.adManagerID = adManagerID; }
}
