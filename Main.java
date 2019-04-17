package projectTF;

import java.io.*;
import java.util.*;


public class Main {

	private static String alphabet;
	private static String states;
	private static Vertex startState;
	private static ArrayList<Vertex> acceptingStates;
	private static ArrayList<Vertex> allStates;
	private static ArrayList<Edge> allEdges;
	Scanner scanner = new Scanner(System.in);


	public static void main(String args[]) {
		
		
		File dfa = importDFA();

		try (BufferedReader br = new BufferedReader(new FileReader(dfa))){
			alphabet = br.readLine();
			alphabet = alphabet.replaceAll("[^a-zA-Z0-9]","");//this removes all symbols and leaves only letters

			String listStates = br.readLine();
			states = listStates.replaceAll("[^a-zA-Z0-9]","");	

			allStates = new ArrayList<Vertex>();
			for (int i = 0; i < states.length(); i++) {
				allStates.add(new Vertex(states.charAt(i)));
			}

			startState = getVertex(br.readLine().charAt(0));

			acceptingStates = new ArrayList<Vertex>();
			String acceptStates = br.readLine();
			acceptStates = acceptStates.replaceAll("[^a-zA-Z0-9]","");
			for(int x = 0; x < acceptStates.length(); x++) {
				acceptingStates.add(getVertex(acceptStates.charAt(x)));
			}

			String line;
			List<String> list = new ArrayList<String>(); //adds all transition states to an array
			
			while((line = br.readLine()) != null){
				list.add(line);
			}
				
				allEdges = new ArrayList<Edge>();
				String[] transitionStates = list.toArray(new String[0]); // transitionStates[i].charAt(1) is state, transitionStates[i].charAt(3) is input, transitionStates[i].charAt(7) is destination
				for (int z = 0; z < transitionStates.length - 1; z++) {
					allEdges.add(new Edge(transitionStates[z].charAt(3), getVertex(transitionStates[z].charAt(1)), getVertex(transitionStates[z].charAt(7))));
				}

		} catch (IOException ioe1){
			System.out.println("This file is invalid or isn't formatted properly.");
		}
		
		Graph graph = new Graph(acceptingStates, startState);
		testStr(graph);
		
	}
	

	public static Vertex getVertex(char c){
		Vertex hold = null;
		for (int i = 0; i < allStates.size(); i++) {
			if (allStates.get(i).getName() == c) {
				hold = allStates.get(i);
			}
		}
		
		return hold;
	}
	
	public static void testStr(Graph graph) {
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Enter your string: ");
	    String userStr = scanner.next();

	    
	    Boolean accept = false;
	    for (int i = 0; i < userStr.length(); i++) {
	    	accept = graph.traverseGraph(userStr.charAt(i));
	    }
	    
	    if (accept == true) {
	    	System.out.println("This string is accepted by the language");
	    }
	    
	    else {
	    	System.out.println("This string is not accepted by the language");
	    }
	}
	
	public static File importDFA(){
		File dfa = new File("/Users/hayesbarber/Desktop/DFA-Test.txt");

		try{
			BufferedReader br = new BufferedReader(new FileReader(dfa));
		} catch (FileNotFoundException fnf1){
			System.out.println("Your file could not be found. Please input the path of your dfa file.");
			Scanner input = new Scanner(System.in);
			dfa = new File(input.next());
		}

		return dfa;
	}

		
}
