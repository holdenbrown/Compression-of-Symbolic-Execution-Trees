import java.util.ArrayList;
import java.util.HashMap;


//Node Class Definitions
public class Node {
	private Edge low;
	private Edge high;
	private int nodeNumber;
	private String comp;
	
	public Node(String string) {
		String[] params = string.split(";");
		nodeNumber = Integer.parseInt(params[0]);
		comp = params[1];
		if (params.length == 2) {
			high = null;
			low = null;
		}
		else {
			high = new Edge(params[2]);
			low = new Edge(params[3]);
		}
	}

	
	public String[] evaluateNode(HashMap<String, String> vL){
		String[] directions = Variable.comparison(vL, comp);
		comp = directions[1];
		return directions;
	}
	
	public String toString() {
		String lowString = "";
		String highString = "";
		if (low != null) {
			lowString = low.toString();
			highString = high.toString();
		}
		return Integer.toString(nodeNumber) + ";" + comp + ";" + lowString + ";" + highString;
	}
	
	public Edge getLow() {return low;}
	public Edge getHigh() {return high;}
	public int getNumber() {return nodeNumber;}
	public String getComp() {return comp;}
	public void setComp(String comp) {this.comp = comp;}
	
}
