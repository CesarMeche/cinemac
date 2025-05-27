package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

public class CreateScreeningPanel extends JPanel {
    private AdminPanel admin;
    private JTextField auditoriumNameField;
    private JTextField dateField;
    private JTextField movieNameField;
    private JButton submitButton;
    private JButton backButton;

    public CreateScreeningPanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Auditorium Name:"));
        auditoriumNameField = new JTextField();
        formPanel.add(auditoriumNameField);

        formPanel.add(new JLabel("Date (yyyy-MM-ddTHH:mm):"));
        dateField = new JTextField();
        formPanel.add(dateField);

        formPanel.add(new JLabel("Movie Name:"));
        movieNameField = new JTextField();
        formPanel.add(movieNameField);

        backButton = new JButton("Volver");
        formPanel.add(backButton);
        submitButton = new JButton("Crear Función");
        formPanel.add(submitButton);

        add(formPanel, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendScreeningInfo();
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
        auditoriumNameField.setText("");
        dateField.setText("");
        movieNameField.setText("");
    }

    public void sendScreeningInfo() {
        String auditoriumName = auditoriumNameField.getText();
        String dateStr = dateField.getText();
        String movieName = movieNameField.getText();

        try {
            LocalDateTime date = LocalDateTime.parse(dateStr);

            // Enviar la información al controlador
            admin.getMainFrame().getController().sendMsg(AdminOptions.CREATE_SCREENING.name(), Msg.DONE.name(),
                    new Object[] { auditoriumName, date, movieName });

            if ((boolean) admin.getMainFrame().getController().reciveMsg().getData()) {
                JOptionPane.showMessageDialog(CreateScreeningPanel.this, "Screening created successfully!");
            } else {
                JOptionPane.showMessageDialog(CreateScreeningPanel.this, "Screening creation failed!");
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(CreateScreeningPanel.this, "Invalid date format! Use yyyy-MM-ddTHH:mm");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(CreateScreeningPanel.this, "Error creating the screening: " + e.getMessage());
        } finally {
            System.out.println("Finalizando operación de crear función.");
        }
    }
}
