package netcat.uni.tcp;

import inout.ProcessReader;
import inout.ProcessWriter;
import inout.Input;
import inout.Output;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tuple.Tuple;
import fpinjava.Result;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NetcatTestProcessReaderWriter {

    @ParameterizedTest
    @ValueSource(strings = {"Hallo Server!", "Testnachricht", "123456"})
    void testNetcatCommunication(String testMessage) throws Exception {
        int port = 5555;

        // Start Server
        ProcessBuilder serverBuilder = new ProcessBuilder(
                "java", "-cp", "target/classes", "netcat.uni.tcp.Netcat", "-l", String.valueOf(port)
        );
        serverBuilder.redirectErrorStream(true);
        Process serverProcess = serverBuilder.start();

        // Sicherstellen, dass der Server läuft
        TimeUnit.SECONDS.sleep(2);

        // Start Client
        ProcessBuilder clientBuilder = new ProcessBuilder(
                "java", "-cp", "target/classes", "netcat.uni.tcp.Netcat", "localhost", String.valueOf(port)
        );
        clientBuilder.redirectErrorStream(true);
        Process clientProcess = clientBuilder.start();

        // ProcessReader und ProcessWriter erstellen
        Input serverReader = ProcessReader.processReader(serverProcess);
        Output clientWriter = ProcessWriter.processWriter(clientProcess);

        // Nachricht an den Client senden
        clientWriter.printLine(testMessage);

        // Nachricht vom Server lesen
        Result<Tuple<String, Input>> result = serverReader.readLine();

        // Sicherstellen dass das Result nicht empty ist
        assertTrue(result.isSuccess(), "Keine Ausgabe vom Server erhalten");

        // Den String aus dem Tuple extrahieren
        String receivedMessage = result.getOrElse(Tuple.of("", serverReader)).fst;

        // Prozesse beenden
        clientProcess.waitFor(5, TimeUnit.SECONDS);
        serverProcess.destroy();

        // Überprüfung
        assertEquals(testMessage, receivedMessage,
                "Die Nachricht vom Server stimmt nicht mit der gesendeten Nachricht überein!");
    }
}