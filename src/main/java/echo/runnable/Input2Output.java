package echo.runnable;

import inout.ConsoleReader;
import inout.ConsoleWriter;
import inout.Input;
import inout.Output;
public class Input2Output {

    public static Runnable input2output(Input in, Output out) {
        return () -> {
            in.readLines().forEach(out::printLine);
        };
    }

    public static void main(String[] args) {
        Input inp = ConsoleReader.stdin();
        Output outp = ConsoleWriter.stdout();
        Input2Output.input2output(inp, outp).run();
    }
}
