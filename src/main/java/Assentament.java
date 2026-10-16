import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

//S'utilitza SINGLE TABLE per a les subclasses de Carretera i Assentament

@Entity
@Table(name = "assentament")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipusAssentament", discriminatorType = DiscriminatorType.STRING, length = 10)
abstract class Assentament extends Construccio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_vertex",nullable=false,unique=true)
	private Integer id;
	
	@NotNull
	@Column(name="vertex",nullable=false)
	private Vertex vertex;
	
	@NotNull
	@Column(name="tipous", nullable=false)
	private String tipus;
	
	public Assentament() {
		
	}
	
	public Assentament(Integer id,Vertex vertex, String tipus) {
		this.id=id;
		this.vertex=vertex;
		this.tipus=tipus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tipus, vertex);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assentament other = (Assentament) obj;
		return Objects.equals(id, other.id) && Objects.equals(tipus, other.tipus)
				&& Objects.equals(vertex, other.vertex);
	}

	@Override
	public String toString() {
		return "Assentament [id=" + id + ", vertex=" + vertex + ", tipus=" + tipus + "]";
	}
	
	
	
	
	
}
