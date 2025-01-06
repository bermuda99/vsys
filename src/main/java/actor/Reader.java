package actor;

import inout.Input;
import fpinjava.Result;
/*
Was macht der Reader?
Der Reader ist ein Actor, der Eingabedaten
Zeile für Zeile liest und diese an einen anderen Actor (meist den Writer) weiterleitet.
Am Ende der Eingabe signalisiert er das Ende der Übertragung (EOT) und schließt die Eingabe.
 */
public class Reader extends AbstractActor<String> {
    private final Input input;
    private final boolean isTransceiver;
    private final AbstractActor<String> producer;

    public Reader(String id, Input input, AbstractActor<String> producer) {
        super(id, Actor.Type.SERIAL);
        this.input = input;
        isTransceiver = false;
        this.producer = producer;
    }

    public Reader(String id, Input input, boolean isTrans, AbstractActor<String> producer) {
        super(id, Actor.Type.SERIAL);
        this.input = input;
        isTransceiver = isTrans;
        this.producer = producer;
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
		input.readLines().forEach(mes -> sender.forEach(actor -> actor.tell(mes, producer)));
		if (isTransceiver) sender.forEach(actor -> actor.tell("\u0004", producer));
		input.shutdownInput();
    }

}
