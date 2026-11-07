package gestioFitxers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import classesCatan.Hexagon;
import classesCatan.HexagonVertex;
import classesCatan.Vertex;
import classesCatan.Aresta;



public class ImportarFitxer {
	
	private static final String CARPETA_CSV = "/dades/";
	
	public static HashSet importar(String fitxerCVS, HashMap<Integer, Vertex> vertexs, HashMap<Integer, Hexagon> hexagons) {
		HashSet<Aresta> arestes = new HashSet<>();
		HashSet<HexagonVertex> hexagonVertexs = new HashSet<>();

		InputStream is = ImportarFitxer.class.getResourceAsStream(CARPETA_CSV + fitxerCVS);
		if (is == null) {
			System.out.println("No s'ha trobat el fitxer: " + CARPETA_CSV + fitxerCVS);
			return null;
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			String linia;
			//Salta la capcelera del CSV per evitar errors
			linia=br.readLine();
			Integer[] arrayNombres = new Integer[3];
			int nombreLlegit;

			if (fitxerCVS.contains("arestes.csv")) {
				while ((linia = br.readLine()) != null) {
					String[] dadesLinia = linia.split(",");
					for (int i = 0; i < dadesLinia.length; i++) {
						arrayNombres[i] = Integer.valueOf(dadesLinia[i].trim());
					}
					Vertex vertexA = vertexs.get(arrayNombres[1]);
					Vertex vertexB = vertexs.get(arrayNombres[2]);
					Aresta aresta = new Aresta(arrayNombres[0], vertexA, vertexB);
					arestes.add(aresta);
				}
				return arestes;

			} else if (fitxerCVS.contains("hexagon_vertex.csv")) {
				while ((linia = br.readLine()) != null) {
					String[] dadesLinia = linia.split(",");
					for (int i = 0; i < dadesLinia.length; i++) {
						arrayNombres[i] = Integer.valueOf(dadesLinia[i].trim());
					}
					Hexagon hexagon = hexagons.get(arrayNombres[0]);
					Vertex vertex = vertexs.get(arrayNombres[1]);
					HexagonVertex hv = new HexagonVertex(arrayNombres[2], hexagon, vertex);
					hexagonVertexs.add(hv);
				}
				return hexagonVertexs;

			} else if (fitxerCVS.contains("hexagons.csv")) {
				while ((linia = br.readLine()) != null) {
					String[] dadesLinia = linia.split(",");
					for (int i = 0; i < dadesLinia.length; i++) {
						arrayNombres[i] = Integer.valueOf(dadesLinia[i].trim());
					}
					Hexagon hexagon = new Hexagon(arrayNombres[0], arrayNombres[1], arrayNombres[2]);
					hexagons.put(arrayNombres[0], hexagon);
				}
				return new HashSet<>(hexagons.values());

			} else if (fitxerCVS.contains("vertexs.csv")) {
				while ((linia = br.readLine()) != null) {
					String[] dadesLinia = linia.split(",");
					nombreLlegit = Integer.valueOf(dadesLinia[0].trim());

					Vertex vertex = new Vertex(nombreLlegit);
					vertexs.put(nombreLlegit, vertex);
				}
				return new HashSet<>(vertexs.values());

			} else {
				System.out.println("Tipus de fitxer desconegut: " + fitxerCVS);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}}
