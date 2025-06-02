package ui.panels;

import dao.DoctorDAO;
import model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private DoctorDAO dao = new DoctorDAO();
    private List<Doctor> fullDoctorList;

    private JTextField idField, nameField, emailField, phoneField, addressField, specialtyField, deptField;
    private JPasswordField passwordField; // Added password field
    private JTextField searchField;
    private JComboBox<String> filterCombo;

    public DoctorPanel() {
        setLayout(new BorderLayout());

        // === Search ===
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        filterCombo = new JComboBox<>(new String[]{"Name", "Email", "Specialty"});
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(filterCombo);

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
        });

        // === Table ===
        model = new DefaultTableModel(new String[]{
                "ID", "Name", "Email", "Phone", "Address", "Specialty", "Department ID"
        }, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // === Form + Buttons ===
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Modified form layout to accommodate password field
        JPanel form = new JPanel(new GridLayout(4, 4, 5, 5)); // Changed from 3x5 to 4x4
        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField(); // Added password field
        phoneField = new JTextField();
        addressField = new JTextField();
        specialtyField = new JTextField();
        deptField = new JTextField();

        // Form layout
        form.add(new JLabel("ID:")); 
        form.add(idField);
        form.add(new JLabel("Name:")); 
        form.add(nameField);
        
        form.add(new JLabel("Email:")); 
        form.add(emailField);
        form.add(new JLabel("Password:")); 
        form.add(passwordField);
        
        form.add(new JLabel("Phone:")); 
        form.add(phoneField);
        form.add(new JLabel("Address:")); 
        form.add(addressField);
        
        form.add(new JLabel("Specialty:")); 
        form.add(specialtyField);
        form.add(new JLabel("Department ID:")); 
        form.add(deptField);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Doctor");
        JButton delBtn = new JButton("Delete Doctor");
        JButton refreshBtn = new JButton("Refresh");
        JButton clearBtn = new JButton("Clear Fields"); // Added clear button

        buttons.add(addBtn);
        buttons.add(delBtn);
        buttons.add(refreshBtn);
        buttons.add(clearBtn);

        bottomPanel.add(form, BorderLayout.CENTER);
        bottomPanel.add(buttons, BorderLayout.SOUTH);

        // === Layout Assembly ===
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // === Events ===
        loadDoctors();
        addBtn.addActionListener(e -> addDoctor());
        delBtn.addActionListener(e -> deleteDoctor());
        refreshBtn.addActionListener(e -> loadDoctors());
        clearBtn.addActionListener(e -> clearFields());
    }

    private void loadDoctors() {
        model.setRowCount(0);
        fullDoctorList = dao.getAllDoctors();
        for (Doctor d : fullDoctorList) {
            model.addRow(new Object[]{
                    d.getUserID(), d.getFullName(), d.getEmail(), d.getPhone(),
                    d.getAddress(), d.getSpecialty(), d.getWorkingIn()
            });
        }
    }

    private void filter() {
        String term = searchField.getText().trim().toLowerCase();
        String filterBy = (String) filterCombo.getSelectedItem();

        List<Doctor> filtered = fullDoctorList.stream().filter(d -> {
            switch (filterBy) {
                case "Name": return d.getFullName().toLowerCase().contains(term);
                case "Email": return d.getEmail().toLowerCase().contains(term);
                case "Specialty": return d.getSpecialty().toLowerCase().contains(term);
                default: return true;
            }
        }).collect(Collectors.toList());

        updateTable(filtered);
    }

    private void updateTable(List<Doctor> list) {
        model.setRowCount(0);
        for (Doctor d : list) {
            model.addRow(new Object[]{
                    d.getUserID(), d.getFullName(), d.getEmail(), d.getPhone(),
                    d.getAddress(), d.getSpecialty(), d.getWorkingIn()
            });
        }
    }

    private void addDoctor() {
        try {
            // Validate required fields
            if (idField.getText().trim().isEmpty() || 
                nameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                specialtyField.getText().trim().isEmpty() ||
                deptField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                return;
            }

            // Get password (use default if empty)
            String password = new String(passwordField.getPassword());
            if (password.trim().isEmpty()) {
                password = "defaultpass";
            }

            Doctor d = new Doctor();
            d.setUserID(Integer.parseInt(idField.getText().trim()));
            d.setFullName(nameField.getText().trim());
            d.setEmail(emailField.getText().trim());
            d.setPassword(password); // Set the password
            d.setPhone(phoneField.getText().trim());
            d.setAddress(addressField.getText().trim());
            d.setSpecialty(specialtyField.getText().trim());
            d.setWorkingIn(Integer.parseInt(deptField.getText().trim()));

            if (dao.addDoctor(d)) {
                JOptionPane.showMessageDialog(this, "Doctor added successfully!");
                loadDoctors();
                clearFields(); // Clear fields after successful addition
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add doctor. Check for duplicate ID or database connection.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID and Department ID must be valid numbers.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteDoctor() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete.");
            return;
        }

        int userID = (int) model.getValueAt(row, 0);
        String doctorName = (String) model.getValueAt(row, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete Dr. " + doctorName + "?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteDoctor(userID)) {
                JOptionPane.showMessageDialog(this, "Doctor deleted successfully.");
                loadDoctors();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete doctor. Check database connection.");
            }
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        phoneField.setText("");
        addressField.setText("");
        specialtyField.setText("");
        deptField.setText("");
    }
}