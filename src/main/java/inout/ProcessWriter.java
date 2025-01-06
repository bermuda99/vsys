package inout;

import fpinjava.Result;
import tuple.Tuple;
import java.io.*;
// ProcessWriter class
public class ProcessWriter extends AbstractWriter {
    private ProcessWriter(PrintWriter writer) {
        super(writer);
    }

    public static Output processWriter(Process process) {
        return new ProcessWriter(new PrintWriter(process.getOutputStream(), true));
    }
}