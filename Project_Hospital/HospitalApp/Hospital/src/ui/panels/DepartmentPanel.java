package ui.panels;

import dao.DepartmentDAO;
import model.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private DepartmentDAO dao = new DepartmentDAO();

    private JTextField idField, nameField, specialtyField, medField, adminField;

    public DepartmentPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
                "ID", "Name", "Specialty", "Medical Manager ID", "Admin Manager ID"
        }, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel form = new JPanel(new GridLayout(3, 4, 5, 5));
        idField = new JTextField();
        nameField = new JTextField();
        specialtyField = new JTextField();
        medField = new JTextField();
        adminField = new JTextField();

        form.add(new JLabel("Dept ID:"));
        form.add(idField);
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Specialty:"));
        form.add(specialtyField);
        form.add(new JLabel("Med Manager ID:"));
        form.add(medField);
        form.add(new JLabel("Admin Manager ID:"));
        form.add(adminField);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Department");
        JButton delBtn = new JButton("Delete Department");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(addBtn);
        buttons.add(delBtn);
        buttons.add(refreshBtn);

        add(form, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        loadDepartments();

        addBtn.addActionListener(e -> addDepartment());
        delBtn.addActionListener(e -> deleteDepartment());
        refreshBtn.addActionListener(e -> loadDepartments());
    }

    private void loadDepartments() {
        model.setRowCount(0);
        List<Department> list = dao.getAllDepartments();
        for (Department d : list) {
            model.addRow(new Object[]{
                    d.getDepartmentID(), d.getName(), d.getSpecialty(),
                    d.getMedManagerID(), d.getAdManagerID()
            });
        }
    }

    private void addDepartment() {
        try {
            Department d = new Department();
            d.setDepartmentID(Integer.parseInt(idField.getText().trim()));
            d.setName(nameField.getText().trim());
            d.setSpecialty(specialtyField.getText().trim());
            d.setMedManagerID(Integer.parseInt(medField.getText().trim()));
            d.setAdManagerID(Integer.parseInt(adminField.getText().trim()));

            if (dao.addDepartment(d)) {
                JOptionPane.showMessageDialog(this, "Department added.");
                loadDepartments();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add department.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void deleteDepartment() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a department to delete.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this department?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteDepartment(id)) {
                JOptionPane.showMessageDialog(this, "Deleted.");
                loadDepartments();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete.");
            }
        }
    }
}
