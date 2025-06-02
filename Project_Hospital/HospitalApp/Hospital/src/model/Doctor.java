package model;

public class Doctor {
    private int userID;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String specialty;
    private int workingIn; // departmentID
    private String password; 

    // Existing getters and setters
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public int getWorkingIn() { return workingIn; }
    public void setWorkingIn(int workingIn) { this.workingIn = workingIn; }

    // New password getter and setter
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}