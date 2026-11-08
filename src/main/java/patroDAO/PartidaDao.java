package patroDAO;

import java.time.LocalDate;
import java.util.*;

import org.hibernate.Session;

import classesCatan.*;
import enums.ColorJugador;
import enums.EstatPartida;
import enums.TipusCarta;
import enums.TipusRecurs;
import enums.TipusTerreny;
import gestioFitxers.ImportarFitxer;


public class PartidaDao extends GenericDao<Partida, Integer> implements IPartidaDao {
	
	
	@Override
	public int IniciarPartida(List<String> nomsJugadors, int puntsPerGuanyar) {
		Session session = sessionFactory.getCurrentSession();
		int idPartida;
		try {
			session.beginTransaction();
			idPartida = IniciarPartida(session, nomsJugadors, puntsPerGuanyar);
			if (idPartida == -1) {
				session.getTransaction().rollback();
			} else {
				session.getTransaction().commit();
			}
			return idPartida;
		} catch (RuntimeException e) {
			handleException(session, e);
			return -1;
		}
	}
	
	private int IniciarPartida(Session session, List<String> nomsJugadors, int puntsPerGuanyar) {
			
		EstatPartida estat=EstatPartida.PREPARACIO;
		LocalDate dataInici= LocalDate.now();
		int ronda=0;
		int ultimaTirada=0;
		//System.out.print("Introdueix la quantitat de punts per guanyar: ");
		//int puntsPerGuanyar=getInt(1,100);
		boolean dausTirats=false;
		boolean lladrePendent=false;
		Partida partida = new Partida(estat,dataInici,ronda,ultimaTirada,puntsPerGuanyar,dausTirats,lladrePendent);
		//assignar taulell a la partida
		crearTaulell(partida);

		//Barrejar noms, assignar colors i ordre
		prepararJugadors(nomsJugadors,partida);

		//INSERT de partida a la BBDD
		PartidaDao partidaDao=new PartidaDao();
		session.persist(partida);
		return partida.getId();
	}
	
	
	//============================
	//  AUXILIARS INICIAR PARTIDA
	//============================
	
	private void prepararJugadors(List<String> nomsJugadors, Partida partida) {
		Collections.shuffle(nomsJugadors);
		int ordreJugador=0;
		for (String nom:nomsJugadors){
			ColorJugador color = ColorJugador.values()[ordreJugador];
			ordreJugador++;
			Jugador jugador=new Jugador(nom,color,ordreJugador);
			jugador.setPartida_id(partida);
			partida.getJugadors().add(jugador);
		}
		for (Jugador j:partida.getJugadors()){
			Map<TipusRecurs, RecursJugador> inventari=j.getInventari();
			for(TipusRecurs tr:TipusRecurs.values()){
				RecursJugador rj=new RecursJugador(tr,0);
				rj.setJugador(j);
				inventari.put(tr,rj);
			}
		}
	}
	
	
	private HashSet<HexagonVertex> carregarDades(Partida partida) {
		
		HashMap<Integer, Vertex> mapaVertex=new HashMap<>();
		HashMap<Integer, Hexagon> mapaHexagons=new HashMap<>();
		partida.setVertexs(ImportarFitxer.importar("vertexs.csv",mapaVertex,mapaHexagons));
		partida.setHexagons(ImportarFitxer.importar("hexagons.csv",mapaVertex,mapaHexagons));
		HashSet <HexagonVertex> hexagonsVertex=ImportarFitxer.importar("hexagon_vertex.csv",mapaVertex,mapaHexagons);
		partida.setArestes(ImportarFitxer.importar("arestes.csv",mapaVertex,mapaHexagons));
		
		return hexagonsVertex;
	}
	
	
	private void crearTaulell(Partida partida) {
		
		HashSet<HexagonVertex> hexagonsVertex = carregarDades(partida);
		
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
	}

	// --------------------------------------------------------

