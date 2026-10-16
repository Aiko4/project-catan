import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
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
	
	@NotNull
	@Column(name="vertex",nullable=false)
	private Vertex vertex;
	
	@NotNull
	@Column(name="tipous", nullable=false)
	private String tipus;

	
}
