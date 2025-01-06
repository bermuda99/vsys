package inout;

import fpinjava.*;
import tuple.Tuple;
import java.io.BufferedReader;
/*
Aus dem Buch 13.3 FPJ
 */
public class AbstractReader implements Input {
    protected final BufferedReader reader;
    protected AbstractReader(BufferedReader reader) {
        this.reader = reader;
    }
    @Override
    public Result<Tuple<String, Input>> readLine() {
        try {
            String s = reader.readLine();
            return s.isEmpty()
                    ? Result.empty()
                    : Result.success(new Tuple<>(s, this));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }
    @Override
    public Result<Tuple<Integer, Input>> readInt() {
        try {
            String s = reader.readLine();
            return s.isEmpty()
                    ? Result.empty()
                    : Result.success(new Tuple<>(Integer.parseInt(s), this));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

    @Override
    public void shutdownInput() {

    }
}