	@Override
	public void MostrarPartida(int idPartida) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			MostrarPartida(session, idPartida);
			session.getTransaction().commit();	
		} catch (RuntimeException e) {
			handleException(session, e);
			throw e;
		}		
	}
	
	// metode de @namedQuery
	private void MostrarPartida(Session session, int idPartida) {
		
		
		// informacio de la partida actual (per id)
		Partida p = session.createNamedQuery("Partida.infoPartida", Partida.class)
				.setParameter("id", idPartida)
				.uniqueResult();
		if (p==null) return;
		System.out.println("Partida amb ID: " + idPartida);
		System.out.println("Estat: " + p.getEstatPartida());
		System.out.println("Ronda: " + p.getRonda());

		System.out.println("Id de Jugador actiu: " + (p.getActiu()==null ? "No hi ha cap jugador actiu": p.getActiu().getId()));

		System.out.println("Última tirada: " + ( p.getUltimaTirada()==null ? "No hi han haut tirades encara":p.getUltimaTirada()));
		System.out.println("");



		// per agafar els jugadors no cal consulta
		Set<Jugador> jugadors = p.getJugadors();
		for (Jugador j:jugadors){

			int poblats=0;
			int ciutats=0;
			int carreteres=0;
			List<Construccio> construccions=j.getConstruccionsJugador();
			for (Construccio c:construccions){
				if(c instanceof Poblat) poblats++;
				if (c instanceof Ciutat) ciutats++;
				if (c instanceof Carretera) carreteres++;
			}
			int totalRecursos=0;
			Map<TipusRecurs,RecursJugador> inv=j.getInventari();
			for (RecursJugador rj:inv.values()){
				totalRecursos+=rj.getQuantitat();
			}
			System.out.println("Jugador amb id: "+j.getId());
			//Calcular els punts del jugador (cartes desenvolupament)
			System.out.println("  Punts: ?");
			System.out.println("  Poblats: "+poblats);
			System.out.println("  Ciutats: "+ciutats);
			System.out.println("  Carreteres: "+carreteres);
			System.out.println("  Total de recursos: "+totalRecursos);



		}
	}

	
	
	@Override
	public List<Hexagon> MostrarTauler(int idPartida) {
		Session session = sessionFactory.getCurrentSession();
		List<Hexagon> hexagons = null;
		try {
			session.beginTransaction();
			hexagons = MostrarTauler(session, idPartida);
			session.getTransaction().commit();	
			return hexagons;
		} catch (RuntimeException e) {
			handleException(session, e);
			throw e;
		}	
	}
	
	// metode de @namedQuery
	public List<Hexagon> MostrarTauler(Session session, int idPartida) { 
		
		return session.createNamedQuery("Hexagon.MostrarTauler", Hexagon.class)
				.setParameter("idPartida", idPartida)
				.getResultList();
	}
	
	//----------------------------------------------------------
	@Override
	public String TirarDaus(int idPartida) {
		Session session = sessionFactory.getCurrentSession();
		String resumTirada;
		try {
			session.beginTransaction();
			resumTirada = TirarDaus(session, idPartida);
			session.getTransaction().commit();	
		} catch (RuntimeException e) {
			handleException(session, e);
			return Missatges.ERROR_BD;
		}		
		return resumTirada;
	}
	
	private String TirarDaus(Session session, int idPartida) {
		
		return "";
	}

	//----------------------------------------------------------
	
	@Override
	public void ComprovarVictoria(int idPartida) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			ComprovarVictoria(session, idPartida);
			session.getTransaction().commit();	
		} catch (RuntimeException e) {
			handleException(session, e);
		}			
	}
	
	private void ComprovarVictoria(Session session, int idPartida) {
		Partida partida = session.get(Partida.class, idPartida);
		
		if (partida == null || partida.getEstatPartida() == EstatPartida.FINALITZADA) { 
			return;
		}
		
		for(Jugador j: partida.getJugadors()) {
			int punts = calcularPuntsJugador(j);
			if(punts >= partida.getPuntsPerGuanyar()) {
				partida.setEstatPartida(EstatPartida.FINALITZADA);
				System.out.println("PARTIDA FINALITZADA. El jugador " + j.getNom() + " ha guanyat amb " + punts + " punts.");
				break;
			}
		}
	}
	
	public int calcularPuntsJugador(Jugador j) {
		int punts = 0;
		for (Construccio c : j.getConstruccionsJugador()) {
			if (c instanceof Poblat) punts += 1;
			if (c instanceof Ciutat) punts += 2;
		}
		return punts;
	}
	
	//----------------------------------------------------------
	

	
	
	//============================
	//  	  AUXILIARS
	//============================
	
	private <E> E buscarPerCodi(Session session, Class<E> classe, int codi) {
		return session.createQuery("FROM " + classe.getName() + " e WHERE e.codi = :codi", classe)
				.setParameter("codi", codi)
				.uniqueResult();
	}
	
	private void prepararCartes(Partida partida) {
		List<CartaDesenvolupament> pila = new ArrayList<>();
		
		// Bucle per a les cartes de CAVALLERS
		for(int i = 0; i < 14; i++) {
			CartaDesenvolupament cavallers = new CartaDesenvolupament();
			cavallers.setTipus(TipusCarta.CAVALLER);
			cavallers.setPartida(partida);
			pila.add(cavallers);
		}
		
		// Bucle per a les cartes de PUNT_VICTORIA
		for(int i = 0; i < 5; i++) {
			CartaDesenvolupament puntVictoria = new CartaDesenvolupament();
			puntVictoria.setTipus(TipusCarta.PUNT_VICTORIA);
			puntVictoria.setPartida(partida);
			pila.add(puntVictoria);
		}
		
		// Bucle per a les cartes de MONOPOLI
		for(int i = 0; i < 2; i++) {
			CartaDesenvolupament monopoli = new CartaDesenvolupament();
			monopoli.setTipus(TipusCarta.MONOPOLI);
			monopoli.setPartida(partida);
			pila.add(monopoli);
		}
		
		//Barrejem la pila per a que les compres siguin random
		Collections.shuffle(pila);
		
		partida.setPilaCartes(pila);
	}


}