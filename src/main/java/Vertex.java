
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "vertex")
public class Vertex {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique=true)
	private int id;
	
	@Column(name = "codi", nullable = false, unique = true)
	private int codi;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_partida")
	private Integer idPartida;
	
    
	@OneToMany(mappedBy="vertexA", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Aresta> arestesA;
	
	@OneToMany(mappedBy="vertexB", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Aresta> arestesB;

	
	public Vertex() {
	}
	
	public Vertex (int id, int codi) {
		this.id = id;
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
