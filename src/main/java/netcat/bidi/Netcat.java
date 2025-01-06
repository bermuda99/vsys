package netcat.bidi.tcp;

import actor.Actor;
import fpinjava.Result;
import inout.*;

import java.net.ServerSocket;
import java.net.Socket;

public class Netcat {
    public static void main(String[] args) {
        if (!validateArgs(args)) {
            printUsage();
            System.exit(1);
        }

        try {
            if (args[0].equals("-l")) {
                // Server mode
                int localPort = Integer.parseInt(args[1]);
                runServer(localPort);
            } else {
                // Client mode
                String targetServer = args[0];
                int targetPort = Integer.parseInt(args[1]);
                runClient(targetServer, targetPort);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runServer(int localPort) throws Exception {
        ServerSocket serverSocket = new ServerSocket(localPort);
        Socket socket = serverSocket.accept();
        setupBidirectionalCommunication(socket);
        serverSocket.close();
    }

    private static void runClient(String host, int port) throws Exception {
        Socket socket = new Socket(host, port);
        setupBidirectionalCommunication(socket);
    }

    private static void setupBidirectionalCommunication(Socket socket) throws Exception {
        // Create the socket and console I/O
        TCPReaderWriter tcpIO = new TCPReaderWriter(socket);
        ConsoleReader consoleReader = ConsoleReader.stdin();
        ConsoleWriter consoleWriter = ConsoleWriter.stdout();

        // Create the writers
        Writer socketWriter = new Writer(tcpIO, "SocketWriter");
        Writer consoleWriter = new Writer(consoleReader, consoleWriter, "ConsoleWriter");

        // Connect them bidirectionally
        Result<Actor<String>> socketConsumer = socketWriter.start();
        Result<Actor<String>> consoleConsumer = consoleWriter.start(socketConsumer);
        socketWriter.start(consoleConsumer);

        // Wait for both writers to complete
        socketWriter.join();
        consoleWriter.join();

        socket.close();
    }

    private static boolean validateArgs(String[] args) {
        if (args.length != 2) {
            return false;
        }

        if (args[0].equals("-l")) {
            try {
                Integer.parseInt(args[1]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            try {
                Integer.parseInt(args[1]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("Server mode: java Netcat -l <localport>");
        System.err.println("Client mode: java Netcat <targetserver> <targetport>");
    }
}