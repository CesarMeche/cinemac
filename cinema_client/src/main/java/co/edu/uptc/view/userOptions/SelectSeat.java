package co.edu.uptc.view.userOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import co.edu.uptc.enums.Msg;
import co.edu.uptc.enums.UserOptions;
import co.edu.uptc.model.pojos.Screening;
import co.edu.uptc.model.pojos.Seat;
import co.edu.uptc.view.panel.UserPanel;

public class SelectSeat extends JPanel {
    private UserPanel user;
    private Screening screening;
    private Seat selectedSeat = null;
    private JButton selectedButton = null;

    public SelectSeat(UserPanel userPanel, Screening screening) {
        this.user = userPanel;
        this.screening = screening;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel seatsPanel = new JPanel(new GridLayout(
            screening.getScreeningAuditorium().getSeat().length,
            screening.getScreeningAuditorium().getSeat()[0].length,
            5, 5));

        Seat[][] seats = screening.getScreeningAuditorium().getSeat();
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                Seat seat = seats[i][j];
                JButton seatButton = new JButton(seat.getRow() + "-" + seat.getSeatNumber());
                seatButton.setFont(new Font("SansSerif", Font.BOLD, 12));

                if (seat.isOcuped()) {
                    seatButton.setEnabled(false);
                    seatButton.setBackground(Color.RED);
                    seatButton.setForeground(Color.WHITE);
                } else {
                    seatButton.setBackground(Color.GREEN);
                    seatButton.setForeground(Color.BLACK);
                    seatButton.setFocusPainted(false);

                    seatButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (selectedButton != null) {
                                selectedButton.setBackground(Color.GREEN);
                                selectedButton.setForeground(Color.BLACK);
                            }
                            selectedButton = seatButton;
                            selectedButton.setBackground(Color.ORANGE);
                            selectedButton.setForeground(Color.BLACK);
                            selectedSeat = seat;
                        }
                    });
                }
                seatsPanel.add(seatButton);
            }
        }

        JButton buyButton = createStyledButton("Comprar");
        buyButton.addActionListener(e -> {
            if (selectedSeat == null) {
                JOptionPane.showMessageDialog(this, "Por favor selecciona un asiento antes de comprar.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] data = {
                    screening.getMovie().getTitle(),
                    screening.getScreeningAuditorium().getName(),
                    screening.getDate().toString(),
                    selectedSeat.getRow(),
                    String.valueOf(selectedSeat.getSeatNumber())
            };

            user.getMainFrame().getController().sendMsg(UserOptions.CREATE_BOOK.name(), UserOptions.CREATE_BOOK.name(),
                    data);
            Msg answer = Msg.valueOf(user.getMainFrame().getController().reciveMsg().getMessage());

            switch (answer) {
                case DONE:
                    JOptionPane.showMessageDialog(SelectSeat.this, "Reserva creada");
                    user.backToMenu();
                    break;
                case Error:
                    JOptionPane.showMessageDialog(SelectSeat.this, "Reserva no creada");
                    break;
                default:
                    break;
            }
        });

        JLabel titleLabel = new JLabel("Selecciona tu asiento:", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.decode("#1c5052"));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        add(titleLabel, BorderLayout.NORTH);
        add(seatsPanel, BorderLayout.CENTER);
        add(buyButton, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(Color.decode("#348e91"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        return button;
    }

    public Seat getSelectedSeat() {
        return selectedSeat;
    }
}
