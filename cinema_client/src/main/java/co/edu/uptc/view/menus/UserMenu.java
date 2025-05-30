package co.edu.uptc.view.menus;

import java.awt.BorderLayout;
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
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Panel de Usuario", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        getMovieScheduleBtn = new JButton("Ver Horarios de Películas");
        validateBookBtn = new JButton("Validar Reserva");

        buttonPanel.add(getMovieScheduleBtn);
        buttonPanel.add(validateBookBtn);

        add(buttonPanel, BorderLayout.CENTER);
        initActions();
    }

    private void initActions() {
        getMovieScheduleBtn.addActionListener(e -> handleAction("GET_MOVIE_SCHEDULE"));
        validateBookBtn.addActionListener(e -> handleAction("VALIDATE_BOOK"));
    }

    private void handleAction(String action) {
        UserOptions userOptions = UserOptions.valueOf(action);
        switch (userOptions) {
            case GET_MOVIE_SCHEDULE:
                getMovieSchedule();
                break;
            case VALIDATE_BOOK:
                validateBook();
                break;
            default:
                System.out.println("Acción no reconocida: " + action);
        }
    }

    private void getMovieSchedule() {
        MovieShedule ms = (MovieShedule) userPanel.getPanels().get(UserOptions.GET_MOVIE_SCHEDULE.name());
        ms.init();
        userPanel.showPanel(UserOptions.GET_MOVIE_SCHEDULE.name());
    }


    private void validateBook() {
        ValidateBook vb=(ValidateBook) userPanel.getPanels().get(UserOptions.VALIDATE_BOOK.name());
        vb.init();
        userPanel.showPanel(UserOptions.VALIDATE_BOOK.name());
    }


}
