import java.util.ArrayList;

/**
 *
 * @author Ashley Fisher
 */
public class Pattern {
	private ArrayList<String>  lines;
	private ArrayList<Integer> vals;

	private boolean correct;
	
	public Pattern() {
		lines = new ArrayList<String>();
		vals  = new ArrayList<Integer>();

		correct = false;
	}
	
	public Pattern(String filename) {
		MyReader reader = new MyReader(filename);
		lines = new ArrayList<String>();
		vals  = new ArrayList<Integer>();
		
		while (reader.hasMoreData()) {
			String line = reader.giveMeTheNextLine();
			
			// check to see if the line specifies correctness -- if so, set it
			if (line.equals("yes")) {
				correct = true;
			} else if (line.equals("no")) {
				correct = false;
			} else{
				lines.add(line);
			}
		}
		
		// translate the lines to integer values for computing things later
		for (String line : lines) {
			for(char c : line.toCharArray()) {
				if (c == '*') {
					vals.add(1);
				} else if (c == '-') {
					vals.add(0);
				}
			}
		}
	}
	
	public ArrayList<String> getLines() { return lines; }
	public boolean getCorrect() { return correct; }
	public ArrayList<Integer> getVals() { return vals; }
	
	public void setLines(ArrayList<String> ls) {
		lines = new ArrayList<String>();
		for (String l : ls) {
			lines.add(l);
		}
	}
	
	public String toString() {
		String ret = "";
		for (String l : lines) {
			ret = ret.concat(l).concat("\n");
		}
		return ret;
	}
}
