package echo.actor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

import actor.Actor;
import stream.Stream;
import actor.AskStream;
import fpinjava.Result;

public  class EchoActorTest {

	@ParameterizedTest
	@CsvSource({"Hello", "EchoTest"})
	public void testEchoActor(String input) {
		// EchoActor initieren
		Actor<String> echoActor = new EchoActor("testActor");

		//  input string an EchoActor senden mit  AskStream und response speichern
		Stream<String> responseStream = AskStream.ask(echoActor, input, 1000);

		// headOption methode um erstes item im stream zu bekommen
		Result<String> response = responseStream.headOption();

		response.forEachOrFail(echo -> assertEquals(input, echo))
				.forEach(Result::failure);
	}
}
