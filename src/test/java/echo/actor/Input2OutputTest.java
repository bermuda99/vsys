package echo.actor;

import echo.runnable.Input2Output;
import inout.ScriptReader;
import inout.ScriptWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Input2OutputTest {

    @ParameterizedTest
    @CsvSource({"Hallo", "HalloHallo"})
    public void test(String inputString) throws InterruptedException {
        ScriptReader scriptReader = new ScriptReader(inputString);
        ScriptWriter scriptWriter = new ScriptWriter();
        Input2Output.input2output(scriptReader, scriptWriter).run();
        Thread.sleep(100);
        
        assertEquals(scriptReader.toList(), scriptWriter.toList());
    }
}
