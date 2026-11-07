package patroDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import classesCatan.*;
import enums.ColorJugador;
import enums.EstatPartida;
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
		
		// retorna els jugadors amb les seves construccions
		List<Jugador> jugadors = session.createNamedQuery("Jugador.construccionsPerJugador", Jugador.class)
				.setParameter("idPartida", idPartida)
				.getResultList();
		
		
		// per cada jugador es fa recompte de recursos
		for (Jugador j: jugadors) {
			List<Object[]> recursos = session.createNamedQuery("Jugador.numRecursosJugadors", Object[].class)
					.setParameter("id", j.getId())
					.getResultList();
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
		
		
	}
	
	//============================
	//  	  AUXILIARS
	//============================
	
	private <E> E buscarPerCodi(Session session, Class<E> classe, int codi) {
		return session.createQuery("FROM " + classe.getName() + " e WHERE e.codi = :codi", classe)
				.setParameter("codi", codi)
				.uniqueResult();
	}


}