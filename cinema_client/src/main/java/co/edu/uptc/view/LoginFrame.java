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
        initPanel();;
    }

    public void initPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel con los campos de entrada
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Usuario:"));
        usuarioField = new JTextField();
        inputPanel.add(usuarioField);

        inputPanel.add(new JLabel("Contraseña:"));
        contrasenaField = new JPasswordField();
        inputPanel.add(contrasenaField);

        add(inputPanel, BorderLayout.CENTER);

        entrarButton = new JButton("Entrar");
        initButton();
        add(entrarButton, BorderLayout.SOUTH);
    }

    public void initButton() {
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showAdminPanel();
            }
        });
    }
}
