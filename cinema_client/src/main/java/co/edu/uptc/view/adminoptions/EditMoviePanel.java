package co.edu.uptc.view.adminoptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.view.panel.AdminPanel;

public class EditMoviePanel extends JPanel {
    private AdminPanel admin;
    private JTextField dataField;
    private JTextField atributeField;
    private JTextField movieNameField;
    private JButton submitButton;
    private JButton backButton;

    public EditMoviePanel(AdminPanel admin) {
        this.admin = admin;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Data:"));
        dataField = new JTextField();
        formPanel.add(dataField);

        formPanel.add(new JLabel("Attribute:"));
        atributeField = new JTextField();
        formPanel.add(atributeField);

        formPanel.add(new JLabel("Movie Name:"));
        movieNameField = new JTextField();
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
        atributeField.setText("");
        movieNameField.setText("");
    }

    public void sendEditMovieInfo() {
        String data = dataField.getText();
        String atribute = atributeField.getText();
        String movieName = movieNameField.getText();

        try {
            // Enviar la información al controlador
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
