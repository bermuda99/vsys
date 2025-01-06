package inout;

import fpinjava.Result;
import stream.Stream;
import tuple.Tuple;
import java.io.*;
// ProcessReaderWriter class
public class ProcessReaderWriter implements InputOutput {
    private final AbstractReader reader;
    private final AbstractWriter writer;

    private ProcessReaderWriter(AbstractReader reader, AbstractWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static InputOutput processReaderWriter(Process process) {
        AbstractReader reader = (AbstractReader) ProcessReader.processReader(process);
        AbstractWriter writer = (AbstractWriter) ProcessWriter.processWriter(process);
        return new ProcessReaderWriter(reader, writer);
    }

    @Override
    public Result<Tuple<String, Input>> readLine() {
        return reader.readLine();
    }

    @Override
    public Result<Tuple<Integer, Input>> readInt() {
        return InputOutput.super.readInt();
    }

    @Override
    public Stream<String> readLines() {
        return InputOutput.super.readLines();
    }

    @Override
    public Stream<Integer> readInts() {
        return InputOutput.super.readInts();
    }

    @Override
    public void print(String s) {
        writer.print(s);
    }

    @Override
    public void printLine(String s) {
        writer.printLine(s);
    }

    @Override
    public void shutdownOutput() {
        writer.shutdownOutput();
    }

    @Override
    public void shutdownInput() {
        reader.shutdownInput();
    }

    @Override
    public void close() {
        InputOutput.super.close();
    }
}