package ui.panels;

import dao.VisitDAO;
import model.Visit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VisitPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private VisitDAO dao = new VisitDAO();

    private JTextField patientIDField, doctorIDField, dateField, timeField, statusField, reportIDField;

    public VisitPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
                "PatientID", "DoctorID", "Date", "Time", "Status", "ReportID"
        }, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel form = new JPanel(new GridLayout(2, 6, 5, 5));
        patientIDField = new JTextField();
        doctorIDField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();
        statusField = new JTextField();
        reportIDField = new JTextField();

        form.add(new JLabel("Patient ID:"));
        form.add(patientIDField);
        form.add(new JLabel("Doctor ID:"));
        form.add(doctorIDField);
        form.add(new JLabel("Date (yyyy-MM-dd):"));
        form.add(dateField);
        form.add(new JLabel("Time (HH:mm):"));
        form.add(timeField);
        form.add(new JLabel("Status:"));
        form.add(statusField);
        form.add(new JLabel("Report ID (optional):"));
        form.add(reportIDField);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Visit");
        JButton delBtn = new JButton("Delete Visit");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(addBtn);
        buttons.add(delBtn);
        buttons.add(refreshBtn);

        add(form, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        loadVisits();

        addBtn.addActionListener(e -> addVisit());
        delBtn.addActionListener(e -> deleteVisit());
        refreshBtn.addActionListener(e -> loadVisits());
    }

    private void loadVisits() {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Visit> visits = dao.getAllVisits();

        for (Visit v : visits) {
            model.addRow(new Object[]{
                    v.getPatientID(), v.getDoctorID(),
                    sdf.format(v.getDate()), v.getTime(),
                    v.getStatus(),
                    (v.getReportID() != null ? v.getReportID() : "")
            });
        }
    }

    private void addVisit() {
        try {
            Visit v = new Visit();
            v.setPatientID(Integer.parseInt(patientIDField.getText().trim()));
            v.setDoctorID(Integer.parseInt(doctorIDField.getText().trim()));
            v.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText().trim()));
            v.setTime(timeField.getText().trim());
            v.setStatus(statusField.getText().trim());

            String reportStr = reportIDField.getText().trim();
            if (!reportStr.isEmpty())
                v.setReportID(Integer.parseInt(reportStr));

            if (dao.addVisit(v)) {
                JOptionPane.showMessageDialog(this, "Visit added.");
                loadVisits();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add visit.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }

    private void deleteVisit() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a visit to delete.");
            return;
        }

        try {
            Visit v = new Visit();
            v.setPatientID((int) model.getValueAt(row, 0));
            v.setDoctorID((int) model.getValueAt(row, 1));
            v.setDate(new SimpleDateFormat("yyyy-MM-dd").parse((String) model.getValueAt(row, 2)));
            v.setTime((String) model.getValueAt(row, 3));

            if (dao.deleteVisit(v)) {
                JOptionPane.showMessageDialog(this, "Visit deleted.");
                loadVisits();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete visit.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
