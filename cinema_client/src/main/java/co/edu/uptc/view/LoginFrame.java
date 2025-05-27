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


        add(inputPanel, BorderLayout.CENTER);

        entrarButton = new JButton("Entrar");
        JPanel b= new JPanel();
        initButton();
        b.add(entrarButton);
        JButton userBtu = new JButton("user");
        userBtu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showUserPanel();
                mainFrame.getController().sendMsg(TOOL_TIP_TEXT_KEY, "user", null);
            }
            
        });
        b.add(userBtu);
        add(b, BorderLayout.SOUTH);
    }

    private void initButton() {
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showAdminPanel();
                mainFrame.getController().sendMsg(TOOL_TIP_TEXT_KEY, "admin", null);
            }
        });
    }
}
