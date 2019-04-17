package projectTF;

import java.util.*;

public class Vertex {
	
	ArrayList<Edge> edgeList = new ArrayList<Edge>();
	private char name;
	
	Vertex(char name){	
		this.name = name;
	}
	
	public void addEdge(Edge e) {
		edgeList.add(e);
	}
	
	public char getName() {
		return name;
	}
	
	
	public Edge getEdge(char input) {
		Edge hold = null;
		for (int i = 0; i < edgeList.size(); i++) {
			if (edgeList.get(i).input == input) {
				hold = edgeList.get(i);
			}
		}
		return hold;
	}

}
