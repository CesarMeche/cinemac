package co.edu.uptc.view.userOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import co.edu.uptc.enums.Msg;
import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.model.pojos.Screening;
import co.edu.uptc.model.pojos.Seat;
import co.edu.uptc.view.adminoptions.AddMoviePanel;
import co.edu.uptc.view.panel.UserPanel;

public class SelectSeat extends JPanel {
    private UserPanel user;
    private Screening screening;
    private Seat selectedSeat = null; // Asiento seleccionado
    private JButton selectedButton = null; // Botón actualmente seleccionado

    public SelectSeat(UserPanel userPanel, Screening screening) {
        this.user = userPanel;
        this.screening = screening;
        setLayout(new BorderLayout());

        JPanel seatsPanel = new JPanel(new GridLayout(screening.getScreeningAuditorium().getSeat().length,
                screening.getScreeningAuditorium().getSeat()[0].length, 5, 5));

        Seat[][] seats = screening.getScreeningAuditorium().getSeat();
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                Seat seat = seats[i][j];
                JButton seatButton = new JButton((seat.getRow()) + "-" + (seat.getSeatNumber()));
                if (seat.isOcuped()) {
                    seatButton.setEnabled(false);
                    seatButton.setBackground(Color.RED);
                } else {
                    seatButton.setBackground(Color.GREEN);
                    seatButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Desmarcar el botón anterior (si hay uno)
                            if (selectedButton != null) {
                                selectedButton.setBackground(Color.GREEN);
                            }

                            // Marcar el nuevo botón como seleccionado (naranja)
                            selectedButton = seatButton;
                            selectedButton.setBackground(Color.ORANGE);

                            // Guardar el asiento seleccionado
                            selectedSeat = seat;
                        }
                    });
                }
                seatsPanel.add(seatButton);
            }
        }

        JButton backButton = new JButton("Comprar");
        backButton.addActionListener(e -> {
            String[] data = { screening.getMovie().getTitle(), screening.getScreeningAuditorium().getName(),screening.getDate().toString(),screening.getDate().toString(), selectedSeat.getRow(), "" + selectedSeat.getSeatNumber() };
            user.getMainFrame().getController().sendMsg(UserOptions.CHECK_BOOK.name(), UserOptions.SELECT_SEAT.name(),
                    data);
            Msg anwer = Msg.valueOf(user.getMainFrame().getController().reciveMsg().getMessage());

            switch (anwer) {
                case DONE:
                    JOptionPane.showMessageDialog(SelectSeat.this,
                            "Reserva creada");
                            user.backToMenu();
                    break;
                case Error:
                    JOptionPane.showMessageDialog(SelectSeat.this,
                            "Reserva no creada");
                    break;
                default:
                    break;
            }

        });

        add(new JLabel("Selecciona tu asiento:", SwingConstants.CENTER), BorderLayout.NORTH);
        add(seatsPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    public Seat getSelectedSeat() {
        return selectedSeat;
    }
}
