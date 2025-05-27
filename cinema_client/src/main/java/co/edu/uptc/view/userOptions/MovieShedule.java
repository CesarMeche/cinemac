package co.edu.uptc.view.userOptions;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.model.pojos.Movie;
import co.edu.uptc.model.pojos.Schedule;
import co.edu.uptc.model.pojos.Screening;
import co.edu.uptc.network.JsonResponse;
import co.edu.uptc.view.panel.AdminPanel;
import co.edu.uptc.view.panel.UserPanel;

public class MovieShedule extends JPanel {
    private UserPanel user;
    private JPanel screeningPanel;
    private CardLayout moviesCardLayout;
    private JButton backButton;

    public MovieShedule(UserPanel userPanel) {
        this.user = userPanel;
        setLayout(new BorderLayout());
        screeningPanel = new JPanel();
        moviesCardLayout = new CardLayout();
        screeningPanel.setLayout(moviesCardLayout);
    }

    public void init() {
        recibeMovies();
    }

    private void recibeMovies() {
        user.getMainFrame().getController().sendMsg(UserOptions.GET_MOVIE_SCHEDULE.name(), UserOptions.GET_MOVIE_SCHEDULE.name(), null);
        JsonResponse<Schedule> movies = user.getMainFrame().getController().reciveMsg(Schedule.class);

        if (movies.getData() == null || movies.getData().getScreenings() == null) {
            System.out.println("No hay datos");
            return;
        }

        ArrayList<JButton> moviesList = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Screening>> entry : movies.getData().getScreenings().entrySet()) {
            String title = entry.getKey();

            JButton button = new JButton(title);
            button.addActionListener(e -> moviesCardLayout.show(screeningPanel, title));
            moviesList.add(button);

            ArrayList<Screening> screeningList = entry.getValue();
            createScreeningPanel(screeningList, title);
        }

        // Añade los botones en la parte superior, por ejemplo
        JPanel buttonPanel = new JPanel();
        for (JButton jButton : moviesList) {
            buttonPanel.add(jButton);
        }
        add(buttonPanel, BorderLayout.NORTH);
        add(screeningPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        backButton = new JButton("volver");
        add(backButton,BorderLayout.SOUTH);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.backToMenu();
            }

        });
    }

    private void createScreeningPanel(ArrayList<Screening> screenings, String title) {
        JPanel movie = new JPanel();
        // Aquí puedes personalizar 'movie' con datos de screenings
        movie.add(new JLabel("Funciones de: " + title));
        String[] movieData = getMovieData(screenings.get(0).getMovie());
        for (String string : movieData) {
            movie.add(new JLabel(string));
        }
        LocalDateTime a;
        for (Screening screening : screenings) {
            LocalDateTime dateTime = screening.getDate();

            // Obtener día y mes
            String dateString = dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue();

            // Obtener hora y minutos
            String hourString = String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());

            // Crear un panel para este screening
            JPanel screeningPanel = new JPanel();
            screeningPanel.setLayout(new BoxLayout(screeningPanel, BoxLayout.Y_AXIS));

            // Añadir los JLabels
            screeningPanel.add(new JLabel("Fecha: " + dateString));
            screeningPanel.add(new JLabel("Hora: " + hourString));

            // Separación o borde para que se vea mejor
            screeningPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // AQUÍ ya decides dónde quieres agregarlo,
            // por ejemplo, a tu panel de película:
            movie.add(screeningPanel);
        }

        screeningPanel.add(movie, title);
    }

    private String[] getMovieData(Movie movie) {
        return new String[] { "Calificacion por edad: " + movie.getRate(), "Calificacion: " + movie.getCalification(),
                "Duracion: " + movie.getDurationInMinutes(),
                "Sinopsis: " + movie.getMovieSynopsis() };
    }
}