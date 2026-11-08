package classesCatan;
import jakarta.persistence.*;

@Entity
@Table(name = "carretera")
@NamedQuery(
		name = "Carretera.numCarreteresJugador",
		query = "SELECT COUNT(c) FROM Carretera c WHERE c.jugador.id = :id"
)
public class Carretera extends Construccio {


    //Relacions
	@OneToOne
    @JoinColumn(name = "id_aresta", unique = true, nullable = false)
	private Aresta aresta;

	//Constructors
	public Carretera() {		
	}
	
	public Carretera(Aresta aresta) {		
		this.aresta = aresta;
	}

	//Get Set
	public Aresta getAresta() {
		return aresta;
	}

	public void setAresta(Aresta aresta) {
		this.aresta = aresta;
	}

	@Override
	public String toString() {
		return "Carretera [aresta=" + aresta + "]";
	}
	
}
