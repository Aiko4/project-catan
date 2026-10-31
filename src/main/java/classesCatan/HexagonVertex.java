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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_vertex",nullable=false,unique=true)
	private Integer id;

	@NotNull
	@Column(name="posicio",nullable=false)
	@Min(0)
	@Max(5)
	private Integer posicio;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hexagon_id")
    private Hexagon hexagon;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vertex_id")
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

	@Override
	public int hashCode() {
		return Objects.hash(id, posicio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HexagonVertex other = (HexagonVertex) obj;
		return Objects.equals(id, other.id) && Objects.equals(posicio, other.posicio);
	}

	@Override
	public String toString() {
		return "HexagonVertex[" + "id=" + id + ", posicio=" + posicio + ", hexagon=" + hexagon + ", vertex=" + vertex + ']';
	}
}
	