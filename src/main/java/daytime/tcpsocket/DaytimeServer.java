package daytime.tcpsocket;

import inout.Output;
import inout.TCPWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

public class DaytimeServer {
    public static void main(String[] args) {
        int port = 8205;

        try {
            Callable<Output> callable = TCPWriter.accept(port);
            System.out.println("DaytimeServer gestartet. Warte auf Verbindung...");

            Output output = callable.call();

            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            output.printLine("Aktuelles Datum und Uhrzeit: " + dateTime);

            System.out.println("Uhrzeit gesendet: " + dateTime);
            output.shutdownOutput();

        } catch (Exception e) {
            System.err.println("Fehler im DaytimeServer: " + e.getMessage());
        }
    }
}
