package echo.tcpsocket;

import inout.TCPReaderWriter;
import tuple.Tuple;
import inout.InputOutput;

import java.util.Scanner;

public class EchoClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8205;

    public static void main(String[] args) throws Exception {
        InputOutput tcpReadWriter = TCPReaderWriter.connectTo(SERVER_HOST, SERVER_PORT).call();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Verbindung zum EchoServer hergestellt. Geben Sie eine Nachricht ein (\"exit\" zum Beenden):");
        String userInput;
        while (!(userInput = scanner.nextLine()).equalsIgnoreCase("exit")) {
            System.out.println("Sende Nachricht: " + userInput);
            tcpReadWriter.print(userInput);
            // Anpassung: Stelle sicher, dass der "Fallback" kein `null` enthält
            String response = tcpReadWriter.readLine()
                .map(tuple -> tuple.fst)
                .getOrElse("");
            System.out.println("Empfangene Antwort: " + response);
        }

        tcpReadWriter.shutdownInput();
        tcpReadWriter.shutdownOutput();
    }
}