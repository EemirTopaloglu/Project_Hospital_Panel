package ui.panels;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private UserDAO dao = new UserDAO();

    public UserPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
                "User ID", "Full Name", "Email", "isDoctor", "isPatient", "isStaff"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // sadece görüntüleme
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(new JLabel("All Registered Users", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadUsers());
        buttonPanel.add(refreshBtn);

        add(buttonPanel, BorderLayout.SOUTH);
        
        loadUsers();
    }

    private void loadUsers() {
        model.setRowCount(0);
        List<User> users = dao.getAllUsers();

        for (User u : users) {
            model.addRow(new Object[]{
                    u.getUserID(),
                    u.getFullName(),
                    u.getEmail(),
                    u.isDoctor(),
                    u.isPatient(),
                    u.isStaff()
            });
        }
    }
}