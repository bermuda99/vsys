package daytime.actor;

import actor.AskStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import stream.Stream;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class DaytimeActorTest {

	@ParameterizedTest
	@CsvSource({"'TestMessage1'", "'TestMessage2'"})
	public void testDaytimeActorRespondsWithCurrentTime(String message) throws InterruptedException {
	}
}
