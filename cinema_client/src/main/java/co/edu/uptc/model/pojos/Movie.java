package co.edu.uptc.model.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private String Title;
    private String calification;
    private String movieSynopsis;
    private String rate;
    private String durationInMinutes;
}
