package gestioFitxers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import classesCatan.Hexagon;
import classesCatan.HexagonVertex;
import classesCatan.Vertex;
import classesCatan.Aresta;


public class ImportarFitxer {

		public static HashSet importar(String fitxerCVS,HashMap<Integer,Vertex> vertexs, HashMap<Integer, Hexagon> hexagons){

		HashSet<Aresta> arestes = new HashSet();
		HashSet<HexagonVertex> hexagonVertexs = new HashSet();
		
		try (BufferedReader br = new BufferedReader(new FileReader(fitxerCVS))) {

			String linia;
			Integer arrayNombres[] = new Integer[3];
            
            if (fitxerCVS.equals("arestes.cvs")) {
            	
            	 while ((linia = br.readLine()) != null) {
            		 
            		 String dadesLinia[] = linia.split(",");
            		 
            		 for (int i = 0; i < dadesLinia.length; i++) {
            			 arrayNombres[i] = Integer.valueOf(dadesLinia[i]);  // converteix el valor de la posicio dadesLinia[i] a Integer i s'afegeix a l'array
            		 }
            		 
            		// arrayNombres[0] --> id_aresta
 	           		// arrayNombres[1] --> id_vertexA
 	           		// arrayNombres[2] --> id_vertexB
 	           		
 	           		Integer posicioVertexA = arrayNombres[1];
 	           		Integer posicioVertexB = arrayNombres[2];
            		 
            		 
            		 Vertex vertexA = vertexs.get(posicioVertexA);
            		 Vertex vertexB = vertexs.get(posicioVertexB);
            		 
            		 Aresta aresta = new Aresta(arrayNombres[0], vertexA, vertexB);
            		 arestes.add(aresta);               		 
            	 }
            	 
            	 return arestes;
            	
            } else if (fitxerCVS.equals("hexagons_vertex.cvs")) {
            	
            	while ((linia = br.readLine()) != null) {
        	        
            		String dadesLinia[] = linia.split(",");
           		 
	           		for (int i = 0; i < dadesLinia.length; i++) {
	           			 arrayNombres[i] = Integer.valueOf(dadesLinia[i]);  // converteix el valor de la posicio dadesLinia[i] a Integer i s'afegeix a l'array
	           		}
	           		
	           		// arrayNombres[0] --> id_hexagon
	           		// arrayNombres[1] --> id_vertex
	           		// arrayNombres[2] --> id_posicio
	           		
	           		Integer posicioHexagon = arrayNombres[0];
	           		Integer posicioVertex = arrayNombres[1];
	           		
	           		Hexagon hexagon = hexagons.get(posicioHexagon);
	           		Vertex vertex = vertexs.get(posicioVertex);
	           	         		
	           		HexagonVertex hexagonVertex = new HexagonVertex(arrayNombres[2], hexagon, vertex);
	           		hexagonVertexs.add(hexagonVertex);		  		
	           	}
            	
            	return hexagonVertexs;
            	
            	
            } else if (fitxerCVS.equals("hexagons.cvs")) {
            	
            	while ((linia = br.readLine()) != null) {
        	        
            		 String dadesLinia[] = linia.split(",");
            		 
            		 for (int i = 0; i < dadesLinia.length; i++) {
            			 arrayNombres[i] = Integer.valueOf(dadesLinia[i]);  // converteix el valor de la posicio dadesLinia[i] a Integer i s'afegeix a l'array
            		 }
            		 
            		 Hexagon hexagon = new Hexagon(arrayNombres[0], arrayNombres[1], arrayNombres[2]);
            		 hexagons.put(arrayNombres[0], hexagon);      
            	}
				HashSet<Hexagon> setHexagons = new HashSet<>(hexagons.values());
            	
            	return setHexagons;
            	
            } else {
            	
            	while ((linia = br.readLine()) != null) {
            		
	            	 String dadesLinia[] = linia.split(",");
	           		 
	           		 for (int i = 0; i < dadesLinia.length; i++) {
	           			 arrayNombres[i] = Integer.valueOf(dadesLinia[i]);  // converteix el valor de la posicio dadesLinia[i] a Integer i s'afegeix a l'array
	           		 }
	           		 
	           		 // com que de moment no volem dibuixar el tauler, nomes guardem el codi sense afegir les coordenades 
	           		 Vertex vertex = new Vertex(arrayNombres[0]);
	           		 vertexs.put(arrayNombres[0], vertex);  	  	   	           		 
            	}
				HashSet<Vertex> setVertexs = new HashSet<>(vertexs.values());

				return setVertexs;
            }
            
        } catch (IOException e){
        	System.out.print("S'ha produit un error en intentar importar l'arxiu: " + e.getMessage());
        }
		return null;
	}
}
