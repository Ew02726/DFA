package projectTF;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;


public class Main extends Application {

	private static String alphabet;
	private static String states;
	private static Vertex startState;
	private static ArrayList<Vertex> acceptingStates;
	private static ArrayList<Vertex> allStates;
	private static ArrayList<Edge> allEdges;
	Scanner scanner = new Scanner(System.in);

	public static String userStr;
	public static String strTest;

	public static File selectedFile;
	public static Label stringTestLbl;

	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Theoretical Project");

		Button opendfaBtn = new Button();
		opendfaBtn.setText("Choose File");
		opendfaBtn.setLayoutX(80);

		Button insertStringBtn = new Button();
		insertStringBtn.setText("Test String");
		insertStringBtn.setDisable(true);

		Label stringLbl = new Label();
		stringLbl.setText("Enter your string:");

		FileChooser dfaChooser = new FileChooser();

		TextField stringArea = new TextField();
		stringArea.setMaxHeight(20);
		stringArea.setDisable(true);

		stringTestLbl = new Label();


		opendfaBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectedFile = dfaChooser.showOpenDialog(primaryStage);
				stringArea.setDisable(false);
				insertStringBtn.setDisable(false);
			}
		});

		insertStringBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setString(stringArea.getText());
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
					for (int z = 0; z < transitionStates.length; z++) {
						allEdges.add(new Edge(transitionStates[z].charAt(3), getVertex(transitionStates[z].charAt(1)), getVertex(transitionStates[z].charAt(7))));
					}

				} catch (IOException ioe1){
					System.out.println("This file is invalid or isn't formatted properly.");
				}

				Graph graph = new Graph(acceptingStates, startState);
				testStr(graph);
			}
		});

		VBox vbox = new VBox();
		vbox.getChildren().addAll(opendfaBtn, stringLbl, stringArea, insertStringBtn, stringTestLbl);
		Pane root = new Pane();
		root.getChildren().add(vbox);
		primaryStage.setScene(new Scene(root, 150, 300));
		primaryStage.show();
	}

	public void setString(String str){
		userStr = str;
	}

	public static void main(String args[]) {
		launch(args);
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
