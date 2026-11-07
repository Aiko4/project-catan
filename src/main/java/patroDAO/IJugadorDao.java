package patroDAO;

import java.util.List;

import classesCatan.*;
import enums.TipusRecurs;

public interface IJugadorDao extends IGenericDao<Jugador, Integer>{
 
	List<RecursJugador> MostrarRecursos(int idJugador); // consulta SQL --> NAMED QUERY
	Carretera ConstruirCarretera(int idJugador, int idAresta);
	Poblat ConstruirPoblat(int idJugador, int idVertex);
	Ciutat MillorarACiutat(int idJugador, int idVertex);
	void ComerciarBanca(int idJugador, TipusRecurs dona, TipusRecurs rep);
	// void CalcularPunts(int idJugador); // consulta SQL --> NAMED QUERY
	void PassarTorn(int idJugador);
}
