package co.edu.uptc.view.menus;

import java.awt.BorderLayout;
import java.awt.Color;
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

    public AdminMenu(AdminPanel adminPanel) {
    this.adminPanel = adminPanel;
    setLayout(new BorderLayout(20, 20));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    setBackground(Color.decode("#f2f2f2"));

    JLabel title = new JLabel("Panel de Administración", JLabel.CENTER);
    title.setFont(new Font("SansSerif", Font.BOLD, 22));
    title.setForeground(Color.decode("#1c5052"));
    add(title, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 12, 12));
    buttonPanel.setBackground(Color.decode("#f2f2f2"));

    // Crea y estiliza los botones con el mismo estilo para todos
    addMovieBtn = createStyledButton("Agregar Película");
    editMovieBtn = createStyledButton("Editar Datos de Película");
    createScreeningBtn = createStyledButton("Crear Función");
    deleteScreeningBtn = createStyledButton("Eliminar Función");
    configAuditoriumBtn = createStyledButton("Configurar Auditorio");
    generateReportBtn = createStyledButton("Generar Reporte");

    buttonPanel.add(addMovieBtn);
    buttonPanel.add(editMovieBtn);
    buttonPanel.add(createScreeningBtn);
    buttonPanel.add(deleteScreeningBtn);
    buttonPanel.add(configAuditoriumBtn);
    buttonPanel.add(generateReportBtn);

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
    // Puedes agregar aquí un efecto hover si quieres
    return button;
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
