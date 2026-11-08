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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="hexagon_vertex")
public class HexagonVertex {

	//Atributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="posicio",nullable=false)
	@Min(0)
	@Max(5)
	private Integer posicio;

	//Relacions
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "hexagon_id", nullable = false)
	private Hexagon hexagon;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vertex_id", nullable = false)
	private Vertex vertex;

    // constructor hibernate
    public HexagonVertex() {
    }
    
    // constructor import
    public HexagonVertex(Integer posicio, Hexagon hexagon, Vertex vertex) {
    	this.posicio = posicio;
    	this.hexagon = hexagon;
    	this.vertex = vertex;
    }

	//Get Set
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPosicio() {
		return posicio;
	}

	public void setPosicio(Integer posicio) {
		this.posicio = posicio;
	}

	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;

	    HexagonVertex that = (HexagonVertex) o;

	    return Objects.equals(hexagon, that.hexagon)
	            && Objects.equals(vertex, that.vertex);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(hexagon, vertex);
	}

	@Override
	public String toString() {
		return "HexagonVertex[" + "id=" + id + ", posicio=" + posicio + ", hexagon=" + hexagon + ", vertex=" + vertex + ']';
	}
}
	