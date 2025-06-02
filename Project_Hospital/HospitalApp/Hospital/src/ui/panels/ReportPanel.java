package ui.panels;

import dao.ReportDAO;
import model.Report;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ReportDAO dao = new ReportDAO();

    private JTextField reportIDField, diagnosisField, billingField;

    public ReportPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
                "Report ID", "Diagnosis", "Billing Amount"
        }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel form = new JPanel(new GridLayout(2, 4, 5, 5));
        reportIDField = new JTextField();
        diagnosisField = new JTextField();
        billingField = new JTextField();

        form.add(new JLabel("Report ID:"));
        form.add(reportIDField);
        form.add(new JLabel("Diagnosis:"));
        form.add(diagnosisField);
        form.add(new JLabel("Billing:"));
        form.add(billingField);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Report");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(addBtn);
        buttons.add(refreshBtn);

        add(form, BorderLayout.NORTH);
        
        JLabel label = new JLabel("");
        label.setEnabled(false);
        form.add(label);
        add(scrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        loadReports();

        addBtn.addActionListener(e -> addReport());
        refreshBtn.addActionListener(e -> loadReports());
    }

    private void loadReports() {
        model.setRowCount(0);
        List<Report> reports = dao.getAllReports();

        for (Report r : reports) {
            model.addRow(new Object[]{
                    r.getReportID(), r.getDiagnosis(), r.getBilling()
            });
        }
    }

    private void addReport() {
        try {
            Report r = new Report();
            r.setReportID(Integer.parseInt(reportIDField.getText().trim()));
            r.setDiagnosis(diagnosisField.getText().trim());
            r.setBilling(Double.parseDouble(billingField.getText().trim()));

            if (dao.addReport(r)) {
                JOptionPane.showMessageDialog(this, "Report added.");
                loadReports();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add report.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }
}
