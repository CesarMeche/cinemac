package co.edu.uptc.view.adminoptions;

import javax.swing.*;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.model.pojos.Movie;
import co.edu.uptc.view.panel.AdminPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        formPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Calification:"));
        calificationField = new JTextField();
        formPanel.add(calificationField);

        formPanel.add(new JLabel("Movie Synopsis:"));
        movieSynopsisArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(movieSynopsisArea);
        formPanel.add(scrollPane);

        formPanel.add(new JLabel("Rate:"));
        rateField = new JTextField();
        formPanel.add(rateField);

        formPanel.add(new JLabel("Duration (minutes):"));
        durationField = new JTextField();
        formPanel.add(durationField);

        backButton = new JButton("volver");
        formPanel.add(backButton);
        submitButton = new JButton("crear Pelicula");
        formPanel.add(submitButton);

        add(formPanel, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMovieInfo();
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
        titleField.setText("");
        calificationField.setText("");
        movieSynopsisArea.setText("");
        rateField.setText("");
        durationField.setText("");
    }

    public void sendMovieInfo() {
        String title = titleField.getText();
        String calification = calificationField.getText();
        String synopsis = movieSynopsisArea.getText();
        String rate = rateField.getText();
        String duration = durationField.getText();
        try {
            admin.getMainFrame().getController().sendMsg(AdminOptions.ADD_MOVIE.name(), Msg.DONE.name(),
                    new Movie(title, calification, synopsis, rate, duration));

            if ((boolean) admin.getMainFrame().getController().reciveMsg().getData()) {

                JOptionPane.showMessageDialog(AddMoviePanel.this, "Movie added successfully!");
            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(AddMoviePanel.this, "Movie doesn't added successfully!");
            // TODO: handle exception
        } 
        

    }

}
