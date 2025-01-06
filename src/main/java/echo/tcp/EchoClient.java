package echo.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host> <port>");
            return;
        }

        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Verbindung zum EchoServer hergestellt: " + serverAddress + ":" + port);
            System.out.println("Geben Sie eine Nachricht ein (oder 'exit' zum Beenden):");

            String userInput;
            while (true) {
                System.out.print("Client: ");
                userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    System.out.println("Verbindung wird beendet...");
                    break;
                }

                // Nachricht an den Server senden
                out.println(userInput);

                // Antwort vom Server empfangen
                String response = in.readLine();
                System.out.println("Server: " + response);
            }

        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }
}
