package inout;

import fpinjava.Result;
import tuple.Tuple;
import java.io.*;

// ProcessReader class
public class ProcessReader extends AbstractReader {
    private ProcessReader(BufferedReader reader) {
        super(reader);
    }

    public static Input processReader(Process process) {
        return new ProcessReader(new BufferedReader(new InputStreamReader(process.getInputStream())));
    }
}