package co.edu.uptc.view.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.menus.UserMenu;
import co.edu.uptc.view.userOptions.*;

public class UserPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private HashMap<String, JPanel> panels;

    

    public static final String BUTTONS = "buttons";

    public UserPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(cardLayout);
        panels= new HashMap<>();
        contentPanel.add(new UserMenu(this), BUTTONS);

        addPanel(new MovieShedule(this), UserOptions.GET_MOVIE_SCHEDULE.name());

        addPanel(new CheckBook(this), UserOptions.CHECK_BOOK.name());

        addPanel(new ValidateBook(this), UserOptions.VALIDATE_BOOK.name());

        addPanel(new CancelBook(this), UserOptions.CANCEL_BOOK.name());

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        showPanel(BUTTONS);
    }

    public void addPanel(JPanel panel, String name) {
        contentPanel.add(panel,name);
        panels.put(name, panel);
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }

    public void backToMenu() {
        showPanel(BUTTONS);
        
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
    public HashMap<String, JPanel> getPanels() {
        return panels;
    }
}
