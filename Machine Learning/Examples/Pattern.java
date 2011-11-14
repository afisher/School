import java.util.*;

public class Pattern {
    private ArrayList<String> lines;

    public Pattern() {
        lines = new ArrayList<String>();
    }

    public Pattern(ArrayList<String> lines) {
        this.lines = lines;
    }

    public void append(String line) {
        lines.add(line);
    }

    public String toString() {
        StringBuilder build = new StringBuilder();
        for (String line: lines) {
            build.append(line + "\n");
        }

        return "" + build;
    }
}
