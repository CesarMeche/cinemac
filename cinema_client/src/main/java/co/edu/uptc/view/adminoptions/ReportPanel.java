package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
        formPanel.add(new JLabel("Fecha Inicial:"));
        firstDatePicker = createDatePicker();
        formPanel.add(firstDatePicker);

        // Second Date
        formPanel.add(new JLabel("Fecha Final:"));
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
        backButton.addActionListener(e -> admin.backToMenu());

        submitButton.addActionListener(e -> sendReportDates());
    }

    private JDatePanelImpl createDatePicker() {
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        return new JDatePanelImpl(model, p);
    }

    private LocalDate getLocalDate(JDatePanelImpl datePicker) {
        SqlDateModel model = (SqlDateModel) datePicker.getModel();
        if (!model.isSelected()) {
            return null;
        }
        return LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
    }

    private void sendReportDates() {
    try {
        LocalDate firstDate = getLocalDate(firstDatePicker);
        LocalDate secondDate = getLocalDate(secondDatePicker);

        if (firstDate == null || secondDate == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (firstDate.isAfter(secondDate)) {
            JOptionPane.showMessageDialog(this, "La fecha inicial no puede ser posterior a la fecha final.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] dateRange = {
            firstDate.atStartOfDay().toString(),
            secondDate.atStartOfDay().toString()
        };

        admin.getMainFrame().getController().sendMsg(
                AdminOptions.GENERATE_REPORT.name(),
                Msg.DONE.name(),
                dateRange
        );

        Object data = admin.getMainFrame().getController().reciveMsg().getData();
        int reportCount = (int) data;

        if (reportCount > 0) {
            JOptionPane.showMessageDialog(this, "¡Reporte generado exitosamente! Cantidad: " + reportCount);
        } else {
            JOptionPane.showMessageDialog(this, "No se generaron reportes para el rango de fechas.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Ocurrió un error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

}
