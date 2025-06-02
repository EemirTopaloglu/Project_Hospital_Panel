package ui.panels;

import dao.LaboratoryDAO;
import model.Laboratory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LaboratoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private LaboratoryDAO dao = new LaboratoryDAO();

    private JTextField labIDField, nameField, deptField, phoneField, docIDField, staffIDField;

    public LaboratoryPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
                "Lab ID", "Name", "Department ID", "Phone", "Doctor ID", "Staff ID"
        }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel form = new JPanel(new GridLayout(3, 4, 5, 5));
        labIDField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        phoneField = new JTextField();
        docIDField = new JTextField();
        staffIDField = new JTextField();

        form.add(new JLabel("Lab ID:"));
        form.add(labIDField);
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Department ID:"));
        form.add(deptField);
        form.add(new JLabel("Phone:"));
        form.add(phoneField);
        form.add(new JLabel("Doctor ID:"));
        form.add(docIDField);
        form.add(new JLabel("Staff ID:"));
        form.add(staffIDField);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Lab");
        JButton delBtn = new JButton("Delete Selected");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(addBtn);
        buttons.add(delBtn);
        buttons.add(refreshBtn);

        add(form, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        loadLabs();

        addBtn.addActionListener(e -> addLab());
        delBtn.addActionListener(e -> deleteLab());
        refreshBtn.addActionListener(e -> loadLabs());
    }

    private void loadLabs() {
        model.setRowCount(0);
        List<Laboratory> list = dao.getAllLabs();

        for (Laboratory l : list) {
            model.addRow(new Object[]{
                    l.getLabID(), l.getName(), l.getConnectedDept(),
                    l.getPhone(), l.getRespDoctorID(), l.getRespSID()
            });
        }
    }

    private void addLab() {
        try {
            Laboratory l = new Laboratory();
            l.setLabID(Integer.parseInt(labIDField.getText().trim()));
            l.setName(nameField.getText().trim());
            l.setConnectedDept(Integer.parseInt(deptField.getText().trim()));
            l.setPhone(phoneField.getText().trim());
            l.setRespDoctorID(Integer.parseInt(docIDField.getText().trim()));
            l.setRespSID(Integer.parseInt(staffIDField.getText().trim()));

            if (dao.addLab(l)) {
                JOptionPane.showMessageDialog(this, "Lab added.");
                loadLabs();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add lab.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }

    private void deleteLab() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a lab to delete.");
            return;
        }

        int labID = (int) model.getValueAt(row, 0);
        if (dao.deleteLab(labID)) {
            JOptionPane.showMessageDialog(this, "Lab deleted.");
            loadLabs();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete.");
        }
    }
}
