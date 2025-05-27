package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.Properties;

import org.jdatepicker.impl.*;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

public class ReportPanel extends JPanel {
    private final AdminPanel admin;

    private JDatePanelImpl firstDatePicker;
    private JDatePanelImpl secondDatePicker;
    private JButton submitButton;
    private JButton backButton;

    public ReportPanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());
        initComponents();
        initListeners();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        // First Date
        formPanel.add(new JLabel("First Date:"));
        firstDatePicker = createDatePicker();
        formPanel.add(firstDatePicker);

        // Second Date
        formPanel.add(new JLabel("Second Date:"));
        secondDatePicker = createDatePicker();
        formPanel.add(secondDatePicker);

        // Buttons
        backButton = new JButton("Volver");
        submitButton = new JButton("Generar Reporte");
        formPanel.add(backButton);
        formPanel.add(submitButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private void initListeners() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.backToMenu();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendReportDates();
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

    private String stringToLocalDate(JDatePanelImpl datePicker) {
        // Retrieve first date
        StringBuilder date = new StringBuilder();
        date.append(datePicker.getModel().getYear());
        if ((datePicker.getModel().getMonth() + 1) < 10) {
            date.append("-0" + (datePicker.getModel().getMonth() + 1));
        } else {
            date.append("-" + (datePicker.getModel().getMonth() + 1));
        }
        date.append("-" + datePicker.getModel().getDay() + "T00:00");
        return date.toString();
    }

    private void sendReportDates() {
        try {
            admin.getMainFrame().getController().sendMsg(
                    AdminOptions.GENERATE_REPORT.name(),
                    Msg.DONE.name(),
                    new String[] { stringToLocalDate(firstDatePicker), stringToLocalDate(secondDatePicker) });

            boolean success = (boolean) admin.getMainFrame().getController().reciveMsg().getData();
            if (success) {
                JOptionPane.showMessageDialog(this, "Report generated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Report generation failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            System.err.println(e.getMessage());
        } finally {
            System.out.println("Report generation operation finalized.");
        }
    }
}
