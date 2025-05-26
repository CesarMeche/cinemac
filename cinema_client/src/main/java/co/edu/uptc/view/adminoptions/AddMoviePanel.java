package co.edu.uptc.view.adminoptions;

import javax.swing.*;

import co.edu.uptc.view.AdminPanel;

import java.awt.*;

public class AddMoviePanel extends JPanel {

    public AddMoviePanel(AdminPanel adminPanel) {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField titleField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField durationField = new JTextField();

        form.add(new JLabel("Título:"));
        form.add(titleField);
        form.add(new JLabel("Género:"));
        form.add(genreField);
        form.add(new JLabel("Duración (minutos):"));
        form.add(durationField);
        

        JButton enviar = new JButton("Agregar");
        enviar.addActionListener(e -> {
            // Validación y envío aquí
            JOptionPane.showMessageDialog(this, "Película agregada: " + titleField.getText());
        });

        JButton volver = new JButton("Volver");
        volver.addActionListener(e -> adminPanel.showPanel(AdminPanel.BUTTONS));

        JPanel buttons = new JPanel();
        buttons.add(enviar);
        buttons.add(volver);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}
