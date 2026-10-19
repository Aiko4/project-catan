import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "carretera")
public class Carretera extends Construccio {
	
	@Column(name = "aresta", nullable = false)
	private Aresta aresta;
	
	public Carretera() {		
	}
	
	public Carretera(Aresta aresta) {		
		this.aresta = aresta;
	}

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
