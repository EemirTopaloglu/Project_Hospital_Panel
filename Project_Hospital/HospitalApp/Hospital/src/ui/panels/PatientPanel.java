package ui.panels;

import dao.PatientDAO;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/*  Responsibilities:
	Display patients in a JTable
	Form inputs for adding a patient
	Buttons: Add Patient, Delete Patient, Refresh
	Search/filter functionality by name/email/insurance

 	Uses PatientDAO methods:
	Calls getAllPatients() to populate the table
	Calls addPatient() when form is submitted
	Calls deletePatient() to remove selected rows*/
public class PatientPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private PatientDAO dao = new PatientDAO();
    private List<Patient> fullPatientList;

    private JTextField idField, nameField, emailField, phoneField, addressField, dobField, genderField, insuranceField;
    private JPasswordField passwordField; // Changed to JPasswordField for security
    private JTextField searchField;
    private JComboBox<String> filterCombo;
    private JLabel label;
    private JLabel label_1;

    public PatientPanel() {
        setLayout(new BorderLayout());

        // === Search ===
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        filterCombo = new JComboBox<>(new String[]{"Name", "Email", "Insurance"});
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
                "ID", "Name", "Email", "Phone", "Address", "DOB", "Gender", "Insurance"
        }, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // === Form + Buttons ===
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Updated form layout to accommodate password field (3x6 -> 4x5)
        JPanel form = new JPanel(new GridLayout(4, 5, 5, 5));
        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField(); // Added password field
        phoneField = new JTextField();
        addressField = new JTextField();
        dobField = new JTextField();
        genderField = new JTextField();
        insuranceField = new JTextField();

        form.add(new JLabel("ID:")); form.add(idField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Email:")); form.add(emailField);
        form.add(new JLabel("Password:")); form.add(passwordField); // Added password field
        form.add(new JLabel("Phone:")); form.add(phoneField);
        form.add(new JLabel("Address:")); form.add(addressField);
        form.add(new JLabel("DOB (yyyy-MM-dd):")); form.add(dobField);
        form.add(new JLabel("Gender:")); form.add(genderField);
        form.add(new JLabel("Insurance:")); form.add(insuranceField);
        JLabel label_2 = new JLabel();
        label_2.setEnabled(false);
        form.add(label_2); // Empty label for spacing

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Patient");
        JButton delBtn = new JButton("Delete Patient");
        JButton refreshBtn = new JButton("Refresh");
        JButton clearBtn = new JButton("Clear Form"); // Added clear button
        buttons.add(addBtn);
        buttons.add(delBtn);
        buttons.add(refreshBtn);
        buttons.add(clearBtn);

        bottomPanel.add(form, BorderLayout.CENTER);
        
        label = new JLabel();
        label.setEnabled(false);
        form.add(label);
        
        label_1 = new JLabel();
        label_1.setEnabled(false);
        form.add(label_1);
        bottomPanel.add(buttons, BorderLayout.SOUTH);

        // === Layout Assembly ===
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // === Events ===
        loadPatients();
        addBtn.addActionListener(e -> addPatient());
        delBtn.addActionListener(e -> deletePatient());
        refreshBtn.addActionListener(e -> loadPatients());
        clearBtn.addActionListener(e -> clearForm());
    }

    private void loadPatients() {
        model.setRowCount(0);
        fullPatientList = dao.getAllPatients();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        for (Patient p : fullPatientList) {
            model.addRow(new Object[]{
                    p.getUserID(), p.getFullName(), p.getEmail(), p.getPhone(),
                    p.getAddress(), df.format(p.getBirthDate()), p.getGender(), p.getInsurancePolId()
            });
        }
    }

    private void filter() {
        String term = searchField.getText().trim().toLowerCase();
        String filterBy = (String) filterCombo.getSelectedItem();

        List<Patient> filtered = fullPatientList.stream().filter(p -> {
            switch (filterBy) {
                case "Name": return p.getFullName().toLowerCase().contains(term);
                case "Email": return p.getEmail().toLowerCase().contains(term);
                case "Insurance": return p.getInsurancePolId().toLowerCase().contains(term);
                default: return true;
            }
        }).collect(Collectors.toList());

        updateTable(filtered);
    }

    private void updateTable(List<Patient> list) {
        model.setRowCount(0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        for (Patient p : list) {
            model.addRow(new Object[]{
                    p.getUserID(), p.getFullName(), p.getEmail(), p.getPhone(),
                    p.getAddress(), df.format(p.getBirthDate()), p.getGender(), p.getInsurancePolId()
            });
        }
    }

    private void addPatient() {
        try {
            String password = new String(passwordField.getPassword());

            Patient p = new Patient();
            p.setUserID(Integer.parseInt(idField.getText().trim()));
            p.setFullName(nameField.getText().trim());
            p.setEmail(emailField.getText().trim());
            p.setPassword(password); // Set the password
            p.setPhone(phoneField.getText().trim());
            p.setAddress(addressField.getText().trim());
            p.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(dobField.getText().trim()));
            p.setGender(genderField.getText().trim());
            p.setInsurancePolId(insuranceField.getText().trim());

            if (dao.addPatient(p)) {
                JOptionPane.showMessageDialog(this, "Patient added successfully.");
                clearForm();
                loadPatients();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient. Please check if ID already exists.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid ID number.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deletePatient() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this patient?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            int userID = (int) model.getValueAt(row, 0);
            if (dao.deletePatient(userID)) {
                JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
                loadPatients();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete patient.");
            }
        }
    }

    // New method to clear form fields
    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        phoneField.setText("");
        addressField.setText("");
        dobField.setText("");
        genderField.setText("");
        insuranceField.setText("");
    }
}