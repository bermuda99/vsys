package daytime.actor;

import actor.AbstractActor;
import actor.Actor;
import fpinjava.Result;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DaytimeActor extends AbstractActor<String> {

    public DaytimeActor(String id) {
        super(id, Type.PARALLEL);
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        // Aktuelle Systemzeit formatieren
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Zeit an den Absender zurücksenden, falls vorhanden
        sender.forEach(s -> s.tell("Current time: " + currentTime, self()));
    }

    public static DaytimeActor daytimeActor(String id) {
        return new DaytimeActor(id);
    }
}
