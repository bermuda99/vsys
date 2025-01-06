package inout;
import fpinjava.*;
import tuple.Tuple;
import list.List;


/*
zweiten konstruktor überarbeiten
 */

public class ScriptReader implements Input {
    private final List<String> commands;
    public ScriptReader(List<String> lines) {
        super();
        /*wandelt die Liste in eine Liste von Strings um
        und entfernt alle Leerzeichen
        flatmap sorgt dafür, dass die Liste von Listen
        in eine Liste von Strings umgewandelt wird
         */
        this.commands = lines.flatMap(s -> List.list(s.split("\n")).map(String::trim));
    }
    public ScriptReader(String... commands) {
        super();
        /* Teilt die übergebenen Strings anhand von \n auf,
        entfernt Leerzeichen, und speichert jede Zeile
        als einzelnes Listenelement.
         */
        this.commands = List.list(commands)
                .flatMap(s -> List.list(s.split("\n")).map(String::trim));
    }


/*
HeadOption kann man durch head ersetzten
jedoch ist headOption sicherer, da es einen
Optional zurückgibt, falls die Liste leer ist.
und keine Exception wie bei head wirft, falls die Liste leer ist.
Man muss sicherstellen, dass die Liste niemals leer ist.
 */
    @Override
    public Result<Tuple<String, Input>> readLine() {
        return commands.isEmpty()
                ? Result.failure("Not enough entries in script")
                : Result.success(new Tuple<>(commands.headOption().getOrElse(""),
                new ScriptReader(commands.drop(1))));
    }

    @Override
    public Result<Tuple<Integer, Input>> readInt() {
        try {
            return commands.isEmpty()
                    ? Result.failure("Not enough entries in script")
                    : Integer.parseInt(commands.headOption().getOrElse("")) >= 0
                    ? Result.success(new Tuple<>(Integer.parseInt(
                    commands.headOption().getOrElse("")),
                    new ScriptReader(commands.drop(1))))
                    : Result.empty();
        } catch(Exception e) {
            return Result.failure(e);
        }
    }
    //toList gibt die Liste von Strings zurück
    public List<String> toList() {
        return commands;
    }
    @Override
    public void shutdownInput() {

    }

}