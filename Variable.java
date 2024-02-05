import java.util.HashMap;

public class Variable{	
		public static String[] comparison(HashMap<String, String> map, String comp) {
			//Separate based on sides
			String[] opps;
			if (comp.contains("<=")) opps = new String[] {">", "<="};
			else if (comp.contains(">=")) opps = new String[] {"<", ">="};
			else if (comp.contains("<")) opps = new String[] {">=", "<"};
			else if (comp.contains("==")) opps = new String[] {"!=", "=="};
			else if (comp.contains("!=")) opps = new String[] {"==", "!="};
			else opps = new String[] {"<=", ">"};
			
			String[] sides = comp.split(opps[1]);
			//Compute variable values
			String left = parseEquation(map, sides[0]);
			int cons = 0;
			String[] leftParsed = left.split("((?=\\+|\\-)|(?<=\\+|\\-))");
			if (leftParsed[leftParsed.length-1].matches("\\d+")) {
				if (leftParsed.length > 1) {
					cons = Integer.parseInt(leftParsed[leftParsed.length-2] + leftParsed[leftParsed.length-1]);
					leftParsed[leftParsed.length-2] = "";
					leftParsed[leftParsed.length-1] = "";
				}else {
					cons = Integer.parseInt(leftParsed[leftParsed.length-1]);
					leftParsed[leftParsed.length-1] = "";
				}
				left = String.join("", leftParsed);
			}
			
			//Parse right-hand equation
			String right = parseEquation(map, sides[1]);
			if (cons > 0)
				right = parseEquation(map, sides[1] + "+" + Integer.toString(-cons));
			else if (cons < 0)
				right = parseEquation(map, sides[1] + Integer.toString(-cons));
			return new String[] {left + opps[0] + right, left + opps[1] + right};
		}
		
		public static String parseEquation(HashMap<String, String> map, String eq) {
			
			//Parse variables into true meanings.
			String[] params = eq.split("((?=\\+|\\-)|(?<=\\+|\\-))");
			for(int p=0; p<params.length; p++) {
				if (map.get(params[p]) != null)
					params[p] = map.get(params[p]);
				
				//check sign
				if ((p>0) && (params[p-1].compareTo("-") == 0)) {
					String[] paramSplit = params[p].split("((?=\\+|\\-)|(?<=\\+|\\-))");
					for(int ps=0; ps<paramSplit.length; ps++) {
						if (ps > 0) {
							if (paramSplit[ps].compareTo("+") == 0) {
								paramSplit[ps] = "-";
								continue;
							}
							if (paramSplit[ps].compareTo("-") == 0) {
								paramSplit[ps] = "+";
								continue;
							}
						}
					}
					params[p] = String.join("", paramSplit);
				}
			}
			eq = String.join("", params);
			
			//Deal with strange signs
			params = eq.split("((?=\\+|\\-)|(?<=\\+|\\-))");
			for(int p=0; p<params.length; p++) {
				if (params[p].compareTo("+") == 0) {
					if (params[p+1].compareTo("+") == 0) { params[p+1] = ""; }
					else if (params[p+1].compareTo("-") == 0) { params[p] = "-"; params[p+1] = ""; }
					else { continue; }
				}
				else if (params[p].compareTo("-") == 0){
					if (params[p+1].compareTo("+") == 0) { params[p+1] = ""; }
					else if (params[p+1].compareTo("-") == 0) {params[p] = "+"; params[p+1] = "";}
					else { continue; }
				}
				else { continue; }
			}
			eq = String.join("", params);
			
			//Deal with multiple constants			
			int cons = 0;
			params = eq.split("((?=\\+|\\-)|(?<=\\+|\\-))");
			for(int p=0; p<params.length; p++) {
				if (params[p].matches("\\d+")){
					if (p == 0) {
						cons += Integer.parseInt(params[p]);
						params[p] = "";
					}else {
						if (params[p-1].compareTo("-") == 0) {
							cons -= Integer.parseInt(params[p]);
						}else {
							cons += Integer.parseInt(params[p]);
						}
						params[p-1] = "";
						params[p] = "";
					}
				}
			}
			
			if (params[0].contains("+")) { 
				params[0] = "";
			}
			
			String returnString = String.join("", params);
			
			//return with constant on end
			if ((cons > 0) && (returnString.length() > 0))
				return returnString + "+" + Integer.toString(cons);
			else if (cons < 0)
				return returnString + Integer.toString(cons);
			else if (returnString.length() > 0)
				return returnString;
			else return Integer.toString(cons);
		}
		
		
	}