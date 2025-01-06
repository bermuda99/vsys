package actor;

import fpinjava.Result;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import inout.Input;
import tuple.Tuple;

// die actorreader-klasse implementiert die input-schnittstelle und ermöglicht das lesen von nachrichten
// aus einer warteschlange (queue), wobei sie als paralleler actor fungiert.
public class ActorReader extends AbstractActor<String> implements Input {
    private final BlockingQueue<String> messageQueue; // eine blockierungswarteschlange zum speichern von nachrichten
    private final long timeout; // timeout-wert für das lesen von nachrichten in millisekunden

    // konstruktor: initialisiert einen actorreader mit einer eindeutigen id und einem timeout-wert.
    public ActorReader(String id, long timeout) {
        super(id, Type.PARALLEL); // initialisiert die basisklasse mit einer parallelen actor-typisierung
        this.messageQueue = new ArrayBlockingQueue<>(100); // warteschlange mit einer kapazität von 100 nachrichten
        this.timeout = timeout; // speichert den timeout-wert
    }

    // diese methode wird aufgerufen, wenn der actor eine nachricht empfängt.
    // fügt die nachricht in die warteschlange ein.
    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        try {
            messageQueue.put(message); // nachricht in die warteschlange einfügen (blockiert, falls die warteschlange voll ist)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // wiederherstellung des interrupted-status des threads
        }
    }

    // die methode readLine liest eine nachricht aus der warteschlange mit einem timeout.
    @Override
    public Result<Tuple<String, Input>> readLine() {
        try {
            String message = messageQueue.poll(timeout, java.util.concurrent.TimeUnit.MILLISECONDS); // versucht, eine nachricht zu lesen

            if (message != null) {
                Tuple<String, Input> resultTuple = Tuple.tuple(message, this); // erstellt ein tuple aus der nachricht und dem aktuellen input
                return Result.success(resultTuple); // gibt das ergebnis als success zurück
            } else {
                return Result.failure("timeout on reading msg"); // gibt ein fehlerergebnis zurück, falls kein nachricht gelesen wurde
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // wiederherstellung des interrupted-status
            return Result.failure("msg reading interrupted"); // gibt ein fehlerergebnis bei interruption zurück
        }
    }

    // die methode actorReader erstellt einen neuen actorreader.
    public static ActorReader actorReader(String id, long timeout) {
        return new ActorReader(id, timeout); // gibt einen neuen instance von actorreader zurück
    }

    // shutdownInput wird implementiert, aber hier nicht genutzt.
    @Override
    public void shutdownInput() {
        // keine zusätzliche logik notwendig
    }
}
