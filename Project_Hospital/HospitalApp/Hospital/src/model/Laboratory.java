package model;

public class Laboratory {
    private int labID;
    private String name;
    private int connectedDept;
    private String phone;
    private int respDoctorID;
    private int respSID;

    // Getters and setters
    public int getLabID() { return labID; }
    public void setLabID(int labID) { this.labID = labID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getConnectedDept() { return connectedDept; }
    public void setConnectedDept(int connectedDept) { this.connectedDept = connectedDept; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getRespDoctorID() { return respDoctorID; }
    public void setRespDoctorID(int respDoctorID) { this.respDoctorID = respDoctorID; }

    public int getRespSID() { return respSID; }
    public void setRespSID(int respSID) { this.respSID = respSID; }
}
