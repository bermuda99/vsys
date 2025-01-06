package daytime.tcp;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DaytimeServer {
    public static void main(String[] args) {
        int port = 8205;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("DaytimeServer läuft auf Port " + port);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true)) {
                    System.out.println("Verbindung von " + clientSocket.getInetAddress());
                    String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    out.println(currentTime);
                    System.out.println("Zeit gesendet: " + currentTime);
                } catch (Exception e) {
                    System.err.println("Fehler bei der Client-Verarbeitung: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Starten des Servers: " + e.getMessage());
        }
    }
}
