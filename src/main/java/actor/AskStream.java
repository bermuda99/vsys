package actor;

import fpinjava.Result;
import stream.Stream;
import tuple.Tuple;
import inout.Input;

public class AskStream {

    public static Stream<String> ask(Actor<String> actor, String message, long timeout) {
        ActorReader tempReader = ActorReader.actorReader("tempReader", timeout);
        actor.tell(message, Result.success(tempReader));
        return Stream.unfold(tempReader, input -> {
            Result<Tuple<String, Input>> result = input.readLine();
            return result.map(t -> Tuple.tuple(t.fst, input));
        });
    }
}