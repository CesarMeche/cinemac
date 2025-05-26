package co.edu.uptc.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import co.edu.uptc.controller.Controller;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class MainFrame extends JFrame {
    private LoginFrame loginPanel;
    private AdminPanel adminPanel;
    private Controller controller;
    public MainFrame(Controller controller) {
        this.controller=controller;
        loginPanel = new LoginFrame(this); // Le pasamos referencia al frame
        init();
    }

    public void init() {
        setTitle("Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(loginPanel, BorderLayout.CENTER); // Mostrar login por defecto
        setVisible(true);
    }

    public void showAdminPanel() {
        if (loginPanel != null) {
            remove(loginPanel);
        }
        adminPanel = new AdminPanel(this);
        add(adminPanel, BorderLayout.CENTER);
        revalidate(); // Refresca el layout
        repaint();    // Vuelve a pintar la ventana
    }
}
