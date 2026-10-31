package classesCatan;

import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import enums.ColorJugador;
import gestioFitxers.ImportarFitxer;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import enums.EstatPartida;


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
		Partida p=crearPartida();
		//Falta controlar quants jugadors es volen afegir i que no sobrepassin el límit de 5
		afegirJugador(p);
		
	}
	
	//======================================
	//          Creació de partida
	//======================================
	public static Partida crearPartida() {
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
		crearTaulell(partida);
		System.out.println("** PARTIDA CREADAD **");
		return partida;
	}

	public static void afegirJugador(Partida partida){
		System.out.println("** CREANT JUGADOR... **");
		System.out.print("Introdueix el nom del jugador: ");
		String nom=getString();
		ColorJugador colorJugador=triarcolor(partida);
		Jugador jugador=new Jugador(nom,colorJugador,partida.getJugadors().size()+1);
		partida.getJugadors().add(jugador);
		System.out.println("** JUGADOR AFEGIT A LA PARTIDA **");
	}
	public static ColorJugador triarcolor(Partida partida){
		HashSet<Jugador> jugadors=partida.getJugadors();
		HashSet<ColorJugador> colorsDisponibles =new HashSet();
		ColorJugador colorTriat=null;
		for (ColorJugador color : ColorJugador.values()) {
			colorsDisponibles.add(color);
		}
		if(colorsDisponibles.size()>1){
			for(Jugador jugador:jugadors){
				colorsDisponibles.remove(jugador.getColorJugador());
			}
			int cont=0;
			System.out.println("Colors disponibles:");
			for(ColorJugador color:colorsDisponibles){
				cont++;
				System.out.println("   "+cont+". "+color);
			}
			System.out.print("Selecciona un color: ");
			int posicioTria=getInt(1,colorsDisponibles.size())-1;
			colorTriat= (ColorJugador) colorsDisponibles.toArray()[posicioTria];

		} else if (colorsDisponibles.size()==1) {
			colorTriat= (ColorJugador) colorsDisponibles.toArray()[0];
		}
		return colorTriat;
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
				if(i<=min || i>=max) {
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
				if(i<=min || i>=max) {
					System.out.print("Valor invàlid, torna a introduir un nombre: ");
				}else {
					return i;
				}
			}catch(NumberFormatException e){
				System.out.print("Valor invàlid, torna a introduir un nombre: ");
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