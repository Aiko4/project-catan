package classesCatan;

import java.util.HashSet;
import java.util.Objects;

import jakarta.persistence.*;


@Entity
@Table(name = "vertex")
public class Vertex {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique=true)
	private int id; // no hauria de ser Integer? o es int perque pot ser null?
	
	@Column(name = "codi", nullable = false, unique = true)
	private int codi; // no hauria de ser Integer? o es int perque pot ser null?
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "partida_id")
	private Partida partida_id;
	
	@OneToMany(mappedBy="vertexA", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private HashSet<Aresta> arestesA;
	
	@OneToMany(mappedBy="vertexB", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private HashSet<Aresta> arestesB;

	@OneToMany(mappedBy = "vertex")
	private HashSet<HexagonVertex> hexagonVertex;
	
	@OneToOne(mappedBy = "vertex")
	private Assentament assentament;
	
	// constructor hibernate
	public Vertex() {
	}
	
	// constructor import i DAO
	public Vertex (int codi) { // l'id es autogenerat, no s'ha de ficar al constructor
		this.codi = codi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCodi() {
		return codi;
	}

	public void setCodi(int codi) {
		this.codi = codi;
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
