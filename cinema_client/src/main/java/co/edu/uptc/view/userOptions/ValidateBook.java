package co.edu.uptc.view.userOptions;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import co.edu.uptc.enums.Msg;
import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.model.pojos.Book;
import co.edu.uptc.network.JsonResponse;
import co.edu.uptc.view.panel.UserPanel;

public class ValidateBook extends JPanel {
    private UserPanel user;
    private JPanel centerPanel;
    private Book reservaActual;

    public ValidateBook(UserPanel userPanel) {
        this.user = userPanel;
        setLayout(new BorderLayout());
        setUpTitle();
        setUpCenterPanel();
        setUpBackButton();
    }

    public void init() {
        mostrarListaReservas();
    }

    private void setUpTitle() {
        JLabel titleLabel = new JLabel("Reservaciones", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);
    }

    private void setUpCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setUpBackButton() {
        JButton backButton = new JButton("Volver");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0x4285F4));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> user.backToMenu());
        add(backButton, BorderLayout.SOUTH);
    }

    private void mostrarListaReservas() {
        centerPanel.removeAll();

        user.getMainFrame().getController().sendMsg(
                UserOptions.CHECK_BOOK.name(),
                UserOptions.CHECK_BOOK.name(), "");
        JsonResponse<List> response = user.getMainFrame().getController().reciveMsg(List.class);

        if (Msg.valueOf(response.getMessage()).equals(Msg.DONE)) {
            List<Book> books = convertirReservas(response.getData());

            if (books.isEmpty()) {
                agregarLabelCentro("No hay reservas disponibles");
            } else {
                for (Book book : books) {
                    JButton reservaButton = crearBotonReserva(book);
                    reservaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    centerPanel.add(Box.createVerticalStrut(10));
                    centerPanel.add(reservaButton);
                }
            }
        } else {
            agregarLabelCentro("No hay reservas disponibles");
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private List<Book> convertirReservas(List<?> rawList) {
        List<Book> books = new ArrayList<>();
        for (Object obj : rawList) {
            if (obj instanceof LinkedHashMap) {
                LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) obj;
                Book book = new Book();

                book.setId((String) map.get("id"));
                book.setMovieTitle((String) map.get("movieTitle"));
                book.setAuditoriumName((String) map.get("auditoriumName"));
                ArrayList<Integer> date = (ArrayList<Integer>) map.get("date");
                book.setDate(LocalDateTime.of(
                        date.get(0), date.get(1), date.get(2),
                        date.get(3), date.get(4), date.get(5)));
                book.setSeatRow((String) map.get("seatRow"));
                book.setSeatNumber((Integer) map.get("seatNumber"));
                book.setValidated((Boolean) map.get("validated"));

                books.add(book);
            }
        }
        return books;
    }

    private JButton crearBotonReserva(Book book) {
        String buttonText = "Reserva: " + book.getId() + " - " + book.getMovieTitle();
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(500, 50));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(book.isValidated() ? new Color(0x34A853) : new Color(0xF4B400));
        button.setForeground(Color.WHITE);

        button.addActionListener(e -> mostrarDetalleReserva(book));
        return button;
    }

    private void mostrarDetalleReserva(Book book) {
        reservaActual = book;
        centerPanel.removeAll();

        // Panel para detalles
        JPanel detallesPanel = new JPanel();
        detallesPanel.setLayout(new BoxLayout(detallesPanel, BoxLayout.Y_AXIS));
        detallesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        detallesPanel.add(crearLabelDetalle("ID:", book.getId()));
        detallesPanel.add(crearLabelDetalle("Película:", book.getMovieTitle()));
        detallesPanel.add(crearLabelDetalle("Auditorio:", book.getAuditoriumName()));
        detallesPanel.add(crearLabelDetalle("Fecha:", book.getDate().toString()));
        detallesPanel.add(crearLabelDetalle("Puesto:", book.getSeatRow() + book.getSeatNumber()));

        centerPanel.add(detallesPanel);

        centerPanel.add(Box.createVerticalStrut(20));

        // Panel con los tres botones alineados horizontalmente
        JPanel accionesPanel = crearPanelAcciones(book.isValidated());
        centerPanel.add(accionesPanel);

        centerPanel.add(Box.createVerticalStrut(10));

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JPanel crearLabelDetalle(String titulo, String valor) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel labelTitulo = new JLabel(titulo + " ");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel labelValor = new JLabel(valor);
        labelValor.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(labelTitulo, BorderLayout.WEST);
        panel.add(labelValor, BorderLayout.CENTER);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }

    private JPanel crearPanelAcciones(boolean validada) {
        JPanel accionesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        if (!validada) {
            JButton validarButton = new JButton("Validar");
            validarButton.setBackground(new Color(0x34A853));
            validarButton.setForeground(Color.WHITE);
            validarButton.setPreferredSize(new Dimension(120, 40));
            validarButton.addActionListener(e -> validarReserva());

            JButton cancelarButton = new JButton("Cancelar");
            cancelarButton.setBackground(new Color(0xEA4335));
            cancelarButton.setForeground(Color.WHITE);
            cancelarButton.setPreferredSize(new Dimension(120, 40));
            cancelarButton.addActionListener(e -> cancelarReserva());

            accionesPanel.add(validarButton);
            accionesPanel.add(cancelarButton);
        } else {
            JLabel validadaLabel = new JLabel("Reserva validada", SwingConstants.CENTER);
            validadaLabel.setFont(new Font("Arial", Font.BOLD, 18));
            validadaLabel.setForeground(new Color(0x34A853));
            accionesPanel.add(validadaLabel);
        }

        JButton backButton = new JButton("Volver a reservas");
        backButton.setBackground(new Color(0x4285F4));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(180, 40)); 
        backButton.addActionListener(e -> mostrarListaReservas());
        accionesPanel.add(backButton);

        return accionesPanel;
    }

    private void validarReserva() {
        if (reservaActual != null) {
            user.getMainFrame().getController().sendMsg(UserOptions.VALIDATE_BOOK.name(),
                    UserOptions.VALIDATE_BOOK.name(), reservaActual.getId());
            JsonResponse<Boolean> answer = user.getMainFrame().getController().reciveMsg();
            if (answer.getData()) {
                JOptionPane.showMessageDialog(this, "Reserva validada exitosamente");
               mostrarListaReservas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al validar la reserva");
            }
        }
    }

    private void cancelarReserva() {
        if (reservaActual != null) {
            user.getMainFrame().getController().sendMsg(UserOptions.CANCEL_BOOK.name(),
                    UserOptions.CANCEL_BOOK.name(), reservaActual.getId());
            JsonResponse<Boolean> answer = user.getMainFrame().getController().reciveMsg();
            if (answer.getData()) {
                JOptionPane.showMessageDialog(this, "Reserva cancelada exitosamente");
                user.backToMenu();
            } else {
                JOptionPane.showMessageDialog(this, "Error al cancelar la reserva");
            }
        }
    }

    private void agregarLabelCentro(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(label);
    }
}
