package co.edu.uptc.model.pojos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Seat {
    private String row;
    private int seatNumber;
    private boolean ocuped;
    public Seat(String row, int seatNumber) {
        this.row = row;
        this.seatNumber = seatNumber;
        ocuped=false;
    }
}
