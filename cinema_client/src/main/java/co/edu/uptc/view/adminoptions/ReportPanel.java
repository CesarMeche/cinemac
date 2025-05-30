package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

import java.awt.*;
import java.time.LocalDate;
import java.util.Properties;

import org.jdatepicker.impl.*;

public class ReportPanel extends JPanel {
    private final AdminPanel admin;

    private JDatePanelImpl firstDatePicker;
    private JDatePanelImpl secondDatePicker;
    private JButton submitButton;
    private JButton backButton;

    public ReportPanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f2f2f2"));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Generar Reporte", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.decode("#1c5052"));
        add(titleLabel, BorderLayout.NORTH);

        initComponents();
        initListeners();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.decode("#f2f2f2"));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fecha Inicial
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Fecha Inicial:"), gbc);

        gbc.gridx = 1;
        firstDatePicker = createDatePicker();
        formPanel.add(firstDatePicker, gbc);

        // Fecha Final
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createLabel("Fecha Final:"), gbc);

        gbc.gridx = 1;
        secondDatePicker = createDatePicker();
        formPanel.add(secondDatePicker, gbc);

        // Panel de botones con FlowLayout centrado y separación
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonsPanel.setBackground(Color.decode("#f2f2f2"));

        backButton = createButton("Volver");
        submitButton = createButton("Generar Reporte");

        buttonsPanel.add(backButton);
        buttonsPanel.add(submitButton);

        formPanel.add(buttonsPanel, gbc);

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

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.decode("#1c5052"));
        return label;
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
}
