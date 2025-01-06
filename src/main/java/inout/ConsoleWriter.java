package inout;
import java.io.PrintWriter;

public class ConsoleWriter extends AbstractWriter {
    public ConsoleWriter(PrintWriter pw) {
        super(pw);
    }
    public static ConsoleWriter stdout() {
        return new ConsoleWriter(new PrintWriter(System.out, true));
    }
}
