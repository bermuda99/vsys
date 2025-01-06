package daytime.tcp;

import fpinjava.Result;
import inout.Input;
import inout.TCPReader;
import tuple.Tuple;

import java.util.concurrent.Callable;

public class DaytimeClient {
    public static void main(String[] args) {
        String remoteHost = "localhost";
        int port = 8205;

        try {
            Callable<Input> callable = TCPReader.connectTo(remoteHost, port);
            Input input = callable.call();

            Result<Tuple<String, Input>> result = input.readLine();
            result.forEach(line -> System.out.println("Nachricht vom Server: " + line.fst));

            input.shutdownInput();

        } catch (Exception e) {
            System.err.println("Fehler im DaytimeClient: " + e.getMessage());
        }
    }
}
