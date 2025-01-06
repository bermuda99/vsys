package actor;

import fpinjava.Result;
import inout.InputOutput;
import inout.Input;
import tuple.Tuple;

// die actorreaderwriter-klasse kombiniert die funktionen eines readers und eines writers.
// sie implementiert die inputoutput-schnittstelle und ermöglicht sowohl das lesen von nachrichten
// als auch das senden von nachrichten an einen ziel-actor.
public class ActorReaderWriter extends AbstractActor<String> implements InputOutput {
    private final ActorReader actorReader; // ein interner actorreader zum lesen von nachrichten
    private final Actor<String> actorTarget; // der ziel-actor für ausgehende nachrichten

    // konstruktor: initialisiert einen neuen actorreaderwriter mit einer id, einem ziel-actor und einem timeout-wert.
    public ActorReaderWriter(String id, Actor<String> actorTarget, long timeout) {
        super(id, Type.PARALLEL); // initialisiert die basisklasse mit einer parallelen actor-typisierung
        this.actorTarget = actorTarget; // speichert den ziel-actor
        this.actorReader = new ActorReader(id, timeout); // erstellt einen neuen actorreader
    }

    // die methode readLine delegiert das lesen von nachrichten an den internen actorreader.
    @Override
    public Result<Tuple<String, Input>> readLine() {
        return actorReader.readLine(); // ruft readLine vom actorreader auf
    }

    // die methode print sendet eine nachricht an den ziel-actor.
    @Override
    public void print(String message) {
        actorTarget.tell(message, self()); // schickt die nachricht zusammen mit der eigenen referenz
    }

    // die methode printLine sendet eine nachricht mit einem zeilenumbruch an den ziel-actor.
    @Override
    public void printLine(String message) {
        print(message + "\n"); // fügt einen zeilenumbruch hinzu und ruft print auf
    }

    // die methode shutdownInput schließt den internen actorreader.
    @Override
    public void shutdownInput() {
        actorReader.shutdownInput(); // ruft shutdownInput des actorreaders auf
    }

    // die methode shutdownOutput ist leer und könnte bei bedarf implementiert werden.
    @Override
    public void shutdownOutput() {
        // keine zusätzliche logik notwendig
    }

    // die methode onReceive wird aufgerufen, wenn eine nachricht empfangen wird.
    // sie gibt die nachricht auf der konsole aus.
    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        System.out.println("received message: " + message); // nachricht auf die konsole ausgeben
    }

    // die methode actorReaderWriter erstellt und gibt eine neue instanz von actorreaderwriter zurück.
    static ActorReaderWriter actorReaderWriter(String id, Actor<String> actor, long timeout) {
        return new ActorReaderWriter(id, actor, timeout); // gibt eine neue instanz zurück
    }
}
