package model;

public class User {
    private int userID;
    private String fullName;
    private String email;
    private boolean isDoctor;
    private boolean isPatient;
    private boolean isStaff;
    //Getters and Setters
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isDoctor() { return isDoctor; }
    public void setDoctor(boolean doctor) { isDoctor = doctor; }

    public boolean isPatient() { return isPatient; }
    public void setPatient(boolean patient) { isPatient = patient; }

    public boolean isStaff() { return isStaff; }
    public void setStaff(boolean staff) { isStaff = staff; }

    public boolean isAdmin() {
        return isStaff && email.toLowerCase().contains("admin");
    }
}