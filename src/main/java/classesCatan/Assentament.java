package classesCatan;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

//S'utilitza SINGLE TABLE per a les subclasses de Carretera i Assentament --> NO ES POT BARREJAR SINGLE TABLE AMB JOINED, PARTEIXEN DE CONSTRUCCIO, QUE FA SERVIR JOINED (REVISAR!!)
// de moment he tret la single table perque tampoc fa res
@Entity
@Table(name = "assentament")
abstract class Assentament extends Construccio {
	//Atributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//Relacions
	@OneToOne
	@JoinColumn(name="vertex_id",unique = true, nullable = false)
	private Vertex vertex;

	//Constructors
	public Assentament() {
	}

	public Assentament(Vertex vertex) {
		this.vertex=vertex;
	}

	//Get Set
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


	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Assentament that = (Assentament) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Assentament [id=" + id + ", vertex=" + vertex + ", tipus=" + "]";
	}
}
