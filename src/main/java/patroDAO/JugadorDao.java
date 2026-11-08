	package patroDAO;

	import classesCatan.*;

	import java.util.List;

	import enums.EstatPartida;
	import org.hibernate.Session;

	import classesCatan.Carretera;
	import classesCatan.Ciutat;
	import classesCatan.Jugador;
	import classesCatan.Poblat;
	import enums.TipusRecurs;

	public class JugadorDao extends GenericDao<Jugador, Integer> implements IJugadorDao{

		//----------------------------------------------------------
		@Override
		public List<RecursJugador> MostrarRecursos(int idJugador) {
			Session session = sessionFactory.getCurrentSession();
			List<RecursJugador> recursos = null;
			try {
				session.beginTransaction();
				recursos = MostrarRecursos(session, idJugador);
				session.getTransaction().commit();
				return recursos;
			} catch (RuntimeException e) {
				handleException(session, e);
				throw e;
			}
		}

		private List<RecursJugador> MostrarRecursos(Session session, int idJugador) {
			return session.createNamedQuery("Jugador.buscarRecursos", RecursJugador.class)
			.setParameter("id", idJugador)
			.getResultList();
		}

		//----------------------------------------------------------
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
			Jugador j = session.createNamedQuery("Jugador.infoJugador", Jugador.class)
					.setParameter("id", idJugador)
					.uniqueResult();
			if (j==null) return null;

			Aresta a = session.createNamedQuery("Aresta.infoAresta", Aresta.class)
					.setParameter("id", idAresta)
					.uniqueResult();
			if (a==null) return null;

			if(j.getPartida_id().getId()!=a.getPartida_id().getId()){
				System.out.println("L'aresta i el jugador no pertanyen a la mateixa partida");
				return null;
			}

			if (a.getCarretera()!=null) {
				System.out.println("Aquesta aresta ja té una carretera");
				return null;
			}

			// Comprovació del torn del jugador
			if(!j.getPartida_id().getActiu().equals(j)){
				System.out.println("No és el torn del jugador amb id: "+idJugador);
				return null;
			}

			// Comprovació del límit de 15 carreteres per jugador
			Long numCarreteres = session.createNamedQuery("Carretera.numCarreteresJugador", Long.class)
					.setParameter("id", idJugador)
					.uniqueResult();

			if(numCarreteres>=15){
				System.out.println("El jugador ja té el màxim de 15 carreteres");
				return null;
			}

			// Verificació dels assentaments dels vertex que formen l'aresta
			Assentament ava=a.getVertexA().getAssentament();
			Assentament avb=a.getVertexB().getAssentament();

			boolean connectatA = false;
			boolean connectatB = false;

			// Comprovació del vertex A
			if(ava!=null){
				if(ava.getJugador().getId()==j.getId()) connectatA=true;
			}else{
				for(Aresta ar : a.getVertexA().getArestesA()){
					if(ar!=a && ar.getCarretera()!=null && ar.getCarretera().getJugador().getId()==j.getId()){
						connectatA=true;
						break;
					}
				}

				if(!connectatA){
					for(Aresta ar : a.getVertexA().getArestesB()){
						if(ar!=a && ar.getCarretera()!=null && ar.getCarretera().getJugador().getId()==j.getId()){
							connectatA=true;
							break;
						}
					}
				}
			}

			// Comprovació del vertex B
			if(avb!=null){
				if(avb.getJugador().getId()==j.getId()) connectatB=true;
			}else{
				for(Aresta ar : a.getVertexB().getArestesA()){
					if(ar!=a && ar.getCarretera()!=null && ar.getCarretera().getJugador().getId()==j.getId()){
						connectatB=true;
						break;
					}
				}

				if(!connectatB){
					for(Aresta ar : a.getVertexB().getArestesB()){
						if(ar!=a && ar.getCarretera()!=null && ar.getCarretera().getJugador().getId()==j.getId()){
							connectatB=true;
							break;
						}
					}
				}
			}

			// Comprovació de la connectivitat
			if(j.getPartida_id().getEstatPartida()!=EstatPartida.PREPARACIO){
				if(!connectatA && !connectatB){
					System.out.println("L'aresta no està connectada a cap carretera o assentament propi");
					return null;
				}
			}else{
				// Pendent: comprovar que un dels extrems sigui el poblat acabat de col·locar
			}

			// Comprovació dels recursos
			if(j.getPartida_id().getEstatPartida()!=EstatPartida.PREPARACIO){
				RecursJugador fusta = j.getInventari().get(TipusRecurs.FUSTA);
				RecursJugador argila = j.getInventari().get(TipusRecurs.MAO);

				if(fusta==null || argila==null || fusta.getQuantitat()<1 || argila.getQuantitat()<1){
					System.out.println("No tens recursos suficients per construir una carretera");
					return null;
				}

				fusta.setQuantitat(fusta.getQuantitat()-1);
				argila.setQuantitat(argila.getQuantitat()-1);
			}

			// Creació de la carretera
			Carretera c = new Carretera(a);
			c.setJugador(j);
			a.setCarretera(c);

			session.persist(c);

			return c;
		}

		//----------------------------------------------------------
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
			Jugador j = session.createNamedQuery("Jugador.infoJugador", Jugador.class)
					.setParameter("id", idJugador)
					.uniqueResult();
			if (j==null) return null;

			Vertex v = session.createNamedQuery("Vertex.infoVertex", Vertex.class)
					.setParameter("id", idVertex)
					.uniqueResult();
			if (v==null) return null;

			if(j.getPartida_id().getId()!=v.getPartida_id().getId()){
				System.out.println("El vertex i el jugador no pertanyen a la mateixa partida");
				return null;
			}

			// Comprovació del torn del jugador
			if(!j.getPartida_id().getActiu().equals(j)){
				System.out.println("No és el torn del jugador amb id: "+idJugador);
				return null;
			}

			if(v.getAssentament()!=null){
				System.out.println("Aquest vertex ja té un assentament");
				return null;
			}

			// Comprovació de la regla de la distància
			// Pendent de comprovar els assentaments dels vertex adjacents

			// Comprovació de la connexió amb una carretera pròpia
			if(j.getPartida_id().getEstatPartida()!=EstatPartida.PREPARACIO){
				boolean connectat = false;

				for(Aresta ar : v.getArestesA()){
					if(ar.getCarretera()!=null && ar.getCarretera().getJugador().getId()==j.getId()){
						connectat=true;
						break;
					}
				}

				if(!connectat){
					for(Aresta ar : v.getArestesB()){
						if(ar.getCarretera()!=null && ar.getCarretera().getJugador().getId()==j.getId()){
							connectat=true;
							break;
						}
					}
				}

				if(!connectat){
					System.out.println("El vertex no està connectat a cap carretera pròpia");
					return null;
				}
			}

			// Comprovació del límit de 5 poblats
			Long numPoblats = session.createNamedQuery("Poblat.numPoblatsJugador", Long.class)
					.setParameter("id", idJugador)
					.uniqueResult();

			if(numPoblats>=5){
				System.out.println("El jugador ja té el màxim de 5 poblats");
				return null;
			}

			// Comprovació dels recursos
			if(j.getPartida_id().getEstatPartida()!=EstatPartida.PREPARACIO){
				RecursJugador fusta = j.getInventari().get(TipusRecurs.FUSTA);
				RecursJugador argila = j.getInventari().get(TipusRecurs.MAO);
				RecursJugador blat = j.getInventari().get(TipusRecurs.BLAT);
				RecursJugador llana = j.getInventari().get(TipusRecurs.LLANA);

				if(fusta==null || argila==null || blat==null || llana==null ||
						fusta.getQuantitat()<1 || argila.getQuantitat()<1 ||
						blat.getQuantitat()<1 || llana.getQuantitat()<1){
					System.out.println("No tens recursos suficients per construir un poblat");
					return null;
				}

				fusta.setQuantitat(fusta.getQuantitat()-1);
				argila.setQuantitat(argila.getQuantitat()-1);
				blat.setQuantitat(blat.getQuantitat()-1);
				llana.setQuantitat(llana.getQuantitat()-1);
			}

			// Creació del poblat
			Poblat p = new Poblat(v);
			p.setJugador(j);
			v.setAssentament(p);

			session.persist(p);

			// Repartiment dels recursos inicials del segon poblat
			// Pendent d'implementar segons les relacions entre vertex i terrenys

			return p;
		}

		//----------------------------------------------------------
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
			Jugador j = session.createNamedQuery("Jugador.infoJugador", Jugador.class)
					.setParameter("id", idJugador)
					.uniqueResult();
			if (j==null) return null;

			Vertex v = session.createNamedQuery("Vertex.infoVertex", Vertex.class)
					.setParameter("id", idVertex)
					.uniqueResult();
			if (v==null) return null;

			if(j.getPartida_id().getId()!=v.getPartida_id().getId()){
				System.out.println("El vertex i el jugador no pertanyen a la mateixa partida");
				return null;
			}

			// Comprovació del torn del jugador
			if(!j.getPartida_id().getActiu().equals(j)){
				System.out.println("No és el torn del jugador amb id: "+idJugador);
				return null;
			}
			
			if(v.getAssentament()==null || !(v.getAssentament() instanceof Poblat)){
				System.out.println("Aquest vertex no té cap poblat per millorar");
				return null;
			}

			Poblat p = (Poblat) v.getAssentament();

			if(p.getJugador().getId()!=j.getId()){
				System.out.println("Aquest poblat no és teu");
				return null;
			}

			// Comprovació del límit de 4 ciutats
			Long numCiutats = session.createNamedQuery("Ciutat.numCiutatsJugador", Long.class)
					.setParameter("id", idJugador)
					.uniqueResult();

			if(numCiutats>=4){
				System.out.println("El jugador ja té el màxim de 4 ciutats");
				return null;
			}

			// Comprovació dels recursos
			RecursJugador blat = j.getInventari().get(TipusRecurs.BLAT);
			RecursJugador mineral = j.getInventari().get(TipusRecurs.MINERAL);

			if(blat==null || mineral==null || blat.getQuantitat()<2 || mineral.getQuantitat()<3){
				System.out.println("No tens recursos suficients per millorar a ciutat");
				return null;
			}

			blat.setQuantitat(blat.getQuantitat()-2);
			mineral.setQuantitat(mineral.getQuantitat()-3);

			// Creació de la ciutat
			Ciutat c = new Ciutat(v);
			c.setJugador(j);

			v.setAssentament(c);

			session.remove(p);
			session.persist(c);

			return c;
		}

		//----------------------------------------------------------

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
			
			//Comprovacions de seguretat. Validacio dels recursos
			
			if(dona == null || rep == null || dona == rep) {
				return;
			}
			
			Jugador jugador = session.get(Jugador.class, idJugador);
			
			if (jugador == null) { 
				return;
			}
			
			Partida partida = jugador.getPartida_id();
			if (partida == null) { 
				return; 
			}
			
			//Comprovacions de seguretat. Validacio de l'estat de la partida.
			if (partida.getEstatPartida() == EstatPartida.FINALITZADA) { 
				return;
			}
			
			//Comprovacions de seguretat. Validacio de torn i de llançada de daus.
			if (partida.getActiu() == null || partida.getActiu().getId() != idJugador || !partida.isDausTirats()) {
				return;
			}
			
			RecursJugador rjDona = jugador.getInventari().get(dona);
			RecursJugador rjRep = jugador.getInventari().get(rep);
			
			//Comprovacions de seguretat. Validacio dels recursos (si té menys de 4, es cancel·la)
			if(rjDona == null || rjDona.getQuantitat() < 4) {
				return;
			}
			
			//Intercanvi en cas d'èxit.
			rjDona.setQuantitat(rjDona.getQuantitat() - 4);
			if (rjRep == null) {
				rjRep = new RecursJugador(rep, 0);
				rjRep.setJugador(jugador);
				jugador.getInventari().put(rep, rjRep);
			}
			rjRep.setQuantitat(rjRep.getQuantitat() + 1);
		}
			
			
		

		//----------------------------------------------------------

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
			Jugador jugador = session.get(Jugador.class, idJugador);
			
			if (jugador == null) { 
				return;
			}
			
			// Rebutjar si la partida ha finalitzat
			Partida partida = jugador.getPartida_id();
			if (partida == null) { 
				return; 
			}
			
			// Comprovacions de seguretat. Si encara es el torn del jugador
			if (partida.getActiu() == null || partida.getActiu().getId() != idJugador) { 
				return; 
			}
			
			// Comprovacions de seguretat. Si no s'han tirat els daus prèviament.
			if(!partida.isDausTirats()) {
				return;
			}
			
			int seguentTorn = jugador.getOrdreTorn() + 1;
			
			//Quan hagin passat tots els torns, tornem al primer i anem incrementant.
			if(seguentTorn > partida.getJugadors().size()) {
				seguentTorn = 1;
				partida.setRonda(partida.getRonda() + 1);
			}
			
			//Assignar el nou jugador actiu amb un bucle.
			for (Jugador j : partida.getJugadors()) {
				if (j.getOrdreTorn() == seguentTorn) {
					partida.setActiu(j);
					break;
				}
			}
			
			//Reiniciar el daus.
			partida.setDausTirats(false);

			
		}


		//==========================================================
		//                         Utils
		//==========================================================

		private <E> E buscarPerId(Session session, Class<E> classe, int id) {
			return session.createQuery("FROM " + classe.getName() + " e WHERE e.id = :id", classe)
					.setParameter("id", id)
					.uniqueResult();
		}

	}
