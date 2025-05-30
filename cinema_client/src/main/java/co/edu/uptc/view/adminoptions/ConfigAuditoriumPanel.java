package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.AudithoriumSize;
import co.edu.uptc.enums.EditAudithorium;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

public class ConfigAuditoriumPanel extends JPanel {
    private AdminPanel admin;
    private String option;
    private JComboBox<String> optionComboBox;
    private JTextField auditoriumNameField;

    private JButton submitButton;
    private JButton backButton;

    // CardLayout
    private JPanel cardPanel;
    private JTextField dataField;
    private JComboBox<String> sizeComboBox;

    public ConfigAuditoriumPanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Nombre del Auditorio:"));
        auditoriumNameField = new JTextField("papaya");
        formPanel.add(auditoriumNameField);
        // Panel de "entrada de datos" (cambia según la opción)
        cardPanel = new JPanel(new CardLayout());

        // Campo para "Nombre"
        dataField = new JTextField("yuyu");
        cardPanel.add(dataField, "Nombre");

        // Campo para "Tamaño"
        sizeComboBox = new JComboBox<>(new String[] { "Pequeño", "Mediano", "Grande" });
        cardPanel.add(sizeComboBox, "Tamaño");

        formPanel.add(new JLabel("Dato o Tamaño:"));
        formPanel.add(cardPanel);

        option = EditAudithorium.NAME.name();
        String[] options = { "Nombre", "Tamaño" };
        formPanel.add(new JLabel("Opción:"));
        optionComboBox = new JComboBox<>(options);
        editComboBoxAction();
        formPanel.add(optionComboBox);

        backButton = new JButton("Volver");
        formPanel.add(backButton);
        submitButton = new JButton("Configurar Auditorio");
        formPanel.add(submitButton);

        add(formPanel, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAuditoriumConfigInfo();
                cleanTextFields();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.backToMenu();
            }
        });
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

    public void cleanTextFields() {
        dataField.setText("");
        auditoriumNameField.setText("");
        sizeComboBox.setSelectedIndex(0);
        option = EditAudithorium.NAME.name();

        // Restaurar la vista inicial
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
        String data=getSelectedSizeEnum();

        String auditoriumName = auditoriumNameField.getText();

        try {
            admin.getMainFrame().getController().sendMsg(AdminOptions.CONFIGURATE_AUDITORIUM.name(), Msg.DONE.name(),
                    new Object[] { data, auditoriumName, option });

            if ((boolean) admin.getMainFrame().getController().reciveMsg().getData()) {
                JOptionPane.showMessageDialog(ConfigAuditoriumPanel.this,
                        "¡Configuración del auditorio actualizada correctamente!");
            } else {
                JOptionPane.showMessageDialog(ConfigAuditoriumPanel.this,
                        "¡No se pudo actualizar la configuración del auditorio!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ConfigAuditoriumPanel.this,
                    "Error actualizando la configuración: " + e.getMessage());
        }
    }
}
