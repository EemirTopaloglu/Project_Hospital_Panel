package model;

import java.util.Date;

public class Patient {
	//Transferring patient data between UI and database
	//Populating GUI tables
	//Holding user input before insert/update	
    private int userID;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Date birthDate;
    private String gender;
    private String insurancePolId;
    private String password; // Added password field

    // Getters and setters
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

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getInsurancePolId() { return insurancePolId; }
    public void setInsurancePolId(String insurancePolId) { this.insurancePolId = insurancePolId; }

    // Password getter and setter
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}