package inout;

import list.List;

public class ScriptWriter implements Output {
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void print(String s) {
        sb.append(s);
    }

    @Override
    public void printLine(String s) {
        sb.append(s).append("\n");
    }

    @Override
    public void shutdownOutput() {

    }

    public List<String> toList() {
        return List.list(sb.toString().split("\n"));
    }
}
