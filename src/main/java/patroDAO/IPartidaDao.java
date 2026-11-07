package patroDAO;

import java.util.List;

import classesCatan.Partida;

public interface IPartidaDao extends IGenericDao<Partida, Integer>{
	
	int IniciarPartida(List<String> nomsJugadors, int puntsPerGuanyar);
	//void MostrarTauler(int idPartida); // consulta SQL --> NAMED QUERY
	void MostrarPartida(int idPartida);
	String TirarDaus(int idPartida);
	void ComprovarVictoria(int idPartida);
}
