package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.EditMovie;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

public class EditMoviePanel extends JPanel {
    private AdminPanel admin;
    private JComboBox<String> comboBox;
    private String data;
    private JTextField atributeField;
    private JTextField movieNameField;
    private JButton submitButton;
    private JButton backButton;

    public EditMoviePanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Data:"));
        data=EditMovie.TITLE.name();
        String[] options = { "Título",
                "Clasificación",
                "Sinopsis de la película",
                "Tarifa",
                "Duración (minutos)" };
        comboBox = new JComboBox<>(options);

        comboBox.addActionListener(e -> {
            String seleccion = (String) comboBox.getSelectedItem();
            System.out.println("Seleccionaste: " + seleccion);
        });
        editComboBoxAction();
        formPanel.add(comboBox);

        formPanel.add(new JLabel("Attribute:"));
        atributeField = new JTextField("1234");
        formPanel.add(atributeField);

        formPanel.add(new JLabel("Movie Name:"));
        movieNameField = new JTextField("mikus");
        formPanel.add(movieNameField);

        backButton = new JButton("Volver");
        formPanel.add(backButton);
        submitButton = new JButton("Editar Película");
        formPanel.add(submitButton);

        add(formPanel, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendEditMovieInfo();
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

    private void editComboBoxAction() {
        comboBox.addActionListener(e -> {
            switch ((String) comboBox.getSelectedItem()) {
                case "Título":
                    data = EditMovie.TITLE.name();
                    break;
                case "Clasificación":
                    data = EditMovie.CALIFICATION.name();
                    break;
                case "Sinopsis de la película":
                    data = EditMovie.MOVIE_SYNOPSIS.name();
                    break;
                case "Tarifa":
                    data = EditMovie.RATE.name();
                    break;
                case "Duración (minutos)":
                    data = EditMovie.DURATION_IN_MINUTES.name();
                    break;
            }
        });
    }

    public void cleanTextFields() {
        data=EditMovie.TITLE.name();
        atributeField.setText("");
        movieNameField.setText("");
    }

    public void sendEditMovieInfo() {

        String atribute = atributeField.getText();
        String movieName = movieNameField.getText();

        try {

            admin.getMainFrame().getController().sendMsg(AdminOptions.EDIT_MOVIE_DATA.name(), Msg.DONE.name(),
                    new String[] { data, atribute, movieName });

            if ((boolean) admin.getMainFrame().getController().reciveMsg().getData()) {
                JOptionPane.showMessageDialog(EditMoviePanel.this, "Movie edited successfully!");
            } else {
                JOptionPane.showMessageDialog(EditMoviePanel.this, "Movie doesn't edited successfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(EditMoviePanel.this, "Error editing the movie: " + e.getMessage());
        } finally {
            System.out.println("Finalizando operación de editar película.");
        }
    }
}
