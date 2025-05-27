package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.EditAudithorium;
import co.edu.uptc.enums.EditMovie;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

public class ConfigAuditoriumPanel extends JPanel {
    private AdminPanel admin;
    private JTextField dataField;
    private String option;
    private JComboBox<String> optionComboBox;
    private JTextField auditoriumNameField;

    private JButton submitButton;
    private JButton backButton;

    public ConfigAuditoriumPanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Data:"));
        dataField = new JTextField("yuyu");
        formPanel.add(dataField);
        // TODOseleccionar los nombres de los auditorios
        formPanel.add(new JLabel("Auditorium Name:"));
        auditoriumNameField = new JTextField("papaya");
        formPanel.add(auditoriumNameField);
        option = EditAudithorium.NAME.name();
        String[] options = { "Nombre",
                "Tamaño"
        };
        formPanel.add(new JLabel("Option:"));
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
            switch ((String) optionComboBox.getSelectedItem()) {
                // TODO variable final
                case "Nombre":
                    option = EditAudithorium.NAME.name();
                    break;
                case "Tamaño":
                    option = EditAudithorium.SIZE.name();
                    break;
            }
        });
    }

    public void cleanTextFields() {
        dataField.setText("");
        auditoriumNameField.setText("");
        option = EditAudithorium.NAME.name();
    }

    public void sendAuditoriumConfigInfo() {
        String data = dataField.getText();
        String auditoriumName = auditoriumNameField.getText();

        try {
            admin.getMainFrame().getController().sendMsg(AdminOptions.CONFIGURATE_AUDITORIUM.name(), Msg.DONE.name(),
                    new Object[] { data, auditoriumName, option });

            if ((boolean) admin.getMainFrame().getController().reciveMsg().getData()) {
                JOptionPane.showMessageDialog(ConfigAuditoriumPanel.this,
                        "Auditorium configuration updated successfully!");
            } else {
                JOptionPane.showMessageDialog(ConfigAuditoriumPanel.this, "Auditorium configuration update failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ConfigAuditoriumPanel.this,
                    "Error updating the auditorium configuration: " + e.getMessage());
        } 
    }
}
