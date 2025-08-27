package service;

import lombok.experimental.UtilityClass;
import model.Ticket;
import util.DateTimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class FlightAnalyzerService {

    public Map<String, Long> calculateMinFlightTimes(List<Ticket> tickets) {
        Map<String, Long> minTimes = new HashMap<>();

        for (Ticket ticket : tickets) {
            String carrier = ticket.getCarrier();
            long duration = ticket.getFlightDurationMinutes();

            minTimes.merge(carrier, duration, Math::min);
        }

        return minTimes;
    }

    public Map<String, Long> calculateMinFlightTimesStream(List<Ticket> tickets) {
        return tickets.stream()
                .collect(Collectors.toMap(
                        Ticket::getCarrier,
                        Ticket::getFlightDurationMinutes,
                        Math::min
                ));
    }

    public void printAnalysisResults(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println("Нет билетов для анализа");
            return;
        }

        // Минимальное время по перевозчикам
        Map<String, Long> minFlightTimes = calculateMinFlightTimes(tickets);

        // Цены для статистики
        List<Integer> prices = tickets.stream()
                .map(Ticket::getPrice)
                .collect(Collectors.toList());

        double priceDifference = PriceCalculatorService.calculatePriceDifference(prices);

        // Вывод результатов
        System.out.println("Минимальное время полета между Владивостоком и Тель-Авивом:");

        minFlightTimes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String formattedDuration = DateTimeUtils.formatDuration(entry.getValue());
                    System.out.printf("%s: %s%n", entry.getKey(), formattedDuration);
                });

        System.out.printf("%nРазница между средней ценой и медианой: %.2f рублей%n", priceDifference);
    }
}