package inout;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

/*
accept öffnet einen ServerSocket auf einem Port.
wartet auf eingehende Verbindungen.
erzeugt einen TCPWriter, der die Daten schreiben kann.
connectTo stellt eine Verbindung zu einem Server her.
shutdownOutput schließt den Output des Sockets.

 */
public class TCPWriter extends AbstractWriter implements Output {
    private final Socket socket;

    TCPWriter(Socket socket) throws IOException {
        super(new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true));
        this.socket = socket;
    }

    @Override
    public void shutdownOutput() {
        try {
            socket.shutdownOutput();
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Schließen des Outputs: " + e.getMessage());
        }
    }

    public static Callable<Output> accept(int localPort) {
        return () -> {
            try (ServerSocket serverSocket = new ServerSocket(localPort)) {
                Socket socket = serverSocket.accept();
                socket.shutdownInput();
                return new TCPWriter(socket);
            }
        };
    }

    public static Callable<Output> connectTo(String remoteHost, int remotePort) {
        return () -> {
            Socket socket = new Socket(remoteHost, remotePort);
            socket.shutdownInput();
            return new TCPWriter(socket);
        };
    }
}
