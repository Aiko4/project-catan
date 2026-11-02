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


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_aresta", nullable=false, unique=true)
	private Integer id;

	@NotNull
	@Column(name="codi_aresta", nullable=false)
	private Integer codi;

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
	public int hashCode() {
		return Objects.hash(codi, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aresta other = (Aresta) obj;
		return Objects.equals(codi, other.codi) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Aresta [id=" + id + ", codi=" + codi + "]";
	}




}