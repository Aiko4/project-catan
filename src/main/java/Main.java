import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
	public static void main(String args[]) {
		//carregar topologia des de csv
		String arestes = "dades/arestes.csv";
		String hexagons = "dades/hexagons.csv";
		String vertex = "dades/vertex.csv";
		String hexagon_vertex = "dades/hexagon_vertex.csv";
		
		
		ArrayList<String> terrenys= new ArrayList <>(Arrays.asList("MUNTANYA","MUNTANYA","MUNTANYA","TURO","TURO","TURO","BOSC","BOSC","BOSC","BOSC","DESERT","CAMPS","CAMPS","CAMPS","PASTURES","CAMPS","PASTURES","PASTURES","PASTURES"));
		Collections.shuffle(terrenys);
		
		
		//assignar terrenys a hexagons
		
		//assignar números als hexagons
	}
}