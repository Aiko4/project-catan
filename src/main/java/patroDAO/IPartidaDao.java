package patroDAO;

import java.util.List;

import classesCatan.Hexagon;
import classesCatan.Partida;

public interface IPartidaDao extends IGenericDao<Partida, Integer>{
	
	int IniciarPartida(List<String> nomsJugadors, int puntsPerGuanyar);
	List<Hexagon> MostrarTauler(int idPartida); // consulta SQL --> NAMED QUERY
	void MostrarPartida(int idPartida); // consulta SQL --> NAMED QUERY
	String TirarDaus(int idPartida);
	void ComprovarVictoria(int idPartida);
}
