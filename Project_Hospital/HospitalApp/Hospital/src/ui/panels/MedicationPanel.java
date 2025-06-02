package ui.panels;

import dao.MedicationDAO;
import model.Medication;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MedicationPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private MedicationDAO dao = new MedicationDAO();

    private JTextField prescriptionIDField, medicationNameField, dosageField;

    public MedicationPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
                "Prescription ID", "Medication Name", "Dosage Instructions"
        }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel form = new JPanel(new GridLayout(2, 3, 5, 5));
        prescriptionIDField = new JTextField();
        medicationNameField = new JTextField();
        dosageField = new JTextField();

        form.add(new JLabel("Prescription ID:"));
        form.add(prescriptionIDField);
        form.add(new JLabel("Medication Name:"));
        form.add(medicationNameField);
        form.add(new JLabel("Dosage Instructions:"));
        form.add(dosageField);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Medication");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(addBtn);
        buttons.add(refreshBtn);

        add(form, BorderLayout.NORTH);
        
        JLabel label = new JLabel("");
        label.setEnabled(false);
        form.add(label);
        add(scrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        loadMedications();

        addBtn.addActionListener(e -> addMedication());
        refreshBtn.addActionListener(e -> loadMedications());
    }

    private void loadMedications() {
        model.setRowCount(0);
        List<Medication> list = dao.getAllMedications();

        for (Medication m : list) {
            model.addRow(new Object[]{
                    m.getPrescriptionID(), m.getMedicationName(), m.getDosageInstruction()
            });
        }
    }

    private void addMedication() {
        try {
            Medication m = new Medication();
            m.setPrescriptionID(Integer.parseInt(prescriptionIDField.getText().trim()));
            m.setMedicationName(medicationNameField.getText().trim());
            m.setDosageInstruction(dosageField.getText().trim());

            if (dao.addMedication(m)) {
                JOptionPane.showMessageDialog(this, "Medication added.");
                loadMedications();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add medication.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }
}
