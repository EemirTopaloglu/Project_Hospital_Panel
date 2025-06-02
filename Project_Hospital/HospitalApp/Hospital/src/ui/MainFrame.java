package ui;



import model.User;
import ui.panels.*;

import javax.swing.*;
import java.awt.*;




public class MainFrame extends JFrame {
    public MainFrame(User user) {
        setTitle("Hospital Management System - Logged in as: " + user.getFullName());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        if (user.isAdmin()) {
            tabs.addTab("Patients", new PatientPanel());
            tabs.addTab("Doctors", new DoctorPanel());
            tabs.addTab("Departments", new DepartmentPanel());
            tabs.addTab("Visits", new VisitPanel());
            tabs.addTab("Test Results", new TestPanel());
            tabs.addTab("Reports", new ReportPanel());
            tabs.addTab("Medications", new MedicationPanel());
            tabs.addTab("Allergies", new AllergyPanel());
            tabs.addTab("Laboratories", new LaboratoryPanel());
            tabs.addTab("User Management", new UserPanel());
        } else if (user.isDoctor()) {
            tabs.addTab("Visits", new VisitPanel());
            tabs.addTab("Test Results", new TestPanel());
            tabs.addTab("Reports", new ReportPanel());
            tabs.addTab("Medications", new MedicationPanel());
            tabs.addTab("Allergies", new AllergyPanel());
        } else if (user.isPatient()) {
            tabs.addTab("My Visits", new VisitPanel()); // Simplified
            tabs.addTab("My Tests", new TestPanel());
            tabs.addTab("My Reports", new ReportPanel());
        } else if (user.isStaff()) {
            tabs.addTab("Patients", new PatientPanel());
            tabs.addTab("Visits", new VisitPanel());
            tabs.addTab("Reports", new ReportPanel());
        } else if (user.isAdmin() || user.isDoctor()) {
            tabs.addTab("Medications", new MedicationPanel());
            tabs.addTab("Allergies", new AllergyPanel());
        } 


        add(tabs, BorderLayout.CENTER);
    }
}