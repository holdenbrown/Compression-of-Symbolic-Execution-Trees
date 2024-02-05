//Edge Class Definitions
import java.util.HashMap;
import java.util.ArrayList;

public class Edge{
	private int targetNumber;
	private ArrayList<String> decs;	
	
	public Edge(String string) {
		
		if (string.compareTo("") != 0){
			String[] params = string.split(",");
			targetNumber  = Integer.parseInt(params[0]);
			
			decs = new ArrayList<String>();
			for(int i=1; i<params.length; i++) {
				if (params[i].compareTo("") != 0)
					decs.add(params[i]);
			}
		}
	}
	
	public ArrayList<String> getDecs(){
		return decs;
	}
	
	public void clearDecs(){
		decs.clear();
	}
	
	public HashMap<String, String> updateVars(HashMap<String, String> oldMap){
		
		HashMap<String, String> newMap = new HashMap<String, String>();
		newMap.putAll(oldMap);
		
		for(String dec : decs) {
			String[] decSplit = dec.split("=");
			String parseSplit = Variable.parseEquation(newMap, decSplit[1]);
			newMap.put(decSplit[0], parseSplit);
		}
		
		return newMap;
	}
	
	public String toString() {
		return Integer.toString(targetNumber) + "," + String.join(",", decs);
	}
	
	public Node getTarget(ArrayList<Node> nodeList) {
		return nodeList.get(targetNumber);
	}
	
	public int getTargetNum() {
		return targetNumber;
	}
}
