package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JPanel {

    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private JButton entrarButton;
    private MainFrame mainFrame;

    public LoginFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initPanel();
    }

    private void initPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel con campos de entrada
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Usuario:"));
        usuarioField = new JTextField("user");
        inputPanel.add(usuarioField);

        inputPanel.add(new JLabel("Contraseña:"));
        contrasenaField = new JPasswordField();
        inputPanel.add(contrasenaField);

        add(inputPanel, BorderLayout.CENTER);

        // Botón único de "Entrar"
        entrarButton = new JButton("Entrar");
        initButton();
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(entrarButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initButton() {
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usuarioField.getText();
                String password = new String(contrasenaField.getPassword());

                if ("user".equalsIgnoreCase(username)) {
                    mainFrame.showUserPanel();
                    mainFrame.getController().sendMsg("", "user", "user");
                } else if ("admin".equalsIgnoreCase(username)) {
                    mainFrame.showAdminPanel();
                    mainFrame.getController().sendMsg("", "admin", null);
                } else {
                    // Mensaje de error
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Usuario no reconocido",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
