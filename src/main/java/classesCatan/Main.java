package classesCatan;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

import enums.ColorJugador;
import gestioFitxers.ImportarFitxer;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import enums.EstatPartida;
import org.hibernate.collection.spi.PersistentBag;


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

		int idPartida=iniciarPartida(nomsJugadors);

		
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
			System.out.print("Vols afegir més jugadors? (S/N): ");
			addJugadorSN=getBoolean();

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
		//Crec que crear la partida s'ha de fer amb dao en comptes de amb constructor
		Partida partida = new Partida(estat,dataInici,ronda,ultimaTirada,puntsPerGuanyar,dausTirats,lladrePendent);
		crearTaulell(partida);
		//Posar jugadors a partida
		System.out.println("** PARTIDA CREADA **\n");

		//INSERT de partida a la BBDD
		return partida.getId();
	}



	private static void crearTaulell(Partida partida) {
		HashMap<Integer,Vertex> mapaVertex=new HashMap<>();
		HashMap<Integer,Hexagon> mapaHexagons=new HashMap<>();
		partida.setVertexs(ImportarFitxer.importar("/dades/vertexs.csv",mapaVertex,mapaHexagons));
		partida.setHexagons(ImportarFitxer.importar("/dades/hexagons.csv",mapaVertex,mapaHexagons));
		HashSet <HexagonVertex> patata=ImportarFitxer.importar("/dades/hexagon_vertex.csv",mapaVertex,mapaHexagons);
		partida.setArestes(ImportarFitxer.importar("/dades/arestes.csv",mapaVertex,mapaHexagons));
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