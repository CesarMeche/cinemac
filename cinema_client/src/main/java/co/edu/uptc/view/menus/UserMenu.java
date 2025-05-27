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

public class UserMenu extends JPanel {
    private UserPanel userPanel;
    private JButton getMovieScheduleBtn;
    private JButton createBookBtn;
    private JButton checkBookBtn;
    private JButton validateBookBtn;
    private JButton cancelBookBtn;

    public UserMenu(UserPanel userPanel) {
        this.userPanel = userPanel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Panel de Usuario", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        getMovieScheduleBtn = new JButton("Ver Horarios de Películas");
        createBookBtn = new JButton("Reservar");
        checkBookBtn = new JButton("Consultar Reserva");
        validateBookBtn = new JButton("Validar Reserva");
        cancelBookBtn = new JButton("Cancelar Reserva");

        buttonPanel.add(getMovieScheduleBtn);
        buttonPanel.add(createBookBtn);
        buttonPanel.add(checkBookBtn);
        buttonPanel.add(validateBookBtn);
        buttonPanel.add(cancelBookBtn);

        add(buttonPanel, BorderLayout.CENTER);
        initActions();
    }

    private void initActions() {
        getMovieScheduleBtn.addActionListener(e -> handleAction("GET_MOVIE_SCHEDULE"));
        createBookBtn.addActionListener(e -> handleAction("CREATE_BOOK"));
        checkBookBtn.addActionListener(e -> handleAction("CHECK_BOOK"));
        validateBookBtn.addActionListener(e -> handleAction("VALIDATE_BOOK"));
        cancelBookBtn.addActionListener(e -> handleAction("CANCEL_BOOK"));
    }

    private void handleAction(String action) {
        UserOptions userOptions = UserOptions.valueOf(action);
        switch (userOptions) {
            case GET_MOVIE_SCHEDULE:
                getMovieSchedule();
                break;
            case CREATE_BOOK:
                createBook();
                break;
            case CHECK_BOOK:
                checkBook();
                break;
            case VALIDATE_BOOK:
                validateBook();
                break;
            case CANCEL_BOOK:
                cancelBook();
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

    private void createBook() {
        userPanel.showPanel(UserOptions.CREATE_BOOK.name());
    }

    private void checkBook() {
        userPanel.showPanel(UserOptions.CHECK_BOOK.name());
    }

    private void validateBook() {
        userPanel.showPanel(UserOptions.VALIDATE_BOOK.name());
    }

    private void cancelBook() {
        userPanel.showPanel(UserOptions.CANCEL_BOOK.name());
    }
}
