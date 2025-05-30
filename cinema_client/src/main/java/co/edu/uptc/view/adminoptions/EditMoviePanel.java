package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.EditMovie;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

import java.awt.*;
import java.awt.event.ActionEvent;

public class EditMoviePanel extends JPanel {
    private AdminPanel admin;
    private JComboBox<String> comboBox;
    private String data;
    private JTextField attributeField;
    private JTextField movieNameField;
    private JButton submitButton;
    private JButton backButton;

    public EditMoviePanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#f2f2f2"));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Editar Película", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.decode("#1c5052"));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.decode("#f2f2f2"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y combo box para atributo a editar
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Dato a modificar:"), gbc);

        gbc.gridx = 1;
        String[] options = { "Título", "Clasificación", "Sinopsis de la película", "Tarifa", "Duración (minutos)" };
        comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        data = EditMovie.TITLE.name();
        comboBox.addActionListener(e -> {
            switch ((String) comboBox.getSelectedItem()) {
                case "Título" -> data = EditMovie.TITLE.name();
                case "Clasificación" -> data = EditMovie.CALIFICATION.name();
                case "Sinopsis de la película" -> data = EditMovie.MOVIE_SYNOPSIS.name();
                case "Tarifa" -> data = EditMovie.RATE.name();
                case "Duración (minutos)" -> data = EditMovie.DURATION_IN_MINUTES.name();
            }
        });
        formPanel.add(comboBox, gbc);

        // Campo nuevo valor
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(createLabel("Nuevo valor:"), gbc);

        gbc.gridx = 1;
        attributeField = createTextField();
        formPanel.add(attributeField, gbc);

        // Campo nombre de la película
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(createLabel("Nombre de la película:"), gbc);

        gbc.gridx = 1;
        movieNameField = createTextField();
        formPanel.add(movieNameField, gbc);

        // Panel de botones
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonsPanel.setBackground(Color.decode("#f2f2f2"));

        backButton = createButton("Volver");
        submitButton = createButton("Editar Película");

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
        String attribute = attributeField.getText().trim();
        String movieName = movieNameField.getText().trim();

        try {
            admin.getMainFrame().getController().sendMsg(AdminOptions.EDIT_MOVIE_DATA.name(), Msg.DONE.name(),
                    new String[] { data, attribute, movieName });

            boolean success = (boolean) admin.getMainFrame().getController().reciveMsg().getData();

            if (success) {
                JOptionPane.showMessageDialog(this, "¡Película editada exitosamente!", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                cleanTextFields();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo editar la película.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al editar la película: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cleanTextFields() {
        data = EditMovie.TITLE.name();
        attributeField.setText("");
        movieNameField.setText("");
        comboBox.setSelectedIndex(0);
    }
}
