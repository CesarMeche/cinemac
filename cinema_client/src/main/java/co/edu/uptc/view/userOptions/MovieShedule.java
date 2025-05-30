package co.edu.uptc.view.userOptions;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.model.pojos.Movie;
import co.edu.uptc.model.pojos.Schedule;
import co.edu.uptc.model.pojos.Screening;
import co.edu.uptc.network.JsonResponse;
import co.edu.uptc.structures.avltree.AVLTree;
import co.edu.uptc.view.panel.UserPanel;

public class MovieShedule extends JPanel {
    private UserPanel user;
    private CardLayout mainCardLayout;
    // movies panels
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
        //
        movies = new JPanel();
        movies.setLayout(new BorderLayout());
        // agrega movies al Jpanel/mainCardLayout con el titulo movies
        add(movies, "movies");
        moviesButtonsPanel = new JPanel();
        movies.add(new Label("PELICULAS"), BorderLayout.NORTH);
        backButtonConf();
        movies.add(backButton, BorderLayout.SOUTH);

    }

    private void recibeMovies() {
        // recive*7w7*
        user.getMainFrame().getController().sendMsg(UserOptions.GET_MOVIE_SCHEDULE.name(),
                UserOptions.GET_MOVIE_SCHEDULE.name(), null);
        // guarda*
        JsonResponse<Schedule> moviesResponse = user.getMainFrame().getController().reciveMsg(Schedule.class);
        setMoviesJPanels();
        // recorre para añadir botones con su nombre de peli
        for (Map.Entry<String, AVLTree<Screening>> entry : moviesResponse.getData().getScreenings().entrySet()) {
            // recoge data
            String title = entry.getKey();
            ArrayList<Screening> screeningList = entry.getValue().getInOrder();
            // crea el panel datamovie y lo agrega a Jpanel/mainCardLayout con el titulo de
            // la pelicula como contrains
            JPanel AUX = createScreeningPanel(screeningList, title);
            add(AUX, title);
            // crea el boton que muestra el datamovie
            JButton button = new JButton(title);
            // crea la accion que muestra el datapanel del mainCardLayout
            button.addActionListener(e -> mainCardLayout.show(this, title));
            // agreGA el boton que muestra el datamovie al panel moviesButtonsPanel
            moviesButtonsPanel.add(button);
        }
        // setea el panel movie

        movies.add(moviesButtonsPanel, BorderLayout.CENTER);
        mainCardLayout.show(this, "movies");

    }

    private void backButtonConf() {
        backButton = new JButton("volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.backToMenu();
            }

        });
    }

    private JPanel createScreeningPanel(ArrayList<Screening> screenings, String title) {
        // TODO CREAR CLASE PARA LOS CUADROS HIJOS DE MAIN
        JPanel dataMovie = new JPanel();
        dataMovie.setLayout(new BorderLayout());
        // movie data
        dataMovie.setLayout(new GridLayout(6, 1, 10, 10));
        dataMovie.add(new JLabel("Funciones de: " + title));
        String[] movieData = getMovieData(screenings.get(0).getMovie());
        JPanel datPanel = new JPanel();
        for (String string : movieData) {
            datPanel.add(new JLabel(string));
        }
        // screenings data
        for (Screening screening : screenings) {
            datPanel.add(createDateButtons(screening));
        }
        dataMovie.add(new Label(), BorderLayout.NORTH);
        dataMovie.add(datPanel, BorderLayout.CENTER);
        dataMovie.add(localBackButton(), BorderLayout.SOUTH);
        return dataMovie;
    }

    private JButton localBackButton() {
        JButton parcialBackButton = new JButton("Volver");
        parcialBackButton.addActionListener(e -> {
            mainCardLayout.show(this, "movies");
        });
        return parcialBackButton;
    }

    private JPanel createDateButtons(Screening screening) {
        LocalDateTime dateTime = screening.getDate();
        String dateString = dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue();
        String hourString = String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());
        JPanel screeningPanelAUX = new JPanel();
        JButton horario = new JButton("Fecha: " + dateString + "\nHora: " + hourString);
        horario.addActionListener(e->{
           selectSeat(screening);
        });
        screeningPanelAUX.add(horario);
        return screeningPanelAUX;

    }
    private void selectSeat(Screening screening){
        JPanel selectSeatAux = new JPanel();
        selectSeatAux.setLayout(new BorderLayout());
        selectSeatAux.add(new Label(screening.getMovie().getTitle()),BorderLayout.NORTH);
        selectSeat= new SelectSeat(user,screening);
        selectSeatAux.add(selectSeat, BorderLayout.CENTER);
        selectSeatAux.add(localBackButton(), BorderLayout.SOUTH);
        add(selectSeatAux,"select");
        mainCardLayout.show(this,"select");
    }

    private String[] getMovieData(Movie movie) {
        return new String[] { "Calificacion por edad: " + movie.getRate(), "Calificacion: " + movie.getCalification(),
                "Duracion: " + movie.getDurationInMinutes(),
                "Sinopsis: " + movie.getMovieSynopsis() };
    }

    public void init() {

            recibeMovies();

    }
}