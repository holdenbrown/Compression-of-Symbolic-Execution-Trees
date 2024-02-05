import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class SymExTree {
	
	Edge root;
	ArrayList<Node> nodes;
	HashMap<String, String> vars;
	
	
	//Construct AST from file
	public SymExTree(String fileName) {
		nodes = new ArrayList<Node>();
		vars = new HashMap<String, String>();
		
		try {
			File myObj = new File(fileName);
		    Scanner myReader = new Scanner(myObj);
		    
		    //Read input variables
		    String[] readVars = myReader.nextLine().split(",");
		    for(int i=0; i<readVars.length; i++) {
		    	if (!readVars[i].contains("="))
		    		vars.put(readVars[i], readVars[i]);
		    	else {
		    		String[] eqs = readVars[i].split("=");
		    		String varDef = Variable.parseEquation(vars, "+" + eqs[1]);
		    		vars.put(eqs[0], varDef);
		    	}
		    }
		    
		    //Read root
		    root = new Edge(myReader.nextLine());
		    
		    //Read all nodes
		    while (myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		    	if (data.compareTo("") != 0) {
		    		Node addNode = new Node(data);
		    		nodes.add(addNode.getNumber(), addNode);
		    	}
		    }
		    myReader.close();
		} 
		catch (FileNotFoundException e) {
		    System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	
	//Simplify and Evaluate SAT
	public ArrayList<String[]> evaluate(HashMap<String, String> map, Edge edge, int level){
		ArrayList<String[]> myList = new ArrayList<String[]>();
		
		//Clone variable map
		HashMap<String, String> passMap = new HashMap<String, String>();
		passMap = edge.updateVars(map);
		
		if (edge.getTarget(nodes).getComp().contains("return")){
			String ret = "return " + Variable.parseEquation(passMap, edge.getTarget(nodes).getComp().split(" ")[1]);
			edge.getTarget(nodes).setComp(ret);
			myList.add(new String[] {"", "", ret});
		}
		else {
			Node target = edge.getTarget(nodes);
			String[] comps = target.evaluateNode(passMap);
			
			ArrayList<String[]> lowList = evaluate(passMap, target.getLow(), level+1);
			ArrayList<String[]> highList = evaluate(passMap, target.getHigh(), level+1);
		
			
			
			for(String[] element : lowList) {
				myList.add(new String[] {"(" + comps[0] + ")" + element[0], "0" + element[1], element[2]});				
			}
			for(String[] element : highList) {
				myList.add(new String[] {"(" + comps[1] + ")" + element[0], "1" + element[1], element[2]});				
			}
		}
		edge.clearDecs();
		return myList;
	}
	
	public void printTreeToFile(Edge edge, String fileName) {
		String inputVars = "";
		String rootEdge = edge.toString();
		String nodeString = "";
		for(int i=0; i<nodes.size(); i++) {
			Node current = nodes.get(i);
			nodeString += current.toString() + "\n";
			for(char var : current.getComp().toCharArray()) {
				if ((Character.isLetter(var)) && (inputVars.indexOf(var) == -1))
					inputVars += var + ",";
			}
		}
		
		try {
		    FileWriter myObj = new FileWriter(fileName+"_cmpr");
		    myObj.write(inputVars + "\n" + rootEdge + "\n" + nodeString);
		    myObj.close();
		    System.out.println("\nNew file created.");
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}				
	}
	
	public void drawTree(SymExTree tree) {
		TreeDrawer drawer = new TreeDrawer(tree);
		drawer.setVisible(true);
	}
	
	
	public int treeHeight(Edge root) {
		Node target = root.getTarget(nodes);
		if (target.getComp().contains("return"))
			return 0;
		else
			return Integer.max(treeHeight(target.getLow()), treeHeight(target.getHigh()))+1;
	}
	
	public static void main(String[] args) {
	    Scanner myObj = new Scanner(System.in); 
	    System.out.println("Enter SET file: ");
	    String fileName = myObj.nextLine();
	    myObj.close();
	    
	    SymExTree SET = new SymExTree(fileName);
	    SET.drawTree(SET);
	    SET = new SymExTree(fileName);
	    
	    ArrayList<String[]> myList = SET.evaluate(SET.vars, SET.root, 0);
	    System.out.println(SET.treeHeight(SET.root));
	    for(String[] element : myList) {	    	
	    	System.out.print(element[0]);
	    	System.out.print(" ".repeat(30-element[0].length()) + element[2]);
	    	System.out.println(" ".repeat(20-element[2].length()) + element[1]);
	    }
	    
	    SET.printTreeToFile(SET.root, fileName);
	    SET.drawTree(SET);
	}
}


