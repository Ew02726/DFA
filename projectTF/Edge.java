package projectTF;

public class Edge {
	char input;
	Vertex startV;
	Vertex endV;
	
	Edge(char input, Vertex startV, Vertex endV){
		this.input = input;
		this.startV = startV;
		this.endV = endV;
		
		startV.addEdge(this);
	}
}
