
import lombok.extern.java.Log;
import model.Ticket;
import service.FlightAnalyzerService;
import service.TicketService;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

@Log
public class Main {

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                // Режим командной строки
                processFile(args[0]);
            } else {
                // Интерактивный режим
                runInteractive();
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ошибка при выполнении программы", e);
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void processFile(String filePath) throws Exception {
        System.out.println("Анализ файла: " + filePath);
        System.out.println("========================================");

        List<Ticket> allTickets = TicketService.loadTicketsFromJson(filePath);
        List<Ticket> vvoTlvTickets = TicketService.filterTicketsByRoute(
                allTickets, "VVO", "TLV"
        );

        if (vvoTlvTickets.isEmpty()) {
            System.out.println("Не найдено билетов для маршрута Владивосток -> Тель-Авив");
            return;
        }

        FlightAnalyzerService.printAnalysisResults(vvoTlvTickets);
    }

    private static void runInteractive() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("=== Анализатор авиабилетов ===");
            System.out.print("Введите путь к файлу JSON: ");
            String filePath = scanner.nextLine().trim();

            if (filePath.isEmpty()) {
                filePath = "tickets.json";
            }

            processFile(filePath);

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}