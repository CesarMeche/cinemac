package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

import org.jdatepicker.impl.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class DeleteScreeningPanel extends JPanel {
    private AdminPanel admin;
    private JTextField auditoriumNameField;
    private JDatePanelImpl datePicker;
    private JComboBox<String> hourComboBox;
    private JComboBox<String> minuteComboBox;
    private JTextField movieNameField;
    private JButton submitButton;
    private JButton backButton;

    public DeleteScreeningPanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f2f2f2"));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Eliminar Función", JLabel.CENTER);
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
        formPanel.add(createLabel("Fecha:"), gbc);

        gbc.gridx = 1;
        datePicker = createDatePicker();
        formPanel.add(datePicker, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createLabel("Hora (HH:mm):"), gbc);

        gbc.gridx = 1;
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        timePanel.setBackground(Color.decode("#f2f2f2"));
        hourComboBox = createHourComboBox();
        minuteComboBox = createMinuteComboBox();
        timePanel.add(hourComboBox);
        JLabel colon = new JLabel(":");
        colon.setFont(new Font("SansSerif", Font.BOLD, 16));
        colon.setForeground(Color.decode("#1c5052"));
        timePanel.add(colon);
        timePanel.add(minuteComboBox);
        formPanel.add(timePanel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createLabel("Nombre de la Película:"), gbc);

        gbc.gridx = 1;
        movieNameField = createTextField("mikus");
        formPanel.add(movieNameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonsPanel.setBackground(Color.decode("#f2f2f2"));
        backButton = createButton("Volver");
        submitButton = createButton("Eliminar Función");
        buttonsPanel.add(backButton);
        buttonsPanel.add(submitButton);

        formPanel.add(buttonsPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> sendDeleteScreeningInfo());
        backButton.addActionListener(e -> admin.backToMenu());
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

    private JDatePanelImpl createDatePicker() {
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        return new JDatePanelImpl(model, p);
    }

    private JComboBox<String> createHourComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        for (int i = 0; i < 24; i++) {
            comboBox.addItem(String.format("%02d", i));
        }
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return comboBox;
    }

    private JComboBox<String> createMinuteComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        for (int i = 0; i < 60; i += 5) {
            comboBox.addItem(String.format("%02d", i));
        }
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return comboBox;
    }

    public void cleanTextFields() {
        auditoriumNameField.setText("");
        movieNameField.setText("");
        datePicker.getModel().setValue(null);
        hourComboBox.setSelectedIndex(0);
        minuteComboBox.setSelectedIndex(0);
    }

    public void sendDeleteScreeningInfo() {
        String auditoriumName = auditoriumNameField.getText().trim();
        String movieName = movieNameField.getText().trim();

        try {
            java.sql.Date selectedDate = (java.sql.Date) datePicker.getModel().getValue();
            if (selectedDate == null) {
                throw new IllegalArgumentException("¡Por favor selecciona una fecha!");
            }
            LocalDate date = selectedDate.toLocalDate();

            int hour = Integer.parseInt((String) hourComboBox.getSelectedItem());
            int minute = Integer.parseInt((String) minuteComboBox.getSelectedItem());
            LocalTime time = LocalTime.of(hour, minute);

            LocalDateTime dateTime = LocalDateTime.of(date, time);
            String dateTimeStr = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            admin.getMainFrame().getController().sendMsg(
                    AdminOptions.DELETE_SCREENING.name(),
                    Msg.DONE.name(),
                    new String[]{ auditoriumName, dateTimeStr, movieName });

            boolean deleted = (boolean) admin.getMainFrame().getController().reciveMsg().getData();

            if (deleted) {
                JOptionPane.showMessageDialog(this, "¡Función eliminada exitosamente!", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                cleanTextFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la función.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la función: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
