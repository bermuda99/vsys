package actor;

import fpinjava.Result;
/*
Synchronized:
wird verwendet, um sicherzustellen, dass nur ein Thread zur gleichen Zeit eine synchronized Methode oder einen synchronisierten Block ausführen kann.
--> verhindert Wettlaufbedingungen und sorgt für Thread-Sicherheit.

Tell-Methode:
Die tell-Methode ist als synchronized markiert, da sie evtl. antworten erhalten kann bevor überhaupt eine frage bzw. nachricht rausgegangen ist.


ExecutorService:
Ein ExecutorService steuert die Ausführung von Threads und bietet Methoden zum Verwalten und Überwachen der threads

Daemon-Thread:
Ist ein Hintergrund thread der automatisch beendet wird wenn alle User threads beendet sind.

User-Thread
Ist ein Thread der das Programm am laufen hält solange er aktiv ist.
Wird nicht automatisch beendet.

MessageQueue:
Die MessageQueue ist eine Warteschlange, in der Nachrichten gespeichert werden, die von einem Thread an einen anderen gesendet werden sollen.
 */
public interface Actor<T> {
    static <T> Result<Actor<T>> noSender() {
        return Result.empty();
    }
    Result<Actor<T>> self();
    ActorContext<T> getContext();
    default void tell(T message) {
        tell(message, self());
    }
    void tell(T message, Result<Actor<T>> sender);
    void shutdown();
    default void tell(T message, Actor<T> sender) {
        tell(message, Result.of(sender));
    }
    enum Type {SERIAL, PARALLEL}
}