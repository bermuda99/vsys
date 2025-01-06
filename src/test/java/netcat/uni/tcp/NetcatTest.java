package netcat.uni.tcp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetcatTest {

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
        TimeUnit.SECONDS.sleep(2); // Wartezeit, damit der Server bereit ist

        // Start Client
        ProcessBuilder clientBuilder = new ProcessBuilder(
                "java", "-cp", "target/classes", "netcat.uni.tcp.Netcat", "localhost", String.valueOf(port)
        );
        clientBuilder.redirectErrorStream(true);
        Process clientProcess = clientBuilder.start();

        // Nachricht an den Client senden
        try (BufferedWriter clientInput = new BufferedWriter(new OutputStreamWriter(clientProcess.getOutputStream()))) {
            clientInput.write(testMessage);
            clientInput.newLine();
            clientInput.flush();
        }

        // Nachricht vom Server lesen
        String serverOutput;
        try (BufferedReader serverReader = new BufferedReader(new InputStreamReader(serverProcess.getInputStream()))) {
            serverOutput = serverReader.readLine();
        }

        // Prozesse beenden
        clientProcess.waitFor(5, TimeUnit.SECONDS);
        serverProcess.destroy();

        // Überprüfung
        assertEquals(testMessage, serverOutput, "Die Nachricht vom Server stimmt nicht mit der gesendeten Nachricht überein!");
    }
}
