package classesCatan;

import java.awt.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import enums.ColorJugador;
import gestioFitxers.ImportarFitxer;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import enums.EstatPartida;
import enums.TipusTerreny;

import org.hibernate.collection.spi.PersistentBag;
import patroDAO.PartidaDao;


public class Main {
	
	private static SessionFactory sessionFactory;
	private static Scanner scanner=new Scanner(System.in);;

	// La SessionFactory és cara de crear: només n'hi ha UNA per a tota l'aplicació
	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
			} catch (HibernateException e) {
				System.err.println("Error al crear la SessionFactory: " + e.getMessage());
				throw new ExceptionInInitializerError(e);
			}
		}
		return sessionFactory;
	}
	
	public static void main(String args[]) {
		sessionFactory = getSessionFactory();
		ArrayList <String> nomsJugadors=nombrarJugadors();

		Integer idPartida=iniciarPartida(nomsJugadors);
		System.out.println("Id de la partida: "+idPartida);
		
	}
	//Recull de 2 a 4 noms diferents en un ArrayList
	private static ArrayList<String> nombrarJugadors() {
		ArrayList <String> noms=new ArrayList<>();
		while(noms.size()<2){
			boolean nomValid=false;
			System.out.print("Introdueix el nom del jugador: ");
			while(!nomValid){
				String nom=getString();
				nomValid=true;
				for (String n:noms){
					if (nom.equals(n)){
						nomValid=false;
						System.out.print("Aquest nom ja esta registrat, torna a introduir un nom: ");
					}
				}
				if (nomValid) noms.add(nom);
			}
		}
		System.out.print("Vols afegir més jugadors? (S/N): ");
		boolean addJugadorSN=getBoolean();
		while (addJugadorSN && noms.size()<4){
			boolean nomValid=false;
			System.out.print("Introdueix el nom del jugador: ");
			while(!nomValid){
				String nom=getString();
				nomValid=true;
				for (String n:noms){
					if (nom.equals(n)){
						nomValid=false;
						System.out.print("Aquest nom ja esta registrat, torna a introduir un nom: ");
					}
				}
				if (nomValid) noms.add(nom);
			}
			if(noms.size()<4){
				System.out.print("Vols afegir més jugadors? (S/N): ");
				addJugadorSN=getBoolean();
			}


		}

		return noms;
	}

	//======================================
	//          Creació de partida
	//======================================
	public static int iniciarPartida(List<String> nomsJugadors) {
		System.out.println("** CREANT PARTIDA... **");
		EstatPartida estat=EstatPartida.PREPARACIO;
		LocalDate dataInici= LocalDate.now();
		int ronda=0;
		int ultimaTirada=0;
		System.out.print("Introdueix la quantitat de punts per guanyar: ");
		int puntsPerGuanyar=getInt(1,100);
		boolean dausTirats=false;
		boolean lladrePendent=false;
		Partida partida = new Partida(estat,dataInici,ronda,ultimaTirada,puntsPerGuanyar,dausTirats,lladrePendent);
		//assignar taulell a la partida
		crearTaulell(partida);

		//Barrejar noms, assignar colors i ordre
		Collections.shuffle(nomsJugadors);
		int ordreJugador=0;
		for (String nom:nomsJugadors){
			ColorJugador color = ColorJugador.values()[ordreJugador];
			ordreJugador++;
			Jugador jugador=new Jugador(nom,color,ordreJugador);
			jugador.setPartida_id(partida);
			partida.getJugadors().add(jugador);
		}
		
		
		System.out.println("** PARTIDA CREADA **\n");

		//INSERT de partida a la BBDD
		PartidaDao partidaDao=new PartidaDao();
		partida = partidaDao.saveOrUpdate(partida);
		return partida.getId();
	}




	private static void crearTaulell(Partida partida) {
		HashMap<Integer,Vertex> mapaVertex=new HashMap<>();
		HashMap<Integer,Hexagon> mapaHexagons=new HashMap<>();
		partida.setVertexs(ImportarFitxer.importar("/dades/vertexs.csv",mapaVertex,mapaHexagons));
		partida.setHexagons(ImportarFitxer.importar("/dades/hexagons.csv",mapaVertex,mapaHexagons));
		HashSet <HexagonVertex> hexagonsVertex=ImportarFitxer.importar("/dades/hexagon_vertex.csv",mapaVertex,mapaHexagons);
		partida.setArestes(ImportarFitxer.importar("/dades/arestes.csv",mapaVertex,mapaHexagons));

		ArrayList<Integer> numerosHexagons = new ArrayList<>(List.of(2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12));
		ArrayList<TipusTerreny> terrenyHexagon = new ArrayList<>(List.of(TipusTerreny.BOSC, TipusTerreny.BOSC, TipusTerreny.BOSC, TipusTerreny.BOSC,
				TipusTerreny.PASTURES, TipusTerreny.PASTURES, TipusTerreny.PASTURES, TipusTerreny.PASTURES,
				TipusTerreny.CAMPS, TipusTerreny.CAMPS, TipusTerreny.CAMPS, TipusTerreny.CAMPS,
				TipusTerreny.TURONS, TipusTerreny.TURONS, TipusTerreny.TURONS,
				TipusTerreny.MUNTANYES, TipusTerreny.MUNTANYES, TipusTerreny.MUNTANYES,
				TipusTerreny.DESERT));

		Collections.shuffle(numerosHexagons);
		Collections.shuffle(terrenyHexagon);
		int hex = 0;
		int num = 0;
		for (Hexagon h : partida.getHexagons()) {
		    h.setPartida_id(partida);
		    TipusTerreny terreny = terrenyHexagon.get(hex);
		    h.setTerreny(terreny);
		    if (terreny == TipusTerreny.DESERT) {
		    	partida.setLladre(h);
		        h.setNumero(null);
		    } else {
		        h.setNumero(numerosHexagons.get(num));
		        num++;
		    }
		    hex++;
		    
		    for(HexagonVertex hv : hexagonsVertex) {
		    	if (hv.getHexagon().getCodi() == h.getCodi()) {
		    		h.getVertexsHexagon().add(hv);
		    		hv.setHexagon(h);
		    	}
		    }
		}
		for (Vertex v:partida.getVertexs()){
			v.setPartida_id(partida);
			for(HexagonVertex hv : hexagonsVertex) {
		    	if (hv.getVertex().getCodi() == v.getCodi()) {
		    		v.getHexagonVertex().add(hv);
		    		hv.setVertex(v);
		    	}
		    }
		}
		for (Aresta a:partida.getArestes()){
			a.setPartida_id(partida);
		}
		
		System.out.println("HexagonVertex: " + hexagonsVertex.size());
		System.out.println("HexagonVertex: " + partida.getVertexs());
	}
	
	

	//============================
	//          Scanners
	//============================
	public static int getInt() {
		int i=0;
		while(true) {
			try {
				i=Integer.parseInt(scanner.nextLine());
				return i;
			}catch(NumberFormatException e){
				System.out.print("Valor invàlid, torna a introduir un nombre: ");
			}
		}
	}

	public static int getInt(int min,int max) {
		int i=0;
		while(true) {
			try {
				i=Integer.parseInt(scanner.nextLine());
				if(i<min || i>max) {
					System.out.print("Valor invàlid, torna a introduir un nombre: ");
				}else {
					return i;
				}
			}catch(NumberFormatException e){
				System.out.print("Valor invàlid, torna a introduir un nombre: ");
			}
		}
	}
	
	public static double getDouble() {
		double d=0;
		while(true) {
			try {
				d=Double.parseDouble(scanner.nextLine());
				return d;
			}catch(NumberFormatException e){
				System.out.print("Valor invàlid, torna a introduir un nombre: ");
			}
		}
	}

	public static double getDouble(double min,double max) {
		double i=0;
		while(true) {
			try {
				i=Double.parseDouble(scanner.nextLine());
				if(i<min || i>max) {
					System.out.print("Valor invàlid, torna a introduir un nombre: ");
				}else {
					return i;
				}
			}catch(NumberFormatException e){
				System.out.print("Valor invàlid, torna a introduir un nombre: ");
			}
		}
	}

	public static boolean getBoolean(){
		String inpt;
		while(true){
			inpt=scanner.nextLine().toUpperCase();
			if(inpt.equals("SI") || inpt.equals("SÍ") || inpt.equals("S") || inpt.equals("YES") ||inpt.equals("Y") || inpt.equals("TRUE")){
				return true;
			} else if (inpt.equals("NO") || inpt.equals("N") || inpt.equals("FALSE")){
				return false;
			}else{
				System.out.print("Valor invàlid, torna a introduir una opció (S/N): ");
			}
		}

	}

	public static String getString() {
		String s="";
		while(true) {
			s=scanner.nextLine();
			if(s.equals("")){
				System.out.print("Valor invàlid, torna a intentar: ");
			}else{
				return s;
			}
		}
	}
}