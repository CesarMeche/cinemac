package co.edu.uptc.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import co.edu.uptc.controller.Controller;
import co.edu.uptc.view.panel.AdminPanel;
import co.edu.uptc.view.panel.UserPanel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class MainFrame extends JFrame {
    private LoginFrame loginPanel;
    private AdminPanel adminPanel;
    private UserPanel userPanel;
    private Controller controller;
    public MainFrame(Controller controller) {
        this.controller=controller;
        loginPanel = new LoginFrame(this); 
        init();
    }

    public void init() {
        setTitle("Cinema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(loginPanel, BorderLayout.CENTER); 
        setVisible(true);
    }

    public void showAdminPanel() {
        if (loginPanel != null) {
            remove(loginPanel);
        }
        adminPanel = new AdminPanel(this);
        add(adminPanel, BorderLayout.CENTER);
        setTitle("admin");
        revalidate(); 
        repaint();   
    }

    public void showUserPanel() {
        if (loginPanel != null) {
            remove(loginPanel); 
        }
        userPanel = new UserPanel(this);
        add(userPanel, BorderLayout.CENTER);
        revalidate(); 
        repaint(); 
    }
}
