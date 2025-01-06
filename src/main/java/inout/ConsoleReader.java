package inout;
import tuple.Tuple;
import fpinjava.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleReader extends AbstractReader {
    public ConsoleReader(BufferedReader reader) {
        super(reader);
    }
    public Result<Tuple<String, Input>> readLine(String message) {
        System.out.print(message + " ");
        return readLine();
    }
    public Result<Tuple<Integer, Input>> readInt(String message) {
        System.out.print(message + " ");
        return readInt();
    }
    public static ConsoleReader stdin() {
        return new ConsoleReader(new BufferedReader(
                new InputStreamReader(System.in)));
    }
}
/*
Autoflush ist nicht zwingend notwendig solange
die Ausgabe nicht sofort benötigt wird.
es sei denn man möchte,dass jeder Printbefehl direkt
angezeigt wird, dann wird es benötigt.
 */