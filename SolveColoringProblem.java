import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
public class SolveColoringProblem {
	private int numOfCountry;
	public Hashtable<String, String> variable; // variabes for each country
	private String[] DomainColor; // possible color
	private ArrayList<String> constraints; // limitation
	private String country; // for setting current country
	SolveColoringProblem() throws IOException{
		variable = new Hashtable<String,String>();
		constraints = new ArrayList<String>();
		DomainColor = new String[] {"Red","Green","Blue","Yellow","Violet","Gray","Orange"};
		country = "none";
		getInfoFromFile();
		for(int i=1; i<7 ; i++) {
			Hashtable<String, String> ans = Backtrack(variable, i);
			if(ans!=null) {
				ans.forEach((k,v)->{
					System.out.println(k + " : "+ v);
				});
				break;
			}
		}
	}

	public void getInfoFromFile() throws IOException{
		// assuming graph.txt file located in the same project folder
		// if not then add the entire path
		final JFileChooser fc = new JFileChooser("Choose your graph file. (.txt)");
		fc.showOpenDialog(fc);
		File file = fc.getSelectedFile();
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		int total_no_vertices = Integer.parseInt(br.readLine());
		numOfCountry = total_no_vertices;
		for (int i = 1; i <= total_no_vertices; ++i) {
		    String line = br.readLine();
		    String[] vertex_and_adjacent = line.split(" ");
		    variable.put(vertex_and_adjacent[0], "none");
		    
		    for (int j = 1; j < vertex_and_adjacent.length; ++j) {
		    	// Assign the vertex vertex_and_adjacent[0] into your data structure
		    	// Assign it's adjacent vertices from vertex_and_adjacent[1] to vertex_and_adjacent[length - 1] into another data structure
		    	if(!constraints.contains(vertex_and_adjacent[j]+" "+vertex_and_adjacent[0])) {
		    		constraints.add(vertex_and_adjacent[0]+" "+vertex_and_adjacent[j]);
		    	}
		    }
		 }
	}
	
	public Hashtable<String, String> Backtrack(Hashtable<String, String> var,int numOfColor) {
		Hashtable<String, String> tempVar = (Hashtable<String, String>) var.clone();
		tempVar.forEach((k,v)->{
			if(v == "none") {
				country = k;
			}
		});
		if(country == "none") {
			return tempVar;
		}
		for(int j=0; j<numOfColor; j++) {
			tempVar.replace(country, DomainColor[j]);
			if(isConsistent(tempVar)) {
				country = "none";
				return Backtrack(tempVar,numOfColor);
			}
		}
		if(country!="none") {
			if(numOfCountry>0) {
				numOfCountry--;
				List KeyList= variable.keySet().stream().collect(Collectors.toCollection(ArrayList::new));
				for(int i=0; i<KeyList.size(); i++) {
					variable.replace((String) KeyList.get(i), "none");
				}
				variable.replace((String) KeyList.get(numOfCountry), DomainColor[0]);
				return Backtrack(variable,numOfColor);
			}else {
				System.out.println("The map needs more than " + numOfColor + " colors!");
				return null;
			}
		}
		return var;
		//System.out.println(variable.get(country));
		
	}
	
	private boolean isConsistent(Hashtable<String, String> var) {
		for(int i=0; i<constraints.size(); i++) { // testing limitation
			String[] constrain = constraints.get(i).split(" ");
			if(var.get(constrain[0])==var.get(constrain[1]) && !(var.get(constrain[0])=="none" || var.get(constrain[1])=="none")) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SolveColoringProblem solver = new SolveColoringProblem();
	}

}
