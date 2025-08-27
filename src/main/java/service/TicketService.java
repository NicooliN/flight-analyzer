package service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;
import model.Ticket;
import util.DateTimeUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@UtilityClass
public class TicketService {

    public List<Ticket> loadTicketsFromJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new File(filePath));
        JsonNode ticketsNode = rootNode.get("tickets");

        List<Ticket> tickets = new ArrayList<>();

        for (JsonNode ticketNode : ticketsNode) {
            String origin = ticketNode.get("origin").asText();
            String originName = ticketNode.get("origin_name").asText();
            String destination = ticketNode.get("destination").asText();
            String destinationName = ticketNode.get("destination_name").asText();

            LocalDateTime departure = DateTimeUtils.parseDateTime(
                    ticketNode.get("departure_date").asText(),
                    ticketNode.get("departure_time").asText()
            );

            LocalDateTime arrival = DateTimeUtils.parseDateTime(
                    ticketNode.get("arrival_date").asText(),
                    ticketNode.get("arrival_time").asText()
            );

            String carrier = ticketNode.get("carrier").asText();
            int stops = ticketNode.get("stops").asInt();
            int price = ticketNode.get("price").asInt();

            tickets.add(new Ticket(origin, originName, destination, destinationName,
                    departure, arrival, carrier, stops, price));
        }

        return tickets;
    }

    public List<Ticket> filterTicketsByRoute(List<Ticket> tickets,
                                             String origin, String destination) {
        List<Ticket> filtered = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (origin.equals(ticket.getOrigin()) && destination.equals(ticket.getDestination())) {
                filtered.add(ticket);
            }
        }
        return filtered;
    }

    public List<Ticket> filterTicketsByRouteStream(List<Ticket> tickets,
                                                   String origin, String destination) {
        return tickets.stream()
                .filter(ticket -> origin.equals(ticket.getOrigin()) &&
                        destination.equals(ticket.getDestination()))
                .toList();
    }
}