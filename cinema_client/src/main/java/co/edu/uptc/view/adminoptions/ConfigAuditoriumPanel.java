package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

public class ConfigAuditoriumPanel extends JPanel {
    private AdminPanel admin;
    private JTextField dataField;
    private JTextField auditoriumNameField;
    private JTextField optionField;
    private JButton submitButton;
    private JButton backButton;

    public ConfigAuditoriumPanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Data:"));
        dataField = new JTextField();
        formPanel.add(dataField);

        formPanel.add(new JLabel("Auditorium Name:"));
        auditoriumNameField = new JTextField();
        formPanel.add(auditoriumNameField);

        formPanel.add(new JLabel("Option:"));
        optionField = new JTextField();
        formPanel.add(optionField);

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

    public void cleanTextFields() {
        dataField.setText("");
        auditoriumNameField.setText("");
        optionField.setText("");
    }

    public void sendAuditoriumConfigInfo() {
        String data = dataField.getText();
        String auditoriumName = auditoriumNameField.getText();
        String option = optionField.getText();

        try {
            admin.getMainFrame().getController().sendMsg(AdminOptions.CONFIGURATE_AUDITORIUM.name(), Msg.DONE.name(),
                    new Object[] { data, auditoriumName, option });

            if ((boolean) admin.getMainFrame().getController().reciveMsg().getData()) {
                JOptionPane.showMessageDialog(ConfigAuditoriumPanel.this, "Auditorium configuration updated successfully!");
            } else {
                JOptionPane.showMessageDialog(ConfigAuditoriumPanel.this, "Auditorium configuration update failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ConfigAuditoriumPanel.this, "Error updating the auditorium configuration: " + e.getMessage());
        } finally {
            System.out.println("Finalizando operación de configuración de auditorio.");
        }
    }
}
