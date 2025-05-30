package co.edu.uptc.view;

import javax.swing.*;

import co.edu.uptc.enums.Msg;

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
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.decode("#f2f2f2")); // Fondo claro

        // Panel para etiquetas y campos
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.decode("#f2f2f2")); // mismo fondo
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        userLabel.setForeground(Color.decode("#1c5052")); // color texto oscuro
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(userLabel, gbc);

        usuarioField = new JTextField("admin", 15);
        usuarioField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usuarioField.setForeground(Color.decode("#1c5052"));
        usuarioField.setBackground(Color.decode("#f2f2f2"));
        usuarioField.setBorder(BorderFactory.createLineBorder(Color.decode("#348e91"), 2));
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(usuarioField, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // Panel para el botón
        entrarButton = new JButton("Entrar");
        entrarButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        entrarButton.setBackground(Color.decode("#348e91")); // botón color medio
        entrarButton.setForeground(Color.WHITE); // texto blanco
        entrarButton.setFocusPainted(false);
        entrarButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        initButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#f2f2f2"));
        buttonPanel.add(entrarButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initButton() {
        entrarButton.addActionListener(e -> {
            String username = usuarioField.getText();
            boolean isAdmin = "admin".equalsIgnoreCase(username);

            String role = isAdmin ? "admin" : "user";
            Object data = isAdmin ? null : username;

            mainFrame.getController().sendMsg("", role, data);

            Msg response = Msg.valueOf(mainFrame.getController().reciveMsg().getMessage());
            if (response == Msg.DONE) {
                if (isAdmin) {
                    mainFrame.showAdminPanel();
                } else {
                    mainFrame.setTitle(username);
                    mainFrame.showUserPanel();
                }
            } else {
                mostrarErrorLogin();
            }
        });
    }

    private void mostrarErrorLogin() {
        JOptionPane.showMessageDialog(LoginFrame.this,
                "Usuario no reconocido o error en la autenticación",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
