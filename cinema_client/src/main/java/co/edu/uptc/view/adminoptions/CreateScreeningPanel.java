package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Properties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.jdatepicker.impl.*;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.network.JsonResponse;
import co.edu.uptc.view.panel.AdminPanel;

public class CreateScreeningPanel extends JPanel {
    private AdminPanel admin;
    private JTextField auditoriumNameField;
    private JDatePanelImpl datePicker;
    private JComboBox<String> hourComboBox;
    private JComboBox<String> minuteComboBox;
    private JTextField movieNameField;
    private JButton submitButton;
    private JButton backButton;

    public CreateScreeningPanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("Auditorium Name:"));
        auditoriumNameField = new JTextField("papaya");
        formPanel.add(auditoriumNameField);

        formPanel.add(new JLabel("Date:"));
        datePicker = createDatePicker();
        formPanel.add(datePicker);

        formPanel.add(new JLabel("Time (HH:mm):"));
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hourComboBox = createHourComboBox();
        minuteComboBox = createMinuteComboBox();
        timePanel.add(hourComboBox);
        timePanel.add(new JLabel(":"));
        timePanel.add(minuteComboBox);
        formPanel.add(timePanel);

        formPanel.add(new JLabel("Movie Name:"));
        movieNameField = new JTextField("mikus");
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
                // cleanTextFields();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.backToMenu();
            }
        });
    }

    private JDatePanelImpl createDatePicker() {
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return datePanel;
    }

    private JComboBox<String> createHourComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        for (int i = 0; i < 24; i++) {
            comboBox.addItem(String.format("%02d", i));
        }
        return comboBox;
    }

    private JComboBox<String> createMinuteComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        for (int i = 0; i < 60; i += 5) { // Saltos de 5 minutos
            comboBox.addItem(String.format("%02d", i));
        }
        return comboBox;
    }

    public void cleanTextFields() {
        auditoriumNameField.setText("");
        movieNameField.setText("");
        // Limpieza de la fecha y hora
        datePicker.getModel().setValue(null);
        hourComboBox.setSelectedIndex(0);
        minuteComboBox.setSelectedIndex(0);
    }

    public void sendScreeningInfo() {
        String auditoriumName = auditoriumNameField.getText();
        String movieName = movieNameField.getText();

        try {
            // Obtener fecha
            Date selectedDate = (Date) datePicker.getModel().getValue();
            if (selectedDate == null) {
                throw new IllegalArgumentException("¡Por favor selecciona una fecha!");
            }
            LocalDate date = selectedDate.toLocalDate();

            // Obtener hora
            int hour = Integer.parseInt((String) hourComboBox.getSelectedItem());
            int minute = Integer.parseInt((String) minuteComboBox.getSelectedItem());
            LocalTime time = LocalTime.of(hour, minute);

            // Combinar fecha y hora
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            // Formatear la fecha y hora como String para enviar al servidor
            String dateTimeStr = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Enviar la información al controlador
            admin.getMainFrame().getController().sendMsg(
                    AdminOptions.CREATE_SCREENING.name(),
                    Msg.DONE.name(),
                    new String[]{ auditoriumName, dateTimeStr, movieName });

            JsonResponse<Boolean> response = admin.getMainFrame().getController().reciveMsg();
            if (response.getData()) {
                JOptionPane.showMessageDialog(CreateScreeningPanel.this, "¡Función creada exitosamente!");
            } else {
                JOptionPane.showMessageDialog(CreateScreeningPanel.this, "Error al crear la función.");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(CreateScreeningPanel.this, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(CreateScreeningPanel.this, "Error al crear la función: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
