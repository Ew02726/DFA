package projectTF;

import java.util.*;

public class Graph {
	
	ArrayList<Vertex> acceptingStates = new ArrayList<Vertex>();
	Vertex startState;
	Vertex currentState;
	
	
	Graph (ArrayList<Vertex> acceptingStates, Vertex startState){
		this.startState = startState;
		currentState = startState;
		this.acceptingStates = acceptingStates;
	}
	
	public boolean traverseGraph(char input) {
		Edge currentEdge = currentState.getEdge(input);
		currentState = currentEdge.endV;

		
		if (acceptingStates.contains(currentState)) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
