package model;

public class Allergy {
    private int userID;
    private int alID;
    private String allergyName;

    // Getters and setters
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public int getAlID() { return alID; }
    public void setAlID(int alID) { this.alID = alID; }

    public String getAllergyName() { return allergyName; }
    public void setAllergyName(String allergyName) { this.allergyName = allergyName; }
}
