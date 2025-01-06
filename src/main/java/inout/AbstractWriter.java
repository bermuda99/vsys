package inout;

import java.io.PrintWriter;

public class AbstractWriter implements Output {
    protected final PrintWriter writer;
    public AbstractWriter(PrintWriter writer) {
        this.writer = writer;
    }
    @Override
    public void print(String s) {
        writer.print(s);
        writer.flush(); // den buffer leeren um sofortige Ausgabe zu erhalten
    }

    @Override
    public void printLine(String s) {
        writer.println(s);
        writer.flush();
    }

    @Override
    public void shutdownOutput() {
        writer.close();
    }
}
