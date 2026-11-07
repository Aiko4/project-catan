package patroDAO;

import org.hibernate.Session;

import classesCatan.Carretera;
import classesCatan.Ciutat;
import classesCatan.Jugador;
import classesCatan.Poblat;
import enums.TipusRecurs;

public class JugadorDao extends GenericDao<Jugador, Integer> implements IJugadorDao{

	@Override
	public Carretera ConstruirCarretera(int idJugador, int idAresta) {
		Session session = sessionFactory.getCurrentSession();
		Carretera carretera = null;
		try {
			session.beginTransaction();
			carretera = ConstruirCarretera(session, idJugador, idAresta);
			session.getTransaction().commit();	
		} catch (RuntimeException e) {
			handleException(session, e);
		}
		return carretera;
	}
	
	
	private Carretera ConstruirCarretera(Session session, int idJugador, int idAresta) {
	
		return null;
	}

	@Override
	public Poblat ConstruirPoblat(int idJugador, int idVertex) {
		Session session = sessionFactory.getCurrentSession();
		Poblat poblat = null;
		try {
			session.beginTransaction();
			poblat = ConstruirPoblat(session, idJugador, idVertex);
			session.getTransaction().commit();	
		} catch (RuntimeException e) {
			handleException(session, e);
		}
		return poblat;
	}
	
	private Poblat ConstruirPoblat(Session session, int idJugador, int idVertex) {
		
		return null;
	}

	@Override
	public Ciutat MillorarACiutat(int idJugador, int idVertex) {
		Session session = sessionFactory.getCurrentSession();
		Ciutat ciutat = null;
		try {
			session.beginTransaction();
			ciutat = MillorarACiutat(session, idJugador, idVertex);
			session.getTransaction().commit();	
		} catch (RuntimeException e) {
			handleException(session, e);
		}
		return ciutat;
	}
	
	private Ciutat MillorarACiutat(Session session, int idJugador, int idVertex) {
		
		return null;
	}

	@Override
	public void ComerciarBanca(int idJugador, TipusRecurs dona, TipusRecurs rep) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			ComerciarBanca(session, idJugador,  dona,  rep);
			session.getTransaction().commit();	
		} catch (RuntimeException e) {
			handleException(session, e);
		}		
	}
	
	private void ComerciarBanca(Session session, int idJugador, TipusRecurs dona, TipusRecurs rep) {
		
		
	}

	@Override
	public void PassarTorn(int idJugador) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			PassarTorn(session, idJugador);
			session.getTransaction().commit();	
		} catch (RuntimeException e) {
			handleException(session, e);
		}		
	}
	
	private void PassarTorn(Session session, int idJugador) {
		
		
	}

}
