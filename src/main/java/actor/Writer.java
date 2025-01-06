package actor;

import inout.Input;
import inout.InputOutput;
import inout.Output;
import fpinjava.Result;
/*
Was macht der Writer?
Der Writer ist ein Actor, der Nachrichten empfängt und diese an ein Ausgabemedium schreibt.
Zusätzlich erstellt er einen Reader, um die Eingabedaten zu lesen,
und steuert den gesamten Ablauf, indem er den Reader startet und die empfangenen Daten verarbeitet.
 */
public class Writer extends AbstractActor<String> {
    private final Output output;
    private final Reader reader;
    private final boolean isTransceiver;

    public Writer(String id, Input input, Output output) {
        super(id, Actor.Type.SERIAL);
        this.output = output;
        this.reader = new Reader("readerFor" + id, input, this);
        isTransceiver = false;
    }

    public Writer(String id, InputOutput inputOutput) {
        super(id, Actor.Type.SERIAL);
        this.output = inputOutput;
        this.reader = new Reader("readerFor" + id, inputOutput, true, this);
        isTransceiver = true;
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        if ("\u0004".equals(message)) {
            if (isTransceiver) output.printLine(message);
            output.shutdownOutput();
        } else {
            output.printLine(message);
        }
    }

    public void start() {
        reader.tell("", self()); // Den Reader starten
    }

    public void start(Result<Actor<String>> consumer) {
        reader.tell("", consumer);
    }
}
