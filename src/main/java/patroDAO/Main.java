package patroDAO;

import java.util.*;

import classesCatan.*;



public class Main {
	
	private static Scanner scanner=new Scanner(System.in);;
	
	public static void main(String args[]) {
		
		PartidaDao pDao = new PartidaDao();
		JugadorDao jDao = new JugadorDao();
		
		try {
			titol("CONFIGURACIO INCIAL DE LA PARTIDA");
			System.out.print("Introdueix la quantitat de punts per guanyar: ");
			int puntsPerGuanyar = getInt(1,100);
			ArrayList <String> nomsJugadors=nombrarJugadors();
			titol("CREANT PARTIDA...");
			Integer idPartida = pDao.IniciarPartida(nomsJugadors, puntsPerGuanyar);
			System.out.println("Id de la partida creada: "+idPartida);	
			titol("MOSTRANT TAULER...");
			mostrarTaulerMain(pDao, idPartida);
			titol("MOSTRANT RECURSOS...");
			mostrarRecursosMain(jDao, 1);
			mostrarRecursosMain(jDao, 2);
			mostrarRecursosMain(jDao, 3);
			mostrarRecursosMain(jDao, 4);
			titol("MOSTRANT PARTIDA...");
			mostrarPartida(pDao,idPartida);
			

		} finally {
			SessionManager.tancar();
		}

	}
	

	private static void mostrarTaulerMain(PartidaDao pDao, int idPartida) {
		List<Hexagon> hexagons = pDao.MostrarTauler(idPartida);

	    for (Hexagon h : hexagons) {
	        System.out.println("Terreny: " + h.getTerreny());
	        System.out.println("Número: " + h.getNumero());

	        for (HexagonVertex hv : h.getVertexsHexagon()) {

	            Vertex v = hv.getVertex();

	            if (v.getAssentament() != null) {
	                System.out.println("Construcció: " + v.getAssentament());
	                System.out.println("Propietari: " +
	                        v.getAssentament().getJugador());
	            }
	        }
			System.out.println("");
	    }
	}
	
	private static void mostrarRecursosMain(JugadorDao jDao, int idJugador) {
		if(jDao.get(idJugador)==null){
			//Si el jugador no existeix no fa res
			return;
		}
		
		List<RecursJugador> recursosJugador = jDao.MostrarRecursos(idJugador);
		System.out.println("Jugador amb ID: " + idJugador);
		
		for (RecursJugador rj : recursosJugador) {
			
			System.out.println("  "+rj.getTipus()+": " + rj.getQuantitat());
		}
		
	}

	private static void mostrarPartida(PartidaDao pDao, int idPartida){
		pDao.MostrarPartida(idPartida);
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
	
	private static void titol(String text) {
		System.out.println("\n========== " + text + " ==========");
	}
}