package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.AudithoriumSize;
import co.edu.uptc.enums.EditAudithorium;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

import java.awt.*;

public class ConfigAuditoriumPanel extends JPanel {
    private AdminPanel admin;
    private String option;
    private JComboBox<String> optionComboBox;
    private JTextField auditoriumNameField;

    private JButton submitButton;
    private JButton backButton;

    private JPanel cardPanel;
    private JTextField dataField;
    private JComboBox<String> sizeComboBox;

    public ConfigAuditoriumPanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f2f2f2"));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Configurar Auditorio", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.decode("#1c5052"));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.decode("#f2f2f2"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Nombre del Auditorio:"), gbc);

        gbc.gridx = 1;
        auditoriumNameField = createTextField("papaya");
        formPanel.add(auditoriumNameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createLabel("Dato o Tamaño:"), gbc);

        gbc.gridx = 1;
        cardPanel = new JPanel(new CardLayout());
        cardPanel.setBackground(Color.decode("#f2f2f2"));

        dataField = createTextField("yuyu");
        sizeComboBox = new JComboBox<>(new String[]{"Pequeño", "Mediano", "Grande"});
        sizeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cardPanel.add(dataField, "Nombre");
        cardPanel.add(sizeComboBox, "Tamaño");
        formPanel.add(cardPanel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createLabel("Opción:"), gbc);

        gbc.gridx = 1;
        String[] options = {"Nombre", "Tamaño"};
        optionComboBox = new JComboBox<>(options);
        optionComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(optionComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonsPanel.setBackground(Color.decode("#f2f2f2"));
        backButton = createButton("Volver");
        submitButton = createButton("Configurar Auditorio");
        buttonsPanel.add(backButton);
        buttonsPanel.add(submitButton);
        formPanel.add(buttonsPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        option = EditAudithorium.NAME.name();
        editComboBoxAction();

        submitButton.addActionListener(e -> {
            sendAuditoriumConfigInfo();
            cleanTextFields();
        });

        backButton.addActionListener(e -> admin.backToMenu());
    }

    private void editComboBoxAction() {
        optionComboBox.addActionListener(e -> {
            String selected = (String) optionComboBox.getSelectedItem();
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            switch (selected) {
                case "Nombre":
                    option = EditAudithorium.NAME.name();
                    cl.show(cardPanel, "Nombre");
                    break;
                case "Tamaño":
                    option = EditAudithorium.SIZE.name();
                    cl.show(cardPanel, "Tamaño");
                    break;
            }
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.decode("#1c5052"));
        return label;
    }

    private JTextField createTextField(String defaultText) {
        JTextField tf = new JTextField(defaultText);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return tf;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(Color.decode("#348e91"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return button;
    }

    public void cleanTextFields() {
        dataField.setText("");
        auditoriumNameField.setText("");
        sizeComboBox.setSelectedIndex(0);
        option = EditAudithorium.NAME.name();

        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "Nombre");
        optionComboBox.setSelectedIndex(0);
    }

    private String getSelectedSizeEnum() {
        String selectedSize = (String) sizeComboBox.getSelectedItem();

        switch (selectedSize) {
            case "Pequeño":
                return AudithoriumSize.SMALL.name();
            case "Mediano":
                return AudithoriumSize.MEDIUM.name();
            case "Grande":
                return AudithoriumSize.BIG.name();
            default:
                throw new IllegalArgumentException("Tamaño no reconocido: " + selectedSize);
        }
    }

    public void sendAuditoriumConfigInfo() {
        String data;

        if (option.equals(EditAudithorium.NAME.name())) {
            data = dataField.getText().trim();
            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingresa un nombre válido.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else if (option.equals(EditAudithorium.SIZE.name())) {
            data = getSelectedSizeEnum();
        } else {
            JOptionPane.showMessageDialog(this, "Opción inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String auditoriumName = auditoriumNameField.getText().trim();
        if (auditoriumName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa el nombre del auditorio.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            admin.getMainFrame().getController().sendMsg(
                    AdminOptions.CONFIGURATE_AUDITORIUM.name(),
                    Msg.DONE.name(),
                    new Object[]{data, auditoriumName, option});

            boolean success = (boolean) admin.getMainFrame().getController().reciveMsg().getData();

            if (success) {
                JOptionPane.showMessageDialog(this, "¡Configuración del auditorio actualizada correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "¡No se pudo actualizar la configuración del auditorio!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error actualizando la configuración: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
