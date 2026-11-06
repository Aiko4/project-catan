package classesCatan;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CIUTAT")
public class Ciutat extends Assentament {
    //Constructors
    public Ciutat() {
    }

    public Ciutat(Vertex vertex) {
        super(vertex);
    }

}
