package ui.panels;

import dao.TestDAO;
import model.TestResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TestDAO dao = new TestDAO();

    private JTextField patientIDField, doctorIDField, dateField, timeField, nameField, labIDField, resultField, prescriptionIDField;

    public TestPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
                "PatientID", "DoctorID", "Date", "Time", "Test Name", "LabID", "Result", "PrescriptionID"
        }, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel form = new JPanel(new GridLayout(3, 6, 5, 5));
        patientIDField = new JTextField();
        doctorIDField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();
        nameField = new JTextField();
        labIDField = new JTextField();
        resultField = new JTextField();
        prescriptionIDField = new JTextField();

        form.add(new JLabel("Patient ID:"));
        form.add(patientIDField);
        form.add(new JLabel("Doctor ID:"));
        form.add(doctorIDField);
        form.add(new JLabel("Date (yyyy-MM-dd):"));
        form.add(dateField);
        form.add(new JLabel("Time (HH:mm):"));
        form.add(timeField);
        form.add(new JLabel("Test Name:"));
        form.add(nameField);
        form.add(new JLabel("Lab ID:"));
        form.add(labIDField);
        form.add(new JLabel("Result:"));
        form.add(resultField);
        form.add(new JLabel("Prescription ID:"));
        form.add(prescriptionIDField);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Test");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(addBtn);
        buttons.add(refreshBtn);

        add(form, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        loadTests();

        addBtn.addActionListener(e -> addTest());
        refreshBtn.addActionListener(e -> loadTests());
    }

    private void loadTests() {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<TestResult> tests = dao.getAllTests();

        for (TestResult t : tests) {
            model.addRow(new Object[]{
                    t.getPatientID(), t.getDoctorID(),
                    sdf.format(t.getDate()), t.getTime(),
                    t.getName(), t.getLabID(),
                    t.getResult(), t.getPrescriptionID()
            });
        }
    }

    private void addTest() {
        try {
            TestResult t = new TestResult();
            t.setPatientID(Integer.parseInt(patientIDField.getText().trim()));
            t.setDoctorID(Integer.parseInt(doctorIDField.getText().trim()));
            t.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText().trim()));
            t.setTime(timeField.getText().trim());
            t.setName(nameField.getText().trim());
            t.setLabID(Integer.parseInt(labIDField.getText().trim()));
            t.setResult(resultField.getText().trim());
            t.setPrescriptionID(Integer.parseInt(prescriptionIDField.getText().trim()));

            if (dao.addTest(t)) {
                JOptionPane.showMessageDialog(this, "Test added.");
                loadTests();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add test.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }
}
