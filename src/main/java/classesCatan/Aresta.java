package classesCatan;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="aresta")
public class Aresta {

	//Atributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="codi_aresta", nullable=false)
	private Integer codi;

	//Relacions
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "partida_id")
	private Partida partida_id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_vertexA")
	private Vertex vertexA;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_vertexB")
	private Vertex vertexB;

	@OneToOne(mappedBy = "aresta")
	private Carretera carretera;

	// constructor hibernate
	public Aresta() {

	}

	// constructor per a importar arestes
	public Aresta(Integer codi, Vertex vertexA,  Vertex vertexB) {
		this.codi = codi;
		this.vertexA = vertexA;
		this.vertexB = vertexB;
	}

	public Aresta(Integer codi) {
		this.codi = codi;
	}

	//Get Set
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodi() {
		return codi;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}

	public Partida getPartida_id() {
		return partida_id;
	}

	public void setPartida_id(Partida partida_id) {
		this.partida_id = partida_id;
	}

	public Vertex getVertexA() {
		return vertexA;
	}

	public void setVertexA(Vertex vertexA) {
		this.vertexA = vertexA;
	}

	public Vertex getVertexB() {
		return vertexB;
	}

	public void setVertexB(Vertex vertexB) {
		this.vertexB = vertexB;
	}

	public Carretera getCarretera() {
		return carretera;
	}

	public void setCarretera(Carretera carretera) {
		this.carretera = carretera;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;

	    Aresta aresta = (Aresta) o;
	    return Objects.equals(codi, aresta.codi);
	}

	@Override
	public int hashCode() {
	    return Objects.hashCode(codi);
	}
	@Override
	public String toString() {
		return "Aresta [id=" + id + ", codi=" + codi + "]";
	}




}