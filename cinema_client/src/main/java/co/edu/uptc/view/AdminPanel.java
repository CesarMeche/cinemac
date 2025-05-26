package co.edu.uptc.view;

import javax.swing.*;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.view.adminoptions.AddMoviePanel;
import co.edu.uptc.view.adminoptions.ConfigAuditoriumPanel;
import co.edu.uptc.view.adminoptions.CreateScreeningPanel;
import co.edu.uptc.view.adminoptions.DeleteScreeningPanel;
import co.edu.uptc.view.adminoptions.EditMoviePanel;
import co.edu.uptc.view.adminoptions.JButtonPanel;

import java.awt.*;

public class AdminPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public static final String BUTTONS = "buttons";

    public AdminPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(cardLayout);

        contentPanel.add(new JButtonPanel(this), BUTTONS);
        contentPanel.add(new AddMoviePanel(this), AdminOptions.ADD_MOVIE.name());
        contentPanel.add(new EditMoviePanel(this), AdminOptions.EDIT_MOVIE_DATA.name());
        contentPanel.add(new CreateScreeningPanel(this), AdminOptions.CREATE_SCREENING.name());
        contentPanel.add(new DeleteScreeningPanel(this), AdminOptions.DELETE_SCREENING.name());
        contentPanel.add(new ConfigAuditoriumPanel(this), AdminOptions.CONFIGURATE_AUDITORIUM.name());
        contentPanel.add(new ReportPanel(this), AdminOptions.GENERATE_REPORT.name());

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        showPanel(BUTTONS);
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
