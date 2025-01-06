package echo.tcpsocket;

import inout.TCPReaderWriter;
import tuple.Tuple;
import inout.InputOutput;

import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    private static final int SERVER_PORT = 8205;

    public static void main(String[] args) throws Exception {
        System.out.println("EchoServer gestartet.");
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client verbunden: " + clientSocket.getInetAddress());

                InputOutput tcpRW = TCPReaderWriter.accept(clientSocket.getLocalPort()).call();

                tcpRW.readLines().forEach(tcpRW::printLine);

                tcpRW.shutdownInput();
                tcpRW.shutdownOutput();
                System.out.println("Verbindung beendet.");
                clientSocket.close();
            }
        }
    }
}
