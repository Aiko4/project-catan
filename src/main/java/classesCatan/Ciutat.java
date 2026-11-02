package classesCatan;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CIUTAT")
public class Ciutat extends Assentament {

	public Ciutat () {
		
	}
	
	public Ciutat(Integer id,Vertex vertex) {
		super(id, vertex);
	}
	
}
