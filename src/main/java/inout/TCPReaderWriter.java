package inout;

import fpinjava.Callable;
import fpinjava.Result;
import tuple.Tuple;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/*
accept öffnet einen ServerSocket auf einem Port.
wartet auf eingehende Verbindungen.
erzeugt einen TCPReaderWriter, der die Daten lesen und schreiben kann.
connectTo stellt eine Verbindung zu einem Server her.
shutdownInput schließt den Input des Sockets.
 */

public class TCPReaderWriter implements InputOutput {

    private final Socket socket;
    private final AbstractReader reader;
    private final AbstractWriter writer;

    public TCPReaderWriter(Socket socket, AbstractReader reader, AbstractWriter writer) {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public Result<Tuple<String, Input>> readLine() {
        return reader.readLine();
    }

    @Override
    public void print(String s) {
        writer.print(s);
    }

    @Override
    public void shutdownInput() {
        try {
            reader.shutdownInput(); // Schließt den Input des Readers
            socket.close(); // Schließt den Socket
        } catch (IOException e) {
            System.out.println("Error on closing socket: " + e.getMessage());
        }
    }

    @Override
    public void shutdownOutput() {
        try {
            writer.shutdownOutput(); // Schließt den Output des Writers
            socket.close(); // Schließt den Socket
        } catch (IOException e) {
            System.out.println("Error on closing socket: " + e.getMessage());
        }
    }

    public static Callable<InputOutput> accept(int localPort){
        return () -> {
            ServerSocket serverSocket = new ServerSocket(localPort);
            Socket socket = serverSocket.accept();
            return new TCPReaderWriter(socket, new TCPReader(socket), new TCPWriter(socket));
        };
    }

    public static Callable<InputOutput> connectTo(String remoteHost, int remotePort){
        return () -> {
            Socket socket = new Socket(remoteHost, remotePort);
            return new TCPReaderWriter(socket, new TCPReader(socket), new TCPWriter(socket));
        };
    }
}
