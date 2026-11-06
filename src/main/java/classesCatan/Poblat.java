package classesCatan;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("POBLAT")
public class Poblat extends Assentament {
    //Constructors
    public Poblat() {
    }

    public Poblat(Vertex vertex) {
        super(vertex);
    }
}
