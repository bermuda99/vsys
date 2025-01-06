package echo.actor;

import actor.AbstractActor;
import actor.Actor;
import fpinjava.Result;

public class EchoActor extends AbstractActor<String> {
// initialisieren des EchoActor
    public EchoActor(String id) {
        super(id, Type.SERIAL);
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        // print message
        sender.forEach(actor -> actor.tell(message, self()));
    }
}
