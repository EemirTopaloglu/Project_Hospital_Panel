package ui.panels;

import dao.AllergyDAO;
import model.Allergy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AllergyPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private AllergyDAO dao = new AllergyDAO();

    private JTextField userIDField, alIDField, allergyNameField;

    public AllergyPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
                "UserID", "Allergy ID", "Allergy Name"
        }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel form = new JPanel(new GridLayout(2, 3, 5, 5));
        userIDField = new JTextField();
        alIDField = new JTextField();
        allergyNameField = new JTextField();

        form.add(new JLabel("User ID:"));
        form.add(userIDField);
        form.add(new JLabel("Allergy ID:"));
        form.add(alIDField);
        form.add(new JLabel("Allergy Name:"));
        form.add(allergyNameField);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Allergy");
        JButton delBtn = new JButton("Delete Selected");
        JButton refreshBtn = new JButton("Refresh");

        buttons.add(addBtn);
        buttons.add(delBtn);
        buttons.add(refreshBtn);

        add(form, BorderLayout.NORTH);
        
        JLabel label = new JLabel("");
        label.setEnabled(false);
        form.add(label);
        add(scrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        loadAllergies();

        addBtn.addActionListener(e -> addAllergy());
        delBtn.addActionListener(e -> deleteAllergy());
        refreshBtn.addActionListener(e -> loadAllergies());
    }

    private void loadAllergies() {
        model.setRowCount(0);
        List<Allergy> list = dao.getAllergies();

        for (Allergy a : list) {
            model.addRow(new Object[]{a.getUserID(), a.getAlID(), a.getAllergyName()});
        }
    }

    private void addAllergy() {
        try {
            Allergy a = new Allergy();
            a.setUserID(Integer.parseInt(userIDField.getText().trim()));
            a.setAlID(Integer.parseInt(alIDField.getText().trim()));
            a.setAllergyName(allergyNameField.getText().trim());

            if (dao.addAllergy(a)) {
                JOptionPane.showMessageDialog(this, "Allergy added.");
                loadAllergies();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add allergy.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }

    private void deleteAllergy() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }

        int userID = (int) model.getValueAt(row, 0);
        int alID = (int) model.getValueAt(row, 1);

        if (dao.deleteAllergy(userID, alID)) {
            JOptionPane.showMessageDialog(this, "Deleted.");
            loadAllergies();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete.");
        }
    }
}
