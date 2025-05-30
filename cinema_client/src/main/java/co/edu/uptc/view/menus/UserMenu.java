package co.edu.uptc.view.menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.view.panel.UserPanel;
import co.edu.uptc.view.userOptions.MovieShedule;
import co.edu.uptc.view.userOptions.ValidateBook;

public class UserMenu extends JPanel {
    private UserPanel userPanel;
    private JButton getMovieScheduleBtn;
    private JButton validateBookBtn;

    public UserMenu(UserPanel userPanel) {
        this.userPanel = userPanel;
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.decode("#f2f2f2"));

        JLabel title = new JLabel("Panel de Usuario", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.decode("#1c5052"));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 12, 12));
        buttonPanel.setBackground(Color.decode("#f2f2f2"));

        getMovieScheduleBtn = createStyledButton("Ver Horarios de Películas");
        validateBookBtn = createStyledButton("Validar Reserva");

        buttonPanel.add(getMovieScheduleBtn);
        buttonPanel.add(validateBookBtn);

        add(buttonPanel, BorderLayout.CENTER);

        initActions();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(Color.decode("#348e91"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        return button;
    }

    private void initActions() {
        getMovieScheduleBtn.addActionListener(e -> handleAction("GET_MOVIE_SCHEDULE"));
        validateBookBtn.addActionListener(e -> handleAction("VALIDATE_BOOK"));
    }

    private void handleAction(String action) {
        UserOptions userOptions = UserOptions.valueOf(action);
        switch (userOptions) {
            case GET_MOVIE_SCHEDULE:
                showMovieSchedule();
                break;
            case VALIDATE_BOOK:
                showValidateBook();
                break;
            default:
                System.out.println("Acción no reconocida: " + action);
        }
    }

    private void showMovieSchedule() {
        MovieShedule ms = (MovieShedule) userPanel.getPanels().get(UserOptions.GET_MOVIE_SCHEDULE.name());
        ms.init();
        userPanel.showPanel(UserOptions.GET_MOVIE_SCHEDULE.name());
    }

    private void showValidateBook() {
        ValidateBook vb = (ValidateBook) userPanel.getPanels().get(UserOptions.VALIDATE_BOOK.name());
        vb.init();
        userPanel.showPanel(UserOptions.VALIDATE_BOOK.name());
    }
}
