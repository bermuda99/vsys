package echo.runnable;

import inout.ScriptReader;
import inout.ScriptWriter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import list.List;

public class Input2OutputTest {

	@ParameterizedTest
	@CsvSource({"'Hallo1\nHallo2'", "'HalloHallo\njaja'"})
	public void test(String inputString) {
		ScriptReader reader = new ScriptReader(inputString);
		ScriptWriter writer = new ScriptWriter();
		Runnable echoRunnable = Input2Output.input2output(reader, writer);
		echoRunnable.run();
		List<String> expectedOutput = List.list(inputString.split("\n"));
		assertEquals(expectedOutput, writer.toList());
	}
}