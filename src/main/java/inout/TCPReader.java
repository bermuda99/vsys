package inout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
/*
Öffnet einen ServerSocket auf einem Port.
Wartet auf eingehende Verbindungen.
Erzeugt einen TCPReader, der die Daten lesen kann.
connectTo() stellt eine Verbindung zu einem Server her.
shutdownInput() schließt den Input des Sockets.
 */
public class TCPReader extends AbstractReader implements Input {
    private final Socket socket;

    TCPReader(Socket socket) throws IOException {
        super(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        this.socket = socket;
    }

    @Override
    public void shutdownInput() {
        try {
            socket.shutdownInput();
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Schließen des Inputs: " + e.getMessage());
        }
    }

    public static Callable<Input> accept(int localPort) {
        return () -> {
            try (ServerSocket serverSocket = new ServerSocket(localPort)) {
                Socket socket = serverSocket.accept();
                socket.shutdownOutput();
                return new TCPReader(socket);
            }
        };
    }

    public static Callable<Input> connectTo(String remoteHost, int remotePort) {
        return () -> {
            Socket socket = new Socket(remoteHost, remotePort);
            socket.shutdownOutput();
            return new TCPReader(socket);
        };
    }
}
