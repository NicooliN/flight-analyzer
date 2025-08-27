package model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.AllArgsConstructor;


@Getter
@AllArgsConstructor
public class Ticket {
    private final String origin;
    private final String originName;
    private final String destination;
    private final String destinationName;
    private final LocalDateTime departure;
    private final LocalDateTime arrival;
    private final String carrier;
    private final int stops;
    private final int price;

    public long getFlightDurationMinutes() {
        return java.time.Duration.between(departure, arrival).toMinutes();
    }
}