package classesCatan;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;


@Entity
@Table(name = "vertex")
@NamedQuery(
		name = "Vertex.buscarPerCodi",
		query = "FROM Vertex v WHERE v.codi = :codi"
)
public class Vertex {

	//Atributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "codi", nullable = false, unique = true)
	private Integer codi;

	//Relacions
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "partida_id")
	private Partida partida_id;
	
	@OneToMany(mappedBy="vertexA", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Aresta> arestesA;
	
	@OneToMany(mappedBy="vertexB", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Aresta> arestesB;

	@OneToMany(mappedBy = "vertex", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<HexagonVertex> hexagonVertex = new HashSet<>();
	
	@OneToOne(mappedBy = "vertex", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Assentament assentament;
	
	// constructor hibernate
	public Vertex() {
	}
	
	// constructor import i DAO
	public Vertex (int codi) { // l'id es autogenerat, no s'ha de ficar al constructor
		this.codi = codi;
	}

	//Get Set
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}
	
	public Integer getCodi() {
		return codi;
	}

	public Partida getPartida_id() {
		return partida_id;
	}

	public void setPartida_id(Partida partida_id) {
		this.partida_id = partida_id;
	}

	public Set<Aresta> getArestesA() {
		return arestesA;
	}

	public void setArestesA(Set<Aresta> arestesA) {
		this.arestesA = arestesA;
	}

	public Set<Aresta> getArestesB() {
		return arestesB;
	}

	public void setArestesB(Set<Aresta> arestesB) {
		this.arestesB = arestesB;
	}

	public Set<HexagonVertex> getHexagonVertex() {
		return hexagonVertex;
	}

	public void setHexagonVertex(Set<HexagonVertex> hexagonVertex) {
		this.hexagonVertex = hexagonVertex;
	}

	public Assentament getAssentament() {
		return assentament;
	}

	public void setAssentament(Assentament assentament) {
		this.assentament = assentament;
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
		Vertex other = (Vertex) obj;
		return codi == other.codi && id == other.id;
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + ", codi=" + codi + "]";
	}
}
