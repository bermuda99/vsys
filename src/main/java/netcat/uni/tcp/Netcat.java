package netcat.uni.tcp;

import inout.*;

public class Netcat {
    public static void main(String[] args) {
        try {
            if (args.length == 2 && args[0].equals("-l")) {
                // Server-Modus
                int localPort = Integer.parseInt(args[1]);

                // Server-Logik
                Input tcpReader = TCPReader.accept(localPort).call(); // Wartet auf eingehende Verbindungen
                ConsoleWriter consoleWriter = ConsoleWriter.stdout(); // Schreibt Nachrichten auf die Konsole
                tcpReader.readLines().forEach(line -> {
                    consoleWriter.printLine(line); // Nachricht zurück an den Client senden
                });
                tcpReader.shutdownInput(); // Schließt die Verbindung
                consoleWriter.shutdownOutput();

            } else if (args.length == 2) {
                // Client-Modus
                String remoteHost = args[0];
                int remotePort = Integer.parseInt(args[1]);
                System.out.println("Klientel konnektiert mit: " + remoteHost + ":" + remotePort);

                // Client-Logik
                ConsoleReader consoleReader = ConsoleReader.stdin(); // Liest Eingaben von der Konsole
                Output tcpWriter = TCPWriter.connectTo(remoteHost, remotePort).call(); // Verbindet zum Server
                System.out.println("Bitte brieftaube schicken:");
                consoleReader.readLines().forEach(line -> { // Senden von Nachrichten
                    System.out.println("Klientels brieftaube sagt: " + line); // Debug-Ausgabe
                    tcpWriter.printLine(line);
                });
                consoleReader.shutdownInput(); // Schließt Eingabe
                tcpWriter.shutdownOutput();

            } else {
                // Falsche Argumente
                System.err.println("Nutzungsgefahr:");
                System.err.println("  Servieren: java Netzkatze -l port");
                System.err.println("  Klientel: java Netzkatze host port");
            }
        } catch (Exception e) {
            System.err.println("Füller in der Netzkatze: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
// server: java -cp target/classes netcat.uni.tcp.Netcat localhost 5555
// client: java -cp target/classes netcat.uni.tcp.Netcat localhost 5555