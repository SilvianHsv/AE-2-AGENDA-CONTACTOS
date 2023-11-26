import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Agendaapp {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Agendaapp();
        });
    }

    public Agendaapp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Agenda App");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Teléfono");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnAdd = new JButton("Añadir contacto");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddContactDialog();
            }
        });

        JButton btnEdit = new JButton("Editar");
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    showEditContactDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Seleccione un contacto para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnDelete = new JButton("Eliminar");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Seleccione un contacto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void showAddContactDialog() {
        JDialog addDialog = new JDialog(frame, "Añadir contacto", true);
        addDialog.setSize(300, 150);
        addDialog.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();

        addDialog.add(new JLabel("Nombre:"));
        addDialog.add(nameField);
        addDialog.add(new JLabel("Teléfono:"));
        addDialog.add(phoneField);

        JButton btnOK = new JButton("OK");
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                if (!name.isEmpty() && !phone.isEmpty()) {
                    model.addRow(new Object[]{name, phone});
                    addDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(addDialog, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addDialog.add(btnOK);
        addDialog.setVisible(true);
    }

    private void showEditContactDialog(int rowIndex) {
        JDialog editDialog = new JDialog(frame, "Editar contacto", true);
        editDialog.setSize(300, 150);
        editDialog.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField((String) model.getValueAt(rowIndex, 0));
        JTextField phoneField = new JTextField((String) model.getValueAt(rowIndex, 1));

        editDialog.add(new JLabel("Nombre:"));
        editDialog.add(nameField);
        editDialog.add(new JLabel("Teléfono:"));
        editDialog.add(phoneField);

        JButton btnOK = new JButton("OK");
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                if (!name.isEmpty() && !phone.isEmpty()) {
                    model.setValueAt(name, rowIndex, 0);
                    model.setValueAt(phone, rowIndex, 1);
                    editDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editDialog.add(btnOK);
        editDialog.setVisible(true);
    }
}

