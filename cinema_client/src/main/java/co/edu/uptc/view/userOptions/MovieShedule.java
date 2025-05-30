package co.edu.uptc.view.userOptions;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.model.pojos.Movie;
import co.edu.uptc.model.pojos.Screening;
import co.edu.uptc.network.JsonResponse;
import co.edu.uptc.structures.avltree.AVLTree;
import co.edu.uptc.view.panel.UserPanel;

public class MovieShedule extends JPanel {
    private UserPanel user;
    private CardLayout mainCardLayout;
    private JPanel movies;
    private JPanel moviesButtonsPanel;
    private JButton backButton;
    private SelectSeat selectSeat;

    public MovieShedule(UserPanel userPanel) {
        this.user = userPanel;
        mainCardLayout = new CardLayout();
        setLayout(mainCardLayout);
    }

    private void setMoviesJPanels() {
        movies = new JPanel(new BorderLayout(10, 10));
        add(movies, "movies");

        JLabel titleLabel = new JLabel("PELICULAS", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.decode("#1c5052"));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        movies.add(titleLabel, BorderLayout.NORTH);

        moviesButtonsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        moviesButtonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        movies.add(moviesButtonsPanel, BorderLayout.CENTER);

        backButtonConf();
        movies.add(backButton, BorderLayout.SOUTH);
    }

    private void recibeMovies() {
        user.getMainFrame().getController().sendMsg(UserOptions.GET_MOVIE_SCHEDULE.name(),
                UserOptions.GET_MOVIE_SCHEDULE.name(), null);

        JsonResponse<co.edu.uptc.model.pojos.Schedule> moviesResponse = user.getMainFrame().getController()
                .reciveMsg(co.edu.uptc.model.pojos.Schedule.class);

        setMoviesJPanels();

        for (Map.Entry<String, AVLTree<Screening>> entry : moviesResponse.getData().getScreenings().entrySet()) {
            String title = entry.getKey();
            ArrayList<Screening> screeningList = entry.getValue().getInOrder();

            JPanel AUX = createScreeningPanel(screeningList, title);
            add(AUX, title);

            JButton button = createStyledButton(title);
            button.addActionListener(e -> mainCardLayout.show(this, title));
            moviesButtonsPanel.add(button);
        }

        mainCardLayout.show(this, "movies");
    }

    private void backButtonConf() {
        backButton = createStyledButton("Volver");
        backButton.addActionListener(e -> user.backToMenu());
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

    private JPanel createScreeningPanel(ArrayList<Screening> screenings, String title) {
        JPanel dataMovie = new JPanel(new BorderLayout(10, 10));
        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel labelTitle = new JLabel("Funciones de: " + title, JLabel.CENTER);
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelTitle.setForeground(Color.decode("#1c5052"));

        contentPanel.add(labelTitle);

        String[] movieData = getMovieData(screenings.get(0).getMovie());
        for (String info : movieData) {
            JLabel infoLabel = new JLabel(info);
            infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            contentPanel.add(infoLabel);
        }

        for (Screening screening : screenings) {
            contentPanel.add(createDateButtons(screening));
        }

        dataMovie.add(contentPanel, BorderLayout.CENTER);
        dataMovie.add(localBackButton(), BorderLayout.SOUTH);

        return dataMovie;
    }

    private JButton localBackButton() {
        JButton parcialBackButton = createStyledButton("Volver");
        parcialBackButton.addActionListener(e -> mainCardLayout.show(this, "movies"));
        return parcialBackButton;
    }

    private JPanel createDateButtons(Screening screening) {
        LocalDateTime dateTime = screening.getDate();
        String dateString = dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue();
        String hourString = String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());

        JPanel screeningPanelAUX = new JPanel();
        screeningPanelAUX.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton horario = createStyledButton("Fecha: " + dateString + "  Hora: " + hourString);
        horario.addActionListener(e -> selectSeat(screening));

        screeningPanelAUX.add(horario);

        return screeningPanelAUX;
    }

    private void selectSeat(Screening screening) {
        JPanel selectSeatAux = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(screening.getMovie().getTitle(), JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.decode("#1c5052"));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        selectSeatAux.add(titleLabel, BorderLayout.NORTH);

        selectSeat = new SelectSeat(user, screening);
        selectSeatAux.add(selectSeat, BorderLayout.CENTER);
        selectSeatAux.add(localBackButton(), BorderLayout.SOUTH);

        add(selectSeatAux, "select");
        mainCardLayout.show(this, "select");
    }

    private String[] getMovieData(Movie movie) {
        return new String[] { 
            "Calificación por edad: " + movie.getRate(),
            "Calificación: " + movie.getCalification(),
            "Duración: " + movie.getDurationInMinutes() + " minutos",
            "Sinopsis: " + movie.getMovieSynopsis() 
        };
    }

    public void init() {
        recibeMovies();
    }
}
