package model;

public class Medication {
    private int prescriptionID;
    private String medicationName;
    private String dosageInstruction;

    // Getters and setters
    public int getPrescriptionID() { return prescriptionID; }
    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }

    public String getMedicationName() { return medicationName; }
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }

    public String getDosageInstruction() { return dosageInstruction; }
    public void setDosageInstruction(String dosageInstruction) { this.dosageInstruction = dosageInstruction; }
}
