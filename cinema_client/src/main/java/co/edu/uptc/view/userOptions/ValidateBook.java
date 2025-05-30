package co.edu.uptc.view.userOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.time.LocalDateTime;
import co.edu.uptc.enums.Msg;
import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.model.pojos.*;
import co.edu.uptc.network.JsonResponse;
import co.edu.uptc.view.panel.UserPanel;

public class ValidateBook extends JPanel {
    private UserPanel user;
    private JPanel centerPanel;
    private JButton backButton;
    private JButton validarButton;
    private JButton cancelarButton;

    private Book reservaActual; // ⭐️ Aquí se guarda la reserva actual seleccionada

    public ValidateBook(UserPanel userPanel) {
        this.user = userPanel;
        initBase();
    }

    private void initBase() {
        setLayout(new BorderLayout());
        addTitle();
        initBackButton();
        initCenterPanel(); // ⭐️ Inicializa el panel vacío (sin reservas todavía)
    }

    // ⭐️ Llamado cada vez que se muestra este panel
    public void init() {
        mostrarListaReservas(); // 🔄 Recarga la lista de reservas cada vez
    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("Reservaciones", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initBackButton() {
        backButton = new JButton("Volver");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(createBackButtonListener());
        add(backButton, BorderLayout.SOUTH);
    }

    private void mostrarListaReservas() {
        centerPanel.removeAll();

        // Solicita las reservas
        user.getMainFrame().getController().sendMsg(UserOptions.CHECK_BOOK.name(), UserOptions.CHECK_BOOK.name(), "");
        JsonResponse<List> moviesResponse = user.getMainFrame().getController().reciveMsg(List.class);

        if (Msg.valueOf(moviesResponse.getMessage()).equals(Msg.DONE)) {
            List<Book> books = new ArrayList<>();
            List<?> rawList = moviesResponse.getData();

            for (Object obj : rawList) {
                if (obj instanceof LinkedHashMap) {
                    LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) obj;
                    Book book = new Book();

                    book.setId((String) map.get("id"));
                    book.setMovieTitle((String) map.get("movieTitle"));
                    book.setAuditoriumName((String) map.get("auditoriumName"));

                    // Convertir el date a LocalDateTime
                    ArrayList<Integer> date = (ArrayList<Integer>) map.get("date");
                    book.setDate(LocalDateTime.of(
                            date.get(0), date.get(1), date.get(2),
                            date.get(3), date.get(4), date.get(5)));

                    book.setSeatRow((String) map.get("seatRow"));
                    book.setSeatNumber((Integer) map.get("seatNumber"));
                    book.setValidated((Boolean)map.get("validated"));
                    books.add(book);
                }
            }

            if (books.isEmpty()) {
                JLabel noReservationsLabel = new JLabel("No hay reservas disponibles");
                noReservationsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                noReservationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
                centerPanel.add(Box.createVerticalStrut(10));
                centerPanel.add(noReservationsLabel);
            } else {
                for (Book book : books) {

                    JButton reservaButton = crearBotonReserva(book);
                    centerPanel.add(Box.createVerticalStrut(10));
                    centerPanel.add(reservaButton);

                }
            }
        } else {
            JLabel noReservationsLabel = new JLabel("No hay reservas disponibles");
            noReservationsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noReservationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(Box.createVerticalStrut(10));
            centerPanel.add(noReservationsLabel);
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JButton crearBotonReserva(Book book) {
        String buttonName = "Reserva:  " + book.getId();
        JButton reservaButton = new JButton(buttonName);
        reservaButton.setPreferredSize(new Dimension(700, 80));
        reservaButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        reservaButton.setFont(new Font("Arial", Font.PLAIN, 18));

        if (!book.isValidated()) {
            reservaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarInfoReserva(book);
                }
            });
        } else {
            reservaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    unValidatedBookInfo(book);
                }
            });
        }
        return reservaButton;
    }

    private void unValidatedBookInfo(Book book) {
        reservaActual = book;

        centerPanel.removeAll();

        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(new JLabel("ID: " + book.getId()));
        centerPanel.add(new JLabel("Pelicula: " + book.getMovieTitle()));
        centerPanel.add(new JLabel("Auditorio: " + book.getAuditoriumName()));
        centerPanel.add(new JLabel("Fecha: " + book.getDate().toString()));
        centerPanel.add(new JLabel("Puesto: " + book.getSeatRow() + book.getSeatNumber()));

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(localBackButton());
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JButton localBackButton() {
        JButton localBackButton = new JButton();
        localBackButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarListaReservas();
            }

        });
        return localBackButton;

    }

    private void mostrarInfoReserva(Book book) {
        reservaActual = book;

        centerPanel.removeAll();

        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(new JLabel("ID: " + book.getId()));
        centerPanel.add(new JLabel("Pelicula: " + book.getMovieTitle()));
        centerPanel.add(new JLabel("Auditorio: " + book.getAuditoriumName()));
        centerPanel.add(new JLabel("Fecha: " + book.getDate().toString()));
        centerPanel.add(new JLabel("Puesto: " + book.getSeatRow() + book.getSeatNumber()));

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(crearBotonesValidarCancelarPanel());
        centerPanel.add(localBackButton());
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JPanel crearBotonesValidarCancelarPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        initValidarButton();
        initCancelarButton();

        buttonsPanel.add(validarButton);
        buttonsPanel.add(cancelarButton);

        return buttonsPanel;
    }

    private void initValidarButton() {
        validarButton = new JButton("Validar");
        validarButton.setPreferredSize(new Dimension(120, 40));
        validarButton.addActionListener(createValidarButtonListener());
    }

    private void initCancelarButton() {
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setPreferredSize(new Dimension(120, 40));
        cancelarButton.addActionListener(createCancelarButtonListener());
    }

    private ActionListener createValidarButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reservaActual != null) {
                    user.getMainFrame().getController().sendMsg(
                            UserOptions.VALIDATE_BOOK.name(),
                            UserOptions.VALIDATE_BOOK.name(),
                            reservaActual.getId());
                    JsonResponse<Boolean> answer = user.getMainFrame().getController().reciveMsg();
                    if (answer.getData()) {
                        JOptionPane.showMessageDialog(ValidateBook.this, "Reserva validada exitosamente");
                        user.backToMenu();
                    } else {
                        JOptionPane.showMessageDialog(ValidateBook.this, "Error al validar la reserva");
                    }
                }
            }
        };
    }

    private ActionListener createCancelarButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reservaActual != null) {
                    user.getMainFrame().getController().sendMsg(
                            UserOptions.CANCEL_BOOK.name(),
                            UserOptions.CANCEL_BOOK.name(),
                            reservaActual.getId());
                    JsonResponse<Boolean> answer = user.getMainFrame().getController().reciveMsg();
                    if (answer.getData()) {
                        JOptionPane.showMessageDialog(ValidateBook.this, "Reserva cancelada exitosamente");
                        user.backToMenu();
                    } else {
                        JOptionPane.showMessageDialog(ValidateBook.this, "Error al cancelar la reserva");
                    }
                }
            }
        };
    }

    private ActionListener createBackButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.backToMenu();
            }
        };
    }
}
