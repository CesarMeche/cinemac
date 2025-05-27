package co.edu.uptc.view.userOptions;

import java.awt.CardLayout;
import java.awt.LayoutManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.model.pojos.Schedule;
import co.edu.uptc.model.pojos.Screening;
import co.edu.uptc.network.JsonResponse;
import co.edu.uptc.view.panel.AdminPanel;
import co.edu.uptc.view.panel.UserPanel;

public class MovieShedule extends JPanel {
    private UserPanel user;
    private JPanel screeningPanel;
    private CardLayout moviesCardLayout;

    public MovieShedule(UserPanel userPanel) {
        this.user = userPanel;
        screeningPanel= new JPanel();
        screeningPanel.setLayout(moviesCardLayout);
    }
    public void init(){
        recibeMovies();
    }

    private void recibeMovies() {
        user.getMainFrame().getController().sendMsg(UserOptions.GET_MOVIE_SCHEDULE.name(), UserOptions.GET_MOVIE_SCHEDULE.name(), null);
        JsonResponse<Schedule> movies=user.getMainFrame().getController().reciveMsg();
        ArrayList<JButton> movieslList= new ArrayList<>();
        for (Map.Entry<String, ArrayList<Screening>> entry : movies.getData().getScreenings().entrySet()) {
            String title =entry.getKey();
            movieslList.add(new JButton(){{
                addActionListener(e->{
                    moviesCardLayout.show(screeningPanel, title);
                });
            }});
            ArrayList<Screening> screeningList=entry.getValue();
            createScreeningPanel(screeningList,title);




            
        }
        
        
    }
    private void createScreeningPanel( ArrayList<Screening> screening, String title){
        JPanel movie= new JPanel();
        
        moviesCardLayout.addLayoutComponent(movie, title);
    }   
}
