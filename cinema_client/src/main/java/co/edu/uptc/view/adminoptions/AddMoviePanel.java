package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.model.pojos.Movie;
import co.edu.uptc.view.panel.AdminPanel;

import java.awt.*;
import java.awt.event.ActionEvent;

public class AddMoviePanel extends JPanel {
    private AdminPanel admin;
    private JTextField titleField;
    private JTextField calificationField;
    private JTextArea movieSynopsisArea;
    private JTextField rateField;
    private JTextField durationField;
    private JButton submitButton;
    private JButton backButton;

    public AddMoviePanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f2f2f2"));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Agregar Película", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.decode("#1c5052"));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.decode("#f2f2f2"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas y campos
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Título:"), gbc);
        gbc.gridx = 1;
        titleField = createTextField();
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(createLabel("Calificación:"), gbc);
        gbc.gridx = 1;
        calificationField = createTextField();
        formPanel.add(calificationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(createLabel("Sinopsis:"), gbc);
        gbc.gridx = 1;
        movieSynopsisArea = new JTextArea(4, 20);
        movieSynopsisArea.setLineWrap(true);
        movieSynopsisArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(movieSynopsisArea);
        formPanel.add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(createLabel("Tarifa:"), gbc);
        gbc.gridx = 1;
        rateField = createTextField();
        formPanel.add(rateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(createLabel("Duración (minutos):"), gbc);
        gbc.gridx = 1;
        durationField = createTextField();
        formPanel.add(durationField, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonsPanel.setBackground(Color.decode("#f2f2f2"));

        backButton = createButton("Volver");
        submitButton = createButton("Crear Película");

        buttonsPanel.add(backButton);
        buttonsPanel.add(submitButton);

        formPanel.add(buttonsPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Listeners
        submitButton.addActionListener(this::handleSubmit);
        backButton.addActionListener(e -> admin.backToMenu());
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.decode("#1c5052"));
        return label;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField(20);
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

    private void handleSubmit(ActionEvent e) {
        String title = titleField.getText().trim();
        String calification = calificationField.getText().trim();
        String synopsis = movieSynopsisArea.getText().trim();
        String rate = rateField.getText().trim();
        String duration = durationField.getText().trim();

        try {
            admin.getMainFrame().getController().sendMsg(AdminOptions.ADD_MOVIE.name(), Msg.DONE.name(),
                    new Movie(title, calification, synopsis, rate, duration));

            boolean success = (boolean) admin.getMainFrame().getController().reciveMsg().getData();

            if (success) {
                JOptionPane.showMessageDialog(this, "¡Película agregada exitosamente!", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                cleanTextFields();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo agregar la película.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al agregar la película.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cleanTextFields() {
        titleField.setText("");
        calificationField.setText("");
        movieSynopsisArea.setText("");
        rateField.setText("");
        durationField.setText("");
    }
}
