package co.edu.uptc.view.menus;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.enums.AdminOptions;
import co.edu.uptc.view.panel.AdminPanel;

public class AdminMenu extends JPanel {
    private AdminPanel adminPanel;
    private JButton addMovieBtn;
    private JButton editMovieBtn;
    private JButton createScreeningBtn;
    private JButton deleteScreeningBtn;
    private JButton configAuditoriumBtn;
    private JButton generateReportBtn;

    public AdminMenu( AdminPanel adminPanel) {
        this.adminPanel=adminPanel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Panel de Administración", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Panel de botones en forma de menú
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        addMovieBtn = new JButton("Agregar Película");
        editMovieBtn = new JButton("Editar Datos de Película");
        createScreeningBtn = new JButton("Crear Función");
        deleteScreeningBtn = new JButton("Eliminar Función");
        configAuditoriumBtn = new JButton("Configurar Auditorio");
        generateReportBtn = new JButton("Generar Reporte");

        // Añadir botones al panel
        buttonPanel.add(addMovieBtn);
        buttonPanel.add(editMovieBtn);
        buttonPanel.add(createScreeningBtn);
        buttonPanel.add(deleteScreeningBtn);
        buttonPanel.add(configAuditoriumBtn);
        buttonPanel.add(generateReportBtn);

        add(buttonPanel, BorderLayout.CENTER);
         initActions();
    }

    private void initActions() {
        addMovieBtn.addActionListener(e -> handleAction("ADD_MOVIE"));
        editMovieBtn.addActionListener(e -> handleAction("EDIT_MOVIE_DATA"));
        createScreeningBtn.addActionListener(e -> handleAction("CREATE_SCREENING"));
        deleteScreeningBtn.addActionListener(e -> handleAction("DELETE_SCREENING"));
        configAuditoriumBtn.addActionListener(e -> handleAction("CONFIGURATE_AUDITORIUM"));
        generateReportBtn.addActionListener(e -> handleAction("GENERATE_REPORT"));
    }

    private void handleAction(String action) {
        AdminOptions adminOptions = AdminOptions.valueOf(action);
        switch (adminOptions) {
            case ADD_MOVIE:
                addMovie();
                break;
            case EDIT_MOVIE_DATA:
                editMovieData();
                break;
            case CREATE_SCREENING:
                createScreening();
                break;
            case DELETE_SCREENING:
                deleteScreening();
                break;
            case CONFIGURATE_AUDITORIUM:
                configurateAuditorium();
                break;
            case GENERATE_REPORT:
                generateReport();
                break;
            default:
                System.out.println("Acción no reconocida: " + action);
        }
    }

    private void addMovie() {
        adminPanel.showPanel(AdminOptions.ADD_MOVIE.name());
    }

    private void editMovieData() {
         adminPanel.showPanel(AdminOptions.EDIT_MOVIE_DATA.name());
    }

    private void createScreening() {
         adminPanel.showPanel(AdminOptions.CREATE_SCREENING.name());
    }

    private void deleteScreening() {
         adminPanel.showPanel(AdminOptions.DELETE_SCREENING.name());
    }

    private void configurateAuditorium() {
         adminPanel.showPanel(AdminOptions.CONFIGURATE_AUDITORIUM.name());
    }

    private void generateReport() {
         adminPanel.showPanel(AdminOptions.GENERATE_REPORT.name());
    }
}
