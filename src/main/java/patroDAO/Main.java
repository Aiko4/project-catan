package patroDAO;

import java.time.LocalDate;
import java.util.*;
import java.util.List;

import classesCatan.*;
import enums.ColorJugador;
import enums.TipusRecurs;
import gestioFitxers.ImportarFitxer;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mysql.cj.Session;

import enums.EstatPartida;
import enums.TipusTerreny;


public class Main {
	
	private static Scanner scanner=new Scanner(System.in);;
	
	public static void main(String args[]) {
		
		PartidaDao pDao = new PartidaDao();
		
		try {
			titol("CONFIGURACIO INCIAL DE LA PARTIDA");
			System.out.print("Introdueix la quantitat de punts per guanyar: ");
			int puntsPerGuanyar = getInt(1,100);
			ArrayList <String> nomsJugadors=nombrarJugadors();
			titol("CREANT PARTIDA...");
			Integer idPartida = pDao.IniciarPartida(nomsJugadors, puntsPerGuanyar);
			System.out.println("Id de la partida: "+idPartida);	
		} finally {
			SessionManager.tancar();
		}

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
	//          Auxiliars
